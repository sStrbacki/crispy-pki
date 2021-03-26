package rs.ac.uns.ftn.bsep.pki.domain.certificate;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;

public class SubjectInfo {
    private String UID;
    private String organizationName;
    private String organizationalUnitName;
    private String commonName;
    private String surname;
    private String givenName;
    private String initials;
    private String gender;
    private String telephoneNumber;
    private String emailAddress;
    private String generation;
    private String businessCategory;
    private String postalAddress;
    private String postalCode;
    private String countryCode;
    private String title;
    private String street;
    private String stateName;
    private String localityName;

    public SubjectInfo(){ }

    public SubjectInfo(String UID,
                       String organizationName,
                       String organizationalUnitName,
                       String commonName,
                       String surname,
                       String givenName,
                       String initials,
                       String gender,
                       String telephoneNumber,
                       String emailAddress,
                       String generation,
                       String businessCategory,
                       String postalAddress,
                       String postalCode,
                       String countryCode,
                       String title,
                       String street,
                       String stateName,
                       String localityName) {
        this.UID = UID;
        this.organizationName = organizationName;
        this.organizationalUnitName = organizationalUnitName;
        this.commonName = commonName;
        this.surname = surname;
        this.givenName = givenName;
        this.initials = initials;
        this.gender = gender;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
        this.generation = generation;
        this.businessCategory = businessCategory;
        this.postalAddress = postalAddress;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
        this.title = title;
        this.street = street;
        this.stateName = stateName;
        this.localityName = localityName;
    }

    public X500Name toX500Name() {
        X500NameBuilder builder = new X500NameBuilder();
        builder
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


}
