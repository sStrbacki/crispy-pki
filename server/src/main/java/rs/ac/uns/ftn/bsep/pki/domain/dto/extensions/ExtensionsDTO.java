package rs.ac.uns.ftn.bsep.pki.domain.dto.extensions;

import org.bouncycastle.asn1.x509.Extension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExtensionsDTO {

    private KeyPurposesDTO keyPurposes;
    private ExtendedKeyPurposesDTO extendedKeyPurposes;

    public ExtensionsDTO() {
    }

    public KeyPurposesDTO getKeyPurposes() {
        return keyPurposes;
    }

    public void setKeyPurposes(KeyPurposesDTO keyPurposes) {
        this.keyPurposes = keyPurposes;
    }

    public ExtendedKeyPurposesDTO getExtendedKeyPurposes() {
        return extendedKeyPurposes;
    }

    public void setExtendedKeyPurposes(ExtendedKeyPurposesDTO extendedKeyPurposes) {
        this.extendedKeyPurposes = extendedKeyPurposes;
    }
    public List<Extension> toExtensions() throws IOException {
        return Arrays.asList(this.keyPurposes.toExtension(), this.extendedKeyPurposes.toExtension());
    }




}
