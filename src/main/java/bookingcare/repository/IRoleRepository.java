package bookingcare.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookingcare.dto.enums.RoleName;
import bookingcare.entity.Role;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
   Optional<Role> findByName(RoleName name);
}
