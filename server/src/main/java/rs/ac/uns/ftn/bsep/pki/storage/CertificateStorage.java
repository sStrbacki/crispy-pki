package rs.ac.uns.ftn.bsep.pki.storage;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Component
public class CertificateStorage {

    public void store(X509Certificate[] certificateChain, PrivateKey privateKey) {
        String serialNumber = certificateChain[0].getSerialNumber().toString();
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            try {
                keyStore.load(new FileInputStream("keystore.p12"), "123456".toCharArray());
            } catch (IOException e) {
                keyStore.load(null, null);
            }

            // zasto je password serijski broj?
            keyStore.setKeyEntry(serialNumber, privateKey, serialNumber.toCharArray(), certificateChain);
            keyStore.store(new FileOutputStream("keystore.p12"), "123456".toCharArray());
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
}
