package rs.ac.uns.ftn.bsep.pki.service;

import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.*;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.Certificate;
import rs.ac.uns.ftn.bsep.pki.domain.enums.CertificateType;
import rs.ac.uns.ftn.bsep.pki.exceptions.IssuerNotFoundException;
import rs.ac.uns.ftn.bsep.pki.repository.CertificateRepository;
import rs.ac.uns.ftn.bsep.pki.storage.CertificateStorage;

import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateService {

    private CertificateGenerator certificateGenerator;
    private CertificateStorage certificateStorage;
    private CertificateRepository certificateRepository;

    public CertificateService(CertificateGenerator certificateGenerator,
                              CertificateRepository certificateRepository,
                              CertificateStorage certificateStorage) {
        this.certificateGenerator = certificateGenerator;
        this.certificateRepository = certificateRepository;
        this.certificateStorage = certificateStorage;

    }

    public Certificate save(CertificateRequest certificateRequest) {
        KeyPair keyPair = generateKeyPair();
        SubjectData subjectData =
                generateSubjectData(keyPair.getPublic(), certificateRequest.getSubjectDistinguishedName(), certificateRequest.getValidity());
        IssuerData issuerData;
        if (certificateRequest.getType() == CertificateType.SELF_SIGNED) {
            issuerData = new IssuerData(certificateRequest.getSubjectDistinguishedName(), keyPair.getPrivate());
        } else {
            issuerData = certificateStorage.findCAbySerialNumber(certificateRequest.getIssuerSerialNumber());
        }
        var certificateChain =
                certificateGenerator.generateCertificate(subjectData, issuerData, certificateRequest.getExtensions());
        certificateStorage.store(certificateChain, certificateRequest.getType());
        return certificateRepository.save(new Certificate(
                subjectData.getSerialNumber(),
                certificateRequest.getType()
        ));

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

    private SubjectData generateSubjectData(PublicKey publicKey,
                                            X500Name subjectDistinguishedName,
                                            CertificateValidity validity) {
        long now = System.currentTimeMillis();
        return new SubjectData(
                publicKey,
                subjectDistinguishedName,
                Long.toString(now),
                validity.getValidFrom(),
                validity.getValidTo());
    }

    public List<Certificate> getCertificateChain(String serialNumber) {
        return Arrays.stream(certificateStorage.readCertificateChain(serialNumber))
                .map(certificate -> new Certificate(((X509Certificate)certificate).getSerialNumber().toString(), null)).collect(Collectors.toList());
    }

}
