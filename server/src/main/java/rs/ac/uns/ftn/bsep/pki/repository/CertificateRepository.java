package rs.ac.uns.ftn.bsep.pki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.bsep.pki.domain.certificate.Certificate;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    @Query(nativeQuery = true, value = "select * from Certificate where serialNumber := ?1 limit 1")
    Optional<Certificate> getBySerialNumber(String serialNumber);
}
