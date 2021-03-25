package rs.ac.uns.ftn.bsep.pki.exceptions;

public class CertificateAlreadyRevokedException extends RuntimeException {

    public CertificateAlreadyRevokedException() {
        super("This certificate has already been revoked.");
    }
}
