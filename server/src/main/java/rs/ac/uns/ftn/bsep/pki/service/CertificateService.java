package rs.ac.uns.ftn.bsep.pki.service;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bsep.pki.repository.CertificateRepository;
import rs.ac.uns.ftn.bsep.pki.storage.CertificateStorage;
import java.util.List;

@Service
public class CertificateService {

    @Autowired
    private CertificateGenerator certificateGenerator;

    private CertificateStorage certificateStorage;

    private CertificateRepository certificateRepository;

    public CertificateService(CertificateRepository certificateRepository, CertificateStorage certificateStorage) {
        this.certificateRepository = certificateRepository;
        this.certificateStorage = certificateStorage;

    }

    public void save(X500Name subjectDistinguishedName, List<Extension> extensions, String issuerSerialNumber) {
        var certificateChain =
                certificateGenerator.generateRootCertificate(subjectDistinguishedName,extensions);
        certificateStorage.store(certificateChain);
    }

}
