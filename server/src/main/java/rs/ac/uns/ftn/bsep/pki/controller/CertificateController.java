package rs.ac.uns.ftn.bsep.pki.controller;

import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.Certificate;
import rs.ac.uns.ftn.bsep.pki.domain.dto.CertificateRequestDTO;
import rs.ac.uns.ftn.bsep.pki.service.CertificateService;

import java.security.cert.X509Certificate;
import java.util.List;
import java.util.stream.Collectors;


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

    @GetMapping("{serialNumber}")
    public String getCertificate(@PathVariable String serialNumber) {
        return certificateService.getCertificate(serialNumber).toString();
    }
}
