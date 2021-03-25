package rs.ac.uns.ftn.bsep.pki.domain.dto.extensions;

import org.bouncycastle.asn1.x509.Extension;
import java.io.IOException;
import java.util.ArrayList;
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
    public List<Extension> toExtensions() {
        ArrayList<Extension> extensions = new ArrayList<>();
        try {
            var keyPurposesExtension = this.keyPurposes.toExtension();
            var extendedKeyPurposesExtension = this.extendedKeyPurposes.toExtension();
            if(keyPurposesExtension != null)
                extensions.add(keyPurposesExtension);
            if(extendedKeyPurposesExtension != null)
                extensions.add(extendedKeyPurposesExtension);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extensions;
    }




}
