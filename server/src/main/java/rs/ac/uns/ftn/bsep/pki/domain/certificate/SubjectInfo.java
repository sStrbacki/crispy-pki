package rs.ac.uns.ftn.bsep.pki.domain.certificate;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;

public class SubjectInfo {
    private String countryName;
    private String stateName;
    private String localityName;
    private String organizationName;
    private String organizationalUnitName;
    private String commonName;
    private String surname;
    private String givenName;
    private String emailAddress;

    public SubjectInfo(){ }

    public SubjectInfo(String countryName,
                       String stateName,
                       String localityName,
                       String organizationName,
                       String organizationalUnitName,
                       String commonName,
                       String surname,
                       String givenName,
                       String emailAddress) {
        this.countryName = countryName;
        this.stateName = stateName;
        this.localityName = localityName;
        this.organizationName = organizationName;
        this.organizationalUnitName = organizationalUnitName;
        this.commonName = commonName;
        this.surname = surname;
        this.givenName = givenName;
        this.emailAddress = emailAddress;
    }

    public X500Name toX500Name() {
        X500NameBuilder builder = new X500NameBuilder();
        builder
                .addRDN(BCStyle.COUNTRY_OF_RESIDENCE, countryName)
                .addRDN(BCStyle.CN, commonName)
                .addRDN(BCStyle.GIVENNAME, givenName)
                .addRDN(BCStyle.SURNAME, surname)
                .addRDN(BCStyle.EmailAddress, emailAddress)
                .addRDN(BCStyle.L, localityName)
                .addRDN(BCStyle.O, organizationName)
                .addRDN(BCStyle.OU, organizationalUnitName)
                .addRDN(BCStyle.ST, stateName);
        return builder.build();
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getLocalityName() {
        return localityName;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationalUnitName() {
        return organizationalUnitName;
    }

    public void setOrganizationalUnitName(String organizationalUnitName) {
        this.organizationalUnitName = organizationalUnitName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
