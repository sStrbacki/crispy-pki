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

    @Column(nullable = false)
    private CertificateType certificateType;

    @Column(nullable = false)
    private boolean revoked;

    @Column(nullable = false)
    private String chainId;

    @Column
    private RevocationReasonCode reasonCode;

    public Certificate() {
    }

    public Certificate(String serialNumber, CertificateType certificateType, String chainId){
        this.serialNumber = serialNumber;
        this.certificateType = certificateType;
        this.revoked = false;
        this.reasonCode = null;
        this.chainId = chainId;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
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
