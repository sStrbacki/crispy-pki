package rs.ac.uns.ftn.bsep.pki.domain.dto;

import rs.ac.uns.ftn.bsep.pki.domain.enums.CertificateType;
import rs.ac.uns.ftn.bsep.pki.mappers.CertificateMapper;

import java.security.cert.X509Certificate;

public class CertificateDTO {

    private String serialNumber;
    private String issuerCN;
    private String subjectCN;
    private CertificateType certificateType;
    private ValidityDTO validity;

    public CertificateDTO() {
    }

    public CertificateDTO(X509Certificate certificate, CertificateType certificateType){
        var certificateMapper = new CertificateMapper();
        this.serialNumber = certificate.getSerialNumber().toString();
        this.issuerCN = certificateMapper.toIssuerCN(certificate);
        this.subjectCN = certificateMapper.toSubjectCN(certificate);
        this.validity = new ValidityDTO(certificate.getNotBefore(),certificate.getNotAfter());
        this.certificateType = certificateType;
    }

    public CertificateDTO(X509Certificate certificate) {
        var certificateMapper = new CertificateMapper();
        this.serialNumber = certificate.getSerialNumber().toString();
        this.issuerCN = certificateMapper.toIssuerCN(certificate);
        this.subjectCN = certificateMapper.toSubjectCN(certificate);
        this.validity = new ValidityDTO(certificate.getNotBefore(),certificate.getNotAfter());
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getIssuerCN() {
        return issuerCN;
    }

    public void setIssuerCN(String issuerCN) {
        this.issuerCN = issuerCN;
    }

    public String getSubjectCN() {
        return subjectCN;
    }

    public void setSubjectCN(String subjectCN) {
        this.subjectCN = subjectCN;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    public ValidityDTO getValidity() {
        return validity;
    }

    public void setValidity(ValidityDTO validity) {
        this.validity = validity;
    }
}
