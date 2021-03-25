package rs.ac.uns.ftn.bsep.pki.exceptions;

public class CertificateNotFoundException extends RuntimeException {

    public CertificateNotFoundException() {
        super("Such a certificate does not exist.");
    }
}
