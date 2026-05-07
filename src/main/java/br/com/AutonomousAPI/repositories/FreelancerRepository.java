package br.com.AutonomousAPI.repositories;

import br.com.AutonomousAPI.entities.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
    Optional<Freelancer> findByCpf(String cpf);

    Optional<Freelancer> findByEmail(String email);

    Optional<Freelancer> findByPhone(String phone);

    List<Freelancer> findByActiveTrueOrderByName();

    List<Freelancer> findByActiveFalseOrderByName();
}
