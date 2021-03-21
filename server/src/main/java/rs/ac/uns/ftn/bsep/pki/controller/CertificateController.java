package rs.ac.uns.ftn.bsep.pki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.bsep.pki.domain.dto.CertificateRequestDTO;
import rs.ac.uns.ftn.bsep.pki.mappers.CertificateRequestMapper;
import rs.ac.uns.ftn.bsep.pki.service.CertificateService;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

    @Autowired
    private CertificateRequestMapper certificateRequestMapper;
    private CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CertificateRequestDTO certificateRequestDTO){
        var name = certificateRequestMapper
                .toX500Name(certificateRequestDTO.getSubjectInfo());
        var extensions =
                certificateRequestMapper.toExtensions(certificateRequestDTO.getExtensions());
        certificateService.save(name,extensions,certificateRequestDTO.getIssuerSerialNumber());

        return ResponseEntity.ok(null);
    }
}
