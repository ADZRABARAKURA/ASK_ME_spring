package tech.razymov.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.razymov.restfull.entity.Donation;
import tech.razymov.restfull.entity.User;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findAllByUser(User user);
}
