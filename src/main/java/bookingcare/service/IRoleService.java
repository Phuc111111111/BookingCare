package bookingcare.service;



import java.util.Optional;

import bookingcare.dto.enums.RoleName;
import bookingcare.entity.Role;

public interface IRoleService {
    Optional<Role> findByName(RoleName name);
}
