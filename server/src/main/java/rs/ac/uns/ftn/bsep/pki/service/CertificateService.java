package rs.ac.uns.ftn.bsep.pki.service;

import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.Certificate;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.*;
import rs.ac.uns.ftn.bsep.pki.domain.enums.CertificateType;
import rs.ac.uns.ftn.bsep.pki.exceptions.CertificateAlreadyRevokedException;
import rs.ac.uns.ftn.bsep.pki.exceptions.CertificateNotFoundException;
import rs.ac.uns.ftn.bsep.pki.repository.CertificateRepository;
import rs.ac.uns.ftn.bsep.pki.storage.CertificateStorage;
import java.security.*;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CertificateService {

    private final CertificateGenerator certificateGenerator;
    private final CertificateStorage certificateStorage;
    private final CertificateRepository certificateRepository;

    public CertificateService(CertificateGenerator certificateGenerator,
                              CertificateRepository certificateRepository,
                              CertificateStorage certificateStorage) {
        this.certificateGenerator = certificateGenerator;
        this.certificateRepository = certificateRepository;
        this.certificateStorage = certificateStorage;
    }

    public Certificate save(CertificateRequest certificateRequest) {
        KeyPair keyPair = generateKeyPair();
        IssuerData issuerData;
        SubjectData subjectData =
                generateSubjectData(keyPair.getPublic(), certificateRequest.getSubjectDistinguishedName(), certificateRequest.getValidity());
        String chainId;

        chainId = Long.toHexString(certificateRepository.count());

        if (certificateRequest.getType() == CertificateType.SELF_SIGNED)
            issuerData = new IssuerData(certificateRequest.getSubjectDistinguishedName(), keyPair.getPrivate());
        else {
            issuerData = certificateStorage.findCAbySerialNumber(certificateRequest.getIssuerSerialNumber());
            Certificate issuerCert = certificateRepository.findBySerialNumber(certificateRequest.getIssuerSerialNumber());
            chainId += "-" + issuerCert.getChainId();
        }

        var certificateChain =
                certificateGenerator.generateCertificate(subjectData, issuerData, certificateRequest.getExtensions());
        certificateStorage.store(certificateChain, certificateRequest.getType());

        return certificateRepository.save(new Certificate(
                subjectData.getSerialNumber(),
                certificateRequest.getType(),
                chainId
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

    public X509Certificate get(String serialNumber) {
        Certificate cert = certificateRepository.findBySerialNumber(serialNumber);
        if (cert == null) throw new CertificateNotFoundException();
        return (X509Certificate) certificateStorage.readCertificate(serialNumber, cert.getCertificateType());
    }

    public List<X509Certificate> getAll(){
        return findCertificates(certificateRepository.getNonRevoked());
    }

    public List<X509Certificate> getAllCAs() {
        return findCertificates(certificateRepository.getNonRevokedCAs());
    }

    private List<X509Certificate> findCertificates(List<Certificate> certificates){
        ArrayList<X509Certificate> foundCertificates = new ArrayList<>();

        for (var certificate : certificates) {
            var x509certificate = (X509Certificate) certificateStorage
                    .readCertificate(certificate.getSerialNumber(), certificate.getCertificateType());
            try {
                x509certificate.checkValidity();
            } catch (CertificateExpiredException | CertificateNotYetValidException e) {
                continue;
            }
            foundCertificates.add(x509certificate);
        }

        return foundCertificates;

    }

    public CertificateType getCertificateType(String serialNumber){
        Certificate cert = certificateRepository.findBySerialNumber(serialNumber);
        if (cert == null) throw new CertificateNotFoundException();
        return cert.getCertificateType();
    }

    public void revoke(String serialNumber) throws CertificateNotFoundException {
        Certificate cert = certificateRepository.findBySerialNumber(serialNumber);

        if (cert == null) throw new CertificateNotFoundException();
        if (cert.isRevoked()) throw new CertificateAlreadyRevokedException();

        cert.setRevoked(true);
        certificateRepository.save(cert);

        if (cert.getCertificateType() != CertificateType.END_ENTITY) {
            List<Certificate> childCerts = certificateRepository.findByChainIdLike("%" + cert.getChainId());
            for (var child : childCerts) child.setRevoked(true);
            certificateRepository.saveAll(childCerts);
        }
    }

    public boolean isRevoked(String serialNumber) {
        Certificate cert = certificateRepository.findBySerialNumber(serialNumber);
        if (cert == null) throw new CertificateNotFoundException();
        return cert.isRevoked();
    }
}
