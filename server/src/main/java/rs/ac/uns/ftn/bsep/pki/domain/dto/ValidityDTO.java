package rs.ac.uns.ftn.bsep.pki.domain.dto;

import rs.ac.uns.ftn.bsep.pki.domain.certificate.CertificateValidity;

import java.util.Date;

public class ValidityDTO {

    private Date validFrom;
    private Date validTo;

    public ValidityDTO() {
    }

    public ValidityDTO(Date notBefore, Date notAfter) {
        this.validFrom = notBefore;
        this.validTo = notAfter;
    }

    public CertificateValidity toCertificateValidity() {
        return new CertificateValidity(validFrom, validTo);
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }
}
