package rs.ac.uns.ftn.bsep.pki.exceptions;

public class IssuerNotFoundException extends RuntimeException {
    public IssuerNotFoundException(String message) {
        super(message);
    }
}
