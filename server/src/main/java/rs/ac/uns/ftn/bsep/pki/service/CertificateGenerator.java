package rs.ac.uns.ftn.bsep.pki.service;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.CertificateChain;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.IssuerData;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.SubjectData;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class CertificateGenerator {

    private JcaContentSignerBuilder builder;

    public CertificateGenerator() {
        Security.addProvider(new BouncyCastleProvider());
        this.builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
        this.builder.setProvider("BC");
    }

    public CertificateChain generateRootCertificate(X500Name subjectDistinguishedName, List<Extension> extensions) {
        var keyPair = generateKeyPair();
        var subjectData = generateSubjectData(keyPair.getPublic(), subjectDistinguishedName, true);
        var issuerData = new IssuerData(subjectDistinguishedName, keyPair.getPrivate());
        return generateCertificate(subjectData, issuerData, extensions);
    }

    public CertificateChain generateCertificate(SubjectData subjectData, IssuerData issuerData, List<Extension> extensions) {
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
            for(var extension : extensions)
                certificateBuilder.addExtension(extension);
            X509CertificateHolder certificateHolder = certificateBuilder.build(contentSigner);
            JcaX509CertificateConverter certificateConverter = new JcaX509CertificateConverter().setProvider("BC");
            return new CertificateChain(
                    certificateConverter.getCertificate(certificateHolder),
                    issuerData.getPrivateKey());
        } catch (OperatorCreationException | CertificateException | CertIOException e) {
            e.printStackTrace();
        }

        return null;
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
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }
}
