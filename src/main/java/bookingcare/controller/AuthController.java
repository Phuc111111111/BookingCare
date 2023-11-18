package bookingcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import bookingcare.dto.enums.RoleName;
import bookingcare.dto.request.ResetPasswordRequest;
import bookingcare.dto.request.SignInForm;
import bookingcare.dto.request.SignUpForm;
import bookingcare.dto.request.UserDTO;
import bookingcare.dto.response.JwtResponse;
import bookingcare.dto.response.ResponMessage;
import bookingcare.entity.Doctor;
import bookingcare.entity.Patient;
import bookingcare.entity.Role;
import bookingcare.entity.User;
import bookingcare.security.Jwt.jwt.JwtProvider;
import bookingcare.security.Jwt.userprincal.UserPrinciple;
import bookingcare.service.DoctorService;
import bookingcare.service.PatientService;
import bookingcare.service.impl.RoleServiceImpl;
import bookingcare.service.impl.UserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    DoctorService doctorService;
    @Autowired
    PatientService patientService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    
    
    
//    @PostMapping("/signup")
//    public ResponseEntity<?> register(@RequestBody SignUpForm signUpForm){
//    	
//        if(userService.existsByUsername(signUpForm.getUsername())){
//            return new ResponseEntity<>(new ResponMessage("no user"), HttpStatus.OK);
//        }
//        if(userService.existsByEmail(signUpForm.getEmail())){
//            return new ResponseEntity<>(new ResponMessage("no email"), HttpStatus.OK);
//        }
//        User user = new User(signUpForm.getUsername(),signUpForm.getAddress(), signUpForm.getEmail() ,passwordEncoder.encode(signUpForm.getPassword()));
//        Set<String> strRoles = signUpForm.getRoles();
//        
//        Set<Role> roles = new HashSet<>();
//        strRoles.forEach(role ->{
//            switch (role){
//                case "ROLE_ADMIN":
//                    Role adminRole = roleService.findByName(RoleName.ROLE_ADMIN).orElseThrow(
//                            ()-> new RuntimeException("Role not found")
//                    );
//                    roles.add(adminRole);
//                    break;
//                case "ROLE_DOCTOR":
//                    Role pmRole = roleService.findByName(RoleName.ROLE_DOCTOR).orElseThrow( ()-> new RuntimeException("Role not found"));
//                    roles.add(pmRole);
//                    break;
//                default:
//                    Role userRole = roleService.findByName(RoleName.ROLE_USER).orElseThrow( ()-> new RuntimeException("Role not found"));
//                    roles.add(userRole);
//                    
//            }
//        });
//        user.setRoles(roles);
//        userService.saveUser(user);
//        return new ResponseEntity<>(new ResponMessage("signup successfully"), HttpStatus.OK);
//    }
    @PostMapping("/signin")
    public ResponseEntity<?> login(@Validated @RequestBody SignInForm signInForm){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getUsername(), userPrinciple.getAuthorities()));
    }
    
    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {


        // check the database if user already exists
        if (userService.userExists(userDTO.getEmail())) {
            return new ResponseEntity<>("Email existed", HttpStatus.BAD_REQUEST);
        }

        // convert userDTO to user
        User user = userDTO.toUser();

        // encrypt password from plaintext to Bcrypt and save to database
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        String randomCode = UUID.randomUUID().toString();
        user.setName(userDTO.getName());
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
        //send VerificationEmail to user
        String siteURL = request.getRequestURL().toString().replace(request.getServletPath(), "");
        userService.sendVerificationEmail(user, siteURL);
        //save to database
        userService.saveUser(user);
        
        if(userDTO.getRoleId()==2) { // save table doctor
        		Doctor doctor = new Doctor();
        		doctor.setUser(user);
        		doctorService.save(doctor);
        	}
        if(userDTO.getRoleId()==3) {// save table patient
    		Patient patient = new Patient();
    		patient.setUser(user);
    		patientService.save(patient);
    	}

        return ResponseEntity.ok("Register successfully! Please check your email to verify your account.");
    }
    // verify to email 19102000ph@gmail.com
    @GetMapping("/verify")
    public String verifyUser(@RequestParam("code") String code) {
        User user = userService.findByVerificationCode(code);
        if (user == null) {
            return "verify fail";
        } else if (user.isEnabled()) {
            return "Email has been verified";
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userService.saveUser(user);
            return "verify success";
        }
    }
    
    @GetMapping("/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestParam("email") String email,
                                            HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        User user = userService.findByEmail(email);
        if (user==null) return new ResponseEntity<>("Email is not found", HttpStatus.NOT_FOUND);

        // create resetPassword code for user and save to database
        String randomCode = UUID.randomUUID().toString();
        user.setResetPasswordCode(randomCode);
        userService.saveUser(user);

        String siteURL = request.getRequestURL().toString().replace(request.getServletPath(), "");
        userService.sendResetPasswordEmail(user, siteURL);
        return ResponseEntity.ok("We send you a email. Please check your email");
    }
    
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("code") String code, @RequestBody ResetPasswordRequest resetPasswordRequest) {
        User user = userService.findByResetPasswordCode(code);
        if (user == null) {
            return "Reset password fail";
        } else {
            // check if new password and rePassword are equal
            if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getRePassword())) {
                return "Password is not equal";
            }
            // set new password for user
            String newPassword = passwordEncoder.encode(resetPasswordRequest.getNewPassword());
            user.setPassword(newPassword);
            user.setResetPasswordCode(null);
            userService.saveUser(user);
            return "reset password success";
        }
    }
}
