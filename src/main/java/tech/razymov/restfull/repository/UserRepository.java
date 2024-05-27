package tech.razymov.restfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.razymov.restfull.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}