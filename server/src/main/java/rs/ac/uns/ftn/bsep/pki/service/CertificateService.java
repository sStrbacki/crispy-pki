package rs.ac.uns.ftn.bsep.pki.service;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.IssuerData;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.SubjectData;
import rs.ac.uns.ftn.bsep.pki.repository.CertificateRepository;
import rs.ac.uns.ftn.bsep.pki.storage.CertificateStorage;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

@Service
public class CertificateService {

    private CertificateStorage certificateStorage;
    private CertificateRepository certificateRepository;

    private KeyPair keyPair;

    public CertificateService(CertificateRepository certificateRepository, CertificateStorage certificateStorage) {
        this.certificateRepository = certificateRepository;
        this.certificateStorage = certificateStorage;

    }

    public void save(X500Name subjectDistinguishedName, String issuerSerialNumber) {
        var certificate = generateRootCertificate(subjectDistinguishedName, issuerSerialNumber);
        certificateStorage.store(new X509Certificate[] {certificate}, keyPair.getPrivate());
    }

    public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData) {
        JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
        builder = builder.setProvider("BC");
        try {
            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());
            X509v3CertificateBuilder certificateBuilder =
                    new JcaX509v3CertificateBuilder(
                            issuerData.getX500name(),
                            new BigInteger(subjectData.getSerialNumber()),
                            subjectData.getStartDate(),
                            subjectData.getEndDate(),
                            subjectData.getX500Name(),
                            subjectData.getPublicKey());
            certificateBuilder.addExtension(Extension.extendedKeyUsage, false, new ExtendedKeyUsage(KeyPurposeId.anyExtendedKeyUsage));
            X509CertificateHolder certificateHolder = certificateBuilder.build(contentSigner);
            JcaX509CertificateConverter certificateConverter = new JcaX509CertificateConverter().setProvider("BC");
            return certificateConverter.getCertificate(certificateHolder);
        } catch (OperatorCreationException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (CertIOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public X509Certificate generateRootCertificate(X500Name subjectDistinguishedName, String issuerSerialNumber) {
        keyPair = generateKeyPair();
        var subjectData = generateSubjectData(keyPair.getPublic(), subjectDistinguishedName, true);
        var issuerData = new IssuerData(subjectDistinguishedName, keyPair.getPrivate());
        return generateCertificate(subjectData, issuerData);
    }

    private SubjectData generateSubjectData(PublicKey publicKey, X500Name subjectDistinguishedName, boolean isCA) {
        long now = System.currentTimeMillis();
        Date startDate = new Date(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        if (isCA)
            calendar.add(Calendar.YEAR, 20);
        else
            calendar.add(Calendar.YEAR, 10);
        Date endDate = calendar.getTime();
        return new SubjectData(publicKey, subjectDistinguishedName, Long.toString(now), startDate, endDate);
    }

    private KeyPair generateKeyPair() {
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyPairGenerator.initialize(2048, random);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }
}
