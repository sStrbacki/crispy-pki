package rs.ac.uns.ftn.bsep.pki.domain.dto;

import rs.ac.uns.ftn.bsep.pki.domain.certificate.CertificateRequest;
import rs.ac.uns.ftn.bsep.pki.domain.dto.extensions.ExtensionsDTO;
import rs.ac.uns.ftn.bsep.pki.domain.enums.CertificateType;
import rs.ac.uns.ftn.bsep.pki.mappers.CertificateRequestMapper;

public class CertificateRequestDTO {

    private String issuerSerialNumber;
    private SubjectInfoDTO subjectInfo;
    private CertificateType certificateType;
    private ValidityDTO certificateValidity;
    private ExtensionsDTO extensions;

    public CertificateRequestDTO() {
    }

    public CertificateRequest toCertificateRequest() {
        return new CertificateRequest(
                new CertificateRequestMapper().toX500Name(subjectInfo),
                issuerSerialNumber,
                extensions.toExtensions(),
                certificateType,
                certificateValidity.toCertificateValidity());
    }

    public SubjectInfoDTO getSubjectInfo() {
        return subjectInfo;
    }

    public void setSubjectInfo(SubjectInfoDTO subjectInfo) {
        this.subjectInfo = subjectInfo;
    }

    public String getIssuerSerialNumber() {
        return issuerSerialNumber;
    }

    public void setIssuerSerialNumber(String issuerSerialNumber) {
        this.issuerSerialNumber = issuerSerialNumber;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    public ValidityDTO getCertificateValidity() {
        return certificateValidity;
    }

    public void setCertificateValidity(ValidityDTO certificateValidity) {
        this.certificateValidity = certificateValidity;
    }

    public ExtensionsDTO getExtensions() {
        return extensions;
    }

    public void setExtensions(ExtensionsDTO extensions) {
        this.extensions = extensions;
    }
}
