package rs.ac.uns.ftn.bsep.pki.storage;

import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.bsep.pki.config.Config;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.CertificateChain;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.IssuerData;
import rs.ac.uns.ftn.bsep.pki.domain.enums.CertificateType;
import rs.ac.uns.ftn.bsep.pki.exceptions.IssuerNotFoundException;
import javax.security.auth.x500.X500Principal;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
public class CertificateStorage {

    private final Config config;

    private final KeyStore EEKeystore;
    private final KeyStore CAKeystore;

    public CertificateStorage(Config config)
            throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException
    {
        String KEYSTORE_TYPE = "PKCS12";

        this.EEKeystore = KeyStore.getInstance(KEYSTORE_TYPE);
        this.CAKeystore = KeyStore.getInstance(KEYSTORE_TYPE);

        try {
            this.EEKeystore.load(new FileInputStream(config.getEEKeyStore()), config.getKeyStorePassword().toCharArray());
        }
        catch (FileNotFoundException e) {
            this.EEKeystore.load(null, null);
        }

        try {
            this.CAKeystore.load(new FileInputStream(config.getCAKeyStore()), config.getKeyStorePassword().toCharArray());
        }
        catch (FileNotFoundException e) {
            this.CAKeystore.load(null, null);
        }

        this.config = config;
    }

    public void store(CertificateChain certificateChain, CertificateType certType) {
        String serialNumber = certificateChain.getCertificateChain()[0].getSerialNumber().toString();
        KeyStore keyStore;
        String keyStorePath;

        if (certType == CertificateType.END_ENTITY) {
            keyStore = this.EEKeystore;
            keyStorePath = config.getEEKeyStore();
        }
        else {
            keyStore = this.CAKeystore;
            keyStorePath = config.getCAKeyStore();
        }

        try {

            keyStore.setKeyEntry(
                serialNumber,
                certificateChain.getPrivateKey(),
                serialNumber.toCharArray(),
                certificateChain.getCertificateChain()
            );
            keyStore.store(
                    new FileOutputStream(keyStorePath),
                    config.getKeyStorePassword().toCharArray()
            );
        }
        catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }

    private Certificate[] readCAChain(String serialNumber) {
        try {
            return this.CAKeystore.getCertificateChain(serialNumber);
        }
        catch (KeyStoreException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Certificate[] getCertificateChain(String serialNumber, CertificateType type) {
        try {
            List<Certificate> ALChain = new ArrayList<>();
            if (type == CertificateType.END_ENTITY) {
                var EECertificate = (X509Certificate)this.EEKeystore.getCertificate(serialNumber);
                ALChain.add(EECertificate);
                serialNumber = EECertificate.getIssuerX500Principal().toString();
            }
            ALChain.addAll(Arrays.asList(this.CAKeystore.getCertificateChain(serialNumber)));
            Certificate[] VChain = new Certificate[ALChain.size()];
            return ALChain.toArray(VChain);
        }
        catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    private X509Certificate findIssuerCertificate(X500Principal issuerX500Principal) {
        for (var certificate: readAllCACertificates()) {
            if (((X509Certificate)certificate).getSubjectX500Principal().equals(issuerX500Principal))
                return (X509Certificate) certificate;
        }
        return null;
    }

    public Certificate[] readAllCACertificates() {
        try {
            Iterator<String> aliases = this.CAKeystore.aliases().asIterator();
            List<Certificate> ALCert = new ArrayList<>();
            while (aliases.hasNext()) {
                Certificate cert = this.CAKeystore.getCertificate(aliases.next());
                if (cert != null)
                    ALCert.add(cert);
            }
            Certificate[] VCert = new Certificate[ALCert.size()];
            return ALCert.toArray(VCert);
        }
        catch(KeyStoreException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Certificate readCertificate(String serialNumber, CertificateType certType) {
        try {
            if (certType == CertificateType.END_ENTITY)
                return this.EEKeystore.getCertificate(serialNumber);
            else
                return this.CAKeystore.getCertificate(serialNumber);
        }
        catch (KeyStoreException e) {
            e.printStackTrace();
            return null;
        }
    }

    public IssuerData findCAbySerialNumber(String serialNumber) throws IssuerNotFoundException {
        try {
            // Why do we need PrivateKeys alongside Certs in the Keystore?
            Key key = this.CAKeystore.getKey(serialNumber, serialNumber.toCharArray());
            if (key instanceof PrivateKey) {
                X509Certificate certificate = (X509Certificate) this.CAKeystore.getCertificate(serialNumber);
                return new IssuerData(new JcaX509CertificateHolder(certificate).getSubject(), (PrivateKey) key);
            }
            else {
                throw new IssuerNotFoundException();
            }
        }
        catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            e.printStackTrace();
            return null;
        }
    }
}
