package bookingcare.service;



import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import bookingcare.entity.User;
import jakarta.mail.MessagingException;

public interface IUserService{
    User findByUsername(String name); //Tim kiem User co ton tai trong DB khong?
    Boolean existsByUsername(String username); //username da co trong DB chua, khi tao du lieu
    Boolean existsByEmail(String email); //email da co trong DB chua
    
    User findByEmail(String email);

    boolean userExists(String email);
    
    void saveUser(User user);

    void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;

    void sendResetPasswordEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;

    User findByResetPasswordCode(String code);

    User findByVerificationCode(String code);

    User findById(Integer id);
}
