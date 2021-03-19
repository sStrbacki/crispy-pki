package rs.ac.uns.ftn.bsep.pki.domain.dto.extensions;

import rs.ac.uns.ftn.bsep.pki.domain.enums.ExtendedKeyUsage;

import java.util.List;

public class KeyUsagesDTO {
    private boolean isCritical;
    private List<ExtendedKeyUsage> keyUsage;

    public KeyUsagesDTO() {
    }

    public boolean isCritical() {
        return isCritical;
    }

    public void setCritical(boolean critical) {
        isCritical = critical;
    }

    public List<ExtendedKeyUsage> getKeyUsage() {
        return keyUsage;
    }

    public void setKeyUsage(List<ExtendedKeyUsage> keyUsage) {
        this.keyUsage = keyUsage;
    }
}
