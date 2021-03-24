package rs.ac.uns.ftn.bsep.pki.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.Certificate;
import rs.ac.uns.ftn.bsep.pki.domain.dto.CertificateDTO;
import rs.ac.uns.ftn.bsep.pki.domain.dto.CertificateRequestDTO;
import rs.ac.uns.ftn.bsep.pki.service.CertificateService;

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
    public String get(@PathVariable String serialNumber) {
        return certificateService.get(serialNumber).toString();
    }

    @GetMapping
    public List<CertificateDTO> getAll(){
        return certificateService.getAll().stream()
                .map(c -> new CertificateDTO(c, certificateService.getCertificateType(c.getSerialNumber().toString())))
                .collect(Collectors.toList());
    }

    @GetMapping("authorities")
    public List<CertificateDTO> getAllCA(){
        return certificateService.getAllCAs().stream()
                .map(c -> new CertificateDTO(c, certificateService.getCertificateType(c.getSerialNumber().toString())))
                .collect(Collectors.toList());
    }

    @GetMapping("revoke/{serialNumber}")
    public ResponseEntity<Void> revoke(@PathVariable String serialNumber) {
        certificateService.revoke(serialNumber);
        return ResponseEntity.ok().build();
    }

}
