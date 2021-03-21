package rs.ac.uns.ftn.bsep.pki.mappers;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.Extension;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.bsep.pki.domain.dto.SubjectInfoDTO;
import org.apache.commons.beanutils.PropertyUtils;
import rs.ac.uns.ftn.bsep.pki.domain.dto.extensions.ExtensionsDTO;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

@Component
public class CertificateRequestMapper {

    private final Map<String, ASN1ObjectIdentifier> rdns = Map.ofEntries(
        entry("UID", BCStyle.UID),
        entry("organizationName", BCStyle.O),
        entry("organizationalUnitName", BCStyle.OU),
        entry("commonName", BCStyle.CN),
        entry("surname", BCStyle.SURNAME),
        entry("givenName", BCStyle.GIVENNAME),
        entry("initials", BCStyle.INITIALS),
        entry("gender", BCStyle.GENDER),
        entry("telephoneNumber", BCStyle.TELEPHONE_NUMBER),
        entry("emailAddress", BCStyle.EmailAddress),
        entry("generation", BCStyle.GENERATION),
        entry("businessCategory", BCStyle.BUSINESS_CATEGORY),
        entry("postalAddress", BCStyle.POSTAL_ADDRESS),
        entry("postalCode", BCStyle.POSTAL_CODE),
        entry("countryCode", BCStyle.C),
        entry("title", BCStyle.T),
        entry("street", BCStyle.STREET),
        entry("stateName", BCStyle.ST),
        entry("localityName", BCStyle.L)
    );

    public X500Name toX500Name(SubjectInfoDTO subjectInfoDTO){
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        for (Field field : subjectInfoDTO.getClass().getDeclaredFields()){
            try {
                String value = (String) PropertyUtils.getProperty(subjectInfoDTO,
                        field.getName());
                if (value != null && !value.equals("")) {
                    builder.addRDN(rdns.get(field.getName()), value);
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                return null;
            }
        }
        return builder.build();
    }
    public List<Extension> toExtensions(ExtensionsDTO extensions){
        return extensions.toExtensions();
    }
}
