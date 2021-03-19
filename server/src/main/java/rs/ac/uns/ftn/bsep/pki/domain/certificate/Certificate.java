package rs.ac.uns.ftn.bsep.pki.domain.certificate;

import rs.ac.uns.ftn.bsep.pki.domain.enums.CertificateType;
import rs.ac.uns.ftn.bsep.pki.domain.enums.RevocationReasonCode;

import javax.persistence.*;

@Entity
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,unique = true)
    private String serialNumber;

    @Column(unique = true)
    private String commonName;

    @Column(nullable = false)
    private CertificateType certificateType;

    @Column(nullable = false)
    private boolean revoked;

    @Column
    private RevocationReasonCode reasonCode;

    public Certificate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public RevocationReasonCode getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(RevocationReasonCode reasonCode) {
        this.reasonCode = reasonCode;
    }
}
