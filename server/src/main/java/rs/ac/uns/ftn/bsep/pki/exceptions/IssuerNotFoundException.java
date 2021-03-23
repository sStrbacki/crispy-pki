package rs.ac.uns.ftn.bsep.pki.exceptions;

public class IssuerNotFoundException extends RuntimeException {
    public IssuerNotFoundException() {
        super("Issuer not found!");
    }
}
