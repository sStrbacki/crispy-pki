package rs.ac.uns.ftn.bsep.pki.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Value("${pki.ca.keystore}")
    private String CAKeyStore;

    @Value("${pki.ee.keystore}")
    private String EEKeyStore;

    @Value("${pki.keystore.password}")
    private String keyStorePassword;

    public String getCAKeyStore() {
        return CAKeyStore;
    }

    public void setCAKeyStore(String CAKeyStore) {
        this.CAKeyStore = CAKeyStore;
    }

    public String getEEKeyStore() {
        return EEKeyStore;
    }

    public void setEEKeyStore(String EEKeyStore) {
        this.EEKeyStore = EEKeyStore;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }
}
