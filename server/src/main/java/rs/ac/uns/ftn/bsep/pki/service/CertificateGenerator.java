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
import rs.ac.uns.ftn.bsep.pki.domain.certificate.CertificateRequest;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.IssuerData;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.SubjectData;
import rs.ac.uns.ftn.bsep.pki.domain.enums.CertificateType;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.List;

@Component
public class CertificateGenerator {

    private JcaContentSignerBuilder builder;

    public CertificateGenerator() {
        Security.addProvider(new BouncyCastleProvider());
        this.builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
        this.builder.setProvider("BC");
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
}
