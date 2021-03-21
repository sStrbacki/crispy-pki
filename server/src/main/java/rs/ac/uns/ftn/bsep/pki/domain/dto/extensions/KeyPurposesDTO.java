package rs.ac.uns.ftn.bsep.pki.domain.dto.extensions;

import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import rs.ac.uns.ftn.bsep.pki.domain.enums.KeyPurpose;

import java.io.IOException;
import java.util.List;

public class KeyPurposesDTO {

    private boolean isCritical;
    private List<KeyPurpose> keyPurposes;

    public boolean isCritical() {
        return isCritical;
    }

    public void setCritical(boolean critical) {
        isCritical = critical;
    }

    public List<KeyPurpose> getKeyPurposes() {
        return keyPurposes;
    }

    public void setKeyPurposes(List<KeyPurpose> keyPurpose) {
        this.keyPurposes = keyPurpose;
    }

    public Extension toExtension() throws IOException {
        int keyUsageId = keyPurposes.get(0).getKeyUsageId();

        for(int i = 1 ; i < keyPurposes.size() - 2 ; i++)
            keyUsageId |= keyPurposes.get(i).getKeyUsageId();

        return new Extension(Extension.keyUsage, isCritical, new DEROctetString(new KeyUsage(keyUsageId)));
    }

}
