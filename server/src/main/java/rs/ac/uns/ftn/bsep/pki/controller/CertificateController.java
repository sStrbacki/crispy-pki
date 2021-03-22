package rs.ac.uns.ftn.bsep.pki.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.Certificate;
import rs.ac.uns.ftn.bsep.pki.domain.dto.CertificateRequestDTO;
import rs.ac.uns.ftn.bsep.pki.exceptions.IssuerNotFoundException;
import rs.ac.uns.ftn.bsep.pki.service.CertificateService;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping
    public Certificate save(@RequestBody CertificateRequestDTO certificateRequestDTO) {
        return certificateService.save(certificateRequestDTO.toCertificateRequest());
    }
}
