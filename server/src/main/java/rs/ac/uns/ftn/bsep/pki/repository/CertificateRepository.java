package rs.ac.uns.ftn.bsep.pki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.Certificate;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
