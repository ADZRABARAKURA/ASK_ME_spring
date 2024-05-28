package tech.razymov.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.razymov.restfull.entity.Donation;
@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
}