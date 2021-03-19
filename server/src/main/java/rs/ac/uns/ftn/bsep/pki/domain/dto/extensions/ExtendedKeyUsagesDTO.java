package rs.ac.uns.ftn.bsep.pki.domain.dto.extensions;

import rs.ac.uns.ftn.bsep.pki.domain.enums.KeyUsage;

import java.util.List;

public class ExtendedKeyUsagesDTO {

    private boolean isCritical;
    private List<KeyUsage> keyUsage;

    public boolean isCritical() {
        return isCritical;
    }

    public void setCritical(boolean critical) {
        isCritical = critical;
    }

    public List<KeyUsage> getKeyUsage() {
        return keyUsage;
    }

    public void setKeyUsage(List<KeyUsage> keyUsage) {
        this.keyUsage = keyUsage;
    }

}
