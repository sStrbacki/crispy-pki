package rs.ac.uns.ftn.bsep.pki.domain.dto;

import java.time.LocalDate;

public class ValidityDTO {

    private LocalDate validFrom;
    private LocalDate validTo;

    public ValidityDTO() {
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }
}
