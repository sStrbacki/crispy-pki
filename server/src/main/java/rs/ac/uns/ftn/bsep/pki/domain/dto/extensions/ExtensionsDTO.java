package rs.ac.uns.ftn.bsep.pki.domain.dto.extensions;

public class ExtensionsDTO {
    private KeyUsagesDTO keyUsages;
    private ExtendedKeyUsagesDTO extendedKeyUsagesDTO;

    public ExtensionsDTO() {
    }

    public KeyUsagesDTO getKeyUsages() {
        return keyUsages;
    }

    public void setKeyUsages(KeyUsagesDTO keyUsages) {
        this.keyUsages = keyUsages;
    }

    public ExtendedKeyUsagesDTO getExtendedKeyUsagesDTO() {
        return extendedKeyUsagesDTO;
    }

    public void setExtendedKeyUsagesDTO(ExtendedKeyUsagesDTO extendedKeyUsagesDTO) {
        this.extendedKeyUsagesDTO = extendedKeyUsagesDTO;
    }
}
