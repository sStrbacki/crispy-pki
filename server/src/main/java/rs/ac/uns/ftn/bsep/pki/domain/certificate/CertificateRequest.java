package rs.ac.uns.ftn.bsep.pki.domain.certificate;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Extension;
import rs.ac.uns.ftn.bsep.pki.domain.enums.CertificateType;

import java.util.List;

public class CertificateRequest {
    private X500Name subjectDistinguishedName;
    private String issuerSerialNumber;
    private List<Extension> extensions;
    private CertificateType type;
    private CertificateValidity validity;

    public CertificateRequest(X500Name subjectDistinguishedName, String issuerSerialNumber, List<Extension> extensions, CertificateType type, CertificateValidity validity) {
        this.subjectDistinguishedName = subjectDistinguishedName;
        this.issuerSerialNumber = issuerSerialNumber;
        this.extensions = extensions;
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

    public List<Extension> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }
}
