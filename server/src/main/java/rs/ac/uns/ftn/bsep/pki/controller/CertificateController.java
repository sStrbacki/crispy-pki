package rs.ac.uns.ftn.bsep.pki.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.Certificate;
import rs.ac.uns.ftn.bsep.pki.domain.dto.CertificateDTO;
import rs.ac.uns.ftn.bsep.pki.domain.dto.CertificateRequestDTO;
import rs.ac.uns.ftn.bsep.pki.service.CertificateService;

import java.security.cert.CertificateEncodingException;
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

    @DeleteMapping("{serialNumber}")
    public void revoke(@PathVariable String serialNumber) {
        certificateService.revoke(serialNumber);
    }

    @GetMapping("revoked/{serialNumber}")
    public ResponseEntity<CertificateService.OCSPStatus> isRevoked(@PathVariable String serialNumber) {
        return ResponseEntity.ok(certificateService.isRevoked(serialNumber));
    }

    @GetMapping("validate/{serialNumber}")
    public void validate(@PathVariable String serialNumber) {
        certificateService.validate(serialNumber);
    }

    @GetMapping("download/{serialNumber}")
    public ResponseEntity<?> download(@PathVariable String serialNumber){
        X509Certificate certificate = certificateService.get(serialNumber);
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename ="
        + certificate.getSerialNumber().toString() + ".cer");

        try {
            ByteArrayResource resource =
                    new ByteArrayResource(certificate.getEncoded());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);

        }catch (CertificateEncodingException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }
}
