package rs.ac.uns.ftn.bsep.pki.domain.certificate;

import org.bouncycastle.asn1.x500.X500Name;
import rs.ac.uns.ftn.bsep.pki.domain.enums.CertificateType;

public class CertificateRequest {
    private X500Name subjectDistinguishedName;
    private String issuerSerialNumber;
    private CertificateType type;
    private CertificateValidity validity;

    public CertificateRequest(X500Name subjectDistinguishedName, String issuerSerialNumber, CertificateType type, CertificateValidity validity) {
        this.subjectDistinguishedName = subjectDistinguishedName;
        this.issuerSerialNumber = issuerSerialNumber;
        this.type = type;
        this.validity = validity;
    }

    public X500Name getSubjectDistinguishedName() {
        return subjectDistinguishedName;
    }

    public void setSubjectDistinguishedName(X500Name subjectDistinguishedName) {
        this.subjectDistinguishedName = subjectDistinguishedName;
    }

    public String getIssuerSerialNumber() {
        return issuerSerialNumber;
    }

    public void setIssuerSerialNumber(String issuerSerialNumber) {
        this.issuerSerialNumber = issuerSerialNumber;
    }

    public CertificateType getType() {
        return type;
    }

    public void setType(CertificateType type) {
        this.type = type;
    }

    public CertificateValidity getValidity() {
        return validity;
    }

    public void setValidity(CertificateValidity validity) {
        this.validity = validity;
    }
}
