package bookingcare.security.Jwt.userprincal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bookingcare.entity.User;
import bookingcare.repository.IUserRepository;

import javax.swing.text.html.Option;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
	@Autowired
	IUserRepository userRepository;
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        
        return UserPrinciple.build(user);
    }
   
}
