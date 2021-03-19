package rs.ac.uns.ftn.bsep.pki.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bsep.pki.repository.CertificateRepository;

@Service
public class CertificateService {

    private CertificateRepository certificateRepository;

    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }
}
