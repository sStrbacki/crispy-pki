package rs.ac.uns.ftn.bsep.pki.storage;

import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.bsep.pki.config.Config;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.CertificateChain;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.IssuerData;
import rs.ac.uns.ftn.bsep.pki.domain.enums.CertificateType;
import rs.ac.uns.ftn.bsep.pki.exceptions.IssuerNotFoundException;

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
import java.util.List;

@Component
public class CertificateStorage {

    private final Config config;

    public CertificateStorage(Config config) {
        this.config = config;
    }

    public void store(CertificateChain certificateChain, CertificateType type) {
        if (type == CertificateType.END_ENTITY) {
            store(certificateChain, config.getEEKeyStore());
        } else {
            store(certificateChain, config.getCAKeyStore());
        }
    }

    public void store(CertificateChain certificateChain, String keyStorePath) {
        String serialNumber = certificateChain.getCertificateChain()[0].getSerialNumber().toString();
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            try {
                keyStore.load(new FileInputStream(keyStorePath), config.getKeyStorePassword().toCharArray());
            } catch (IOException e) {
                keyStore.load(null, null);
            }
            keyStore.setKeyEntry(
                    serialNumber,
                    certificateChain.getPrivateKey(),
                    serialNumber.toCharArray(),
                    certificateChain.getCertificateChain());
            keyStore.store(new FileOutputStream(keyStorePath), config.getKeyStorePassword().toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Certificate[] readCertificateChain(String serialNumber) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream(config.getCAKeyStore()), config.getKeyStorePassword().toCharArray());
            var certificateChain = keyStore.getCertificateChain(serialNumber);
            String lastSerialNumber = ((X509Certificate)certificateChain[certificateChain.length - 1]).getSerialNumber().toString();
            keyStore.load(new FileInputStream(config.getEEKeyStore()), config.getKeyStorePassword().toCharArray());
            List certificateList = new ArrayList(Arrays.asList(certificateChain));
            certificateList.addAll(Arrays.asList(keyStore.getCertificateChain(lastSerialNumber)));
            return (Certificate[]) certificateList.toArray();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Certificate readCertificate(String serialNumber) {
        try {
            Certificate certificate;
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream(config.getCAKeyStore()), config.getKeyStorePassword().toCharArray());
            certificate = keyStore.getCertificate(serialNumber);
            if (certificate == null) {
                keyStore.load(new FileInputStream(config.getEEKeyStore()), config.getKeyStorePassword().toCharArray());
                certificate = keyStore.getCertificate(serialNumber);
            }
            return certificate;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IssuerData findCAbySerialNumber(String serialNumber) throws IssuerNotFoundException {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream(config.getCAKeyStore()), config.getKeyStorePassword().toCharArray());
            Key key = keyStore.getKey(serialNumber, serialNumber.toCharArray());
            if (key instanceof PrivateKey) {
                X509Certificate certificate = (X509Certificate) keyStore.getCertificate(serialNumber);
                return new IssuerData(new JcaX509CertificateHolder(certificate).getSubject(), (PrivateKey) key);
            } else {
                throw new IssuerNotFoundException("Issuer not found!");
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
