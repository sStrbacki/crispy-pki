package rs.ac.uns.ftn.bsep.pki.domain.enums;
import org.bouncycastle.asn1.x509.KeyPurposeId;

public enum ExtendedKeyPurpose {
    SERVER_AUTH(KeyPurposeId.id_kp_serverAuth),
    CLIENT_AUTH(KeyPurposeId.id_kp_clientAuth),
    CODE_SIGNING(KeyPurposeId.id_kp_codeSigning),
    EMAIL_PROTECTION(KeyPurposeId.id_kp_emailProtection),
    TIME_STAMPING(KeyPurposeId.id_kp_timeStamping),
    OCSP_SIGNING(KeyPurposeId.id_kp_OCSPSigning);

    private KeyPurposeId keyPurposeId;

    ExtendedKeyPurpose(KeyPurposeId keyPurposeId) {
        this.keyPurposeId = keyPurposeId;
    }

    public KeyPurposeId getKeyPurposeId() {
        return keyPurposeId;
    }
}
