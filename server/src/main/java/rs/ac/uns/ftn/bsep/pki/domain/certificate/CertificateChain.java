package rs.ac.uns.ftn.bsep.pki.domain.certificate;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class CertificateChain {
    private X509Certificate[] certificateChain;
    private PrivateKey privateKey;

    public CertificateChain(X509Certificate certificate, PrivateKey privateKey) {
        this.certificateChain = new X509Certificate[] {certificate};
        this.privateKey = privateKey;
    }


    public X509Certificate[] getCertificateChain() {
        return certificateChain;
    }

    public void setCertificateChain(X509Certificate[] certificateChain) {
        this.certificateChain = certificateChain;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
