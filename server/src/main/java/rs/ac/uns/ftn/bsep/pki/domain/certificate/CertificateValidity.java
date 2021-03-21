package rs.ac.uns.ftn.bsep.pki.domain.certificate;

import java.util.Date;

public class CertificateValidity {
    private Date validFrom;
    private Date validTo;

    public CertificateValidity(Date validFrom, Date validTo) {
        this.validFrom = validFrom;
        this.validTo = validTo;
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
