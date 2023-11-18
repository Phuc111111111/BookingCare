package bookingcare.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import bookingcare.dto.request.DoctorDTO;
import bookingcare.dto.request.UserDTO;
import bookingcare.dto.response.ResponMessage;
import bookingcare.entity.Doctor;
import bookingcare.entity.Patient;
import bookingcare.entity.User;
import bookingcare.service.ClinicService;
import bookingcare.service.DoctorService;
import bookingcare.service.IUserService;
import bookingcare.service.PatientService;
import bookingcare.service.SpecializationService;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	private IUserService userService;
	private DoctorService doctorService;
	private PatientService patientService;
	private ClinicService clinicService;
	private SpecializationService specializationService;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public AdminController(IUserService userService, DoctorService doctorService, PatientService patientService,
			 ClinicService clinicService,SpecializationService specializationService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.doctorService = doctorService;
		this.patientService = patientService;
		this.clinicService = clinicService;
		this.specializationService = specializationService;
		this.passwordEncoder = passwordEncoder;
	}

	// user current login
	@GetMapping("/info")
	public ResponseEntity<?> get(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		return ResponseEntity.ok(user);
	}

	// admin disable doctor account
	@GetMapping("/disable/doctors/{doctorId}")
	public ResponseEntity<?> disableDoctor(@PathVariable Integer doctorId) {
		System.out.println("doctorId:"+doctorId);
		Doctor doctor = doctorService.findById(doctorId);
		User user = userService.findById(doctor.getUser().getId());

		user.setEnabled(false);
		userService.saveUser(user);
		return ResponseEntity.ok(user);
	}

	// admin enable doctor account
	@GetMapping("/enable/doctors/{doctorId}")
	public ResponseEntity<?> enableDoctor(@PathVariable Integer doctorId) {
		Doctor doctor = doctorService.findById(doctorId);
		User user = userService.findById(doctor.getUser().getId());
		user.setEnabled(true);
		userService.saveUser(user);
		return ResponseEntity.ok(user);
	}

	// admin disable patient account
	@GetMapping("/disable/patients/{patientId}")
	public ResponseEntity<?> disablePatient(@PathVariable Integer patientId) {
		Patient patient = patientService.findById(patientId);
		User user = userService.findById(patient.getUser().getId());
		user.setEnabled(false);
		userService.saveUser(user);
		return new ResponseEntity<>(new ResponMessage("disable patient account successfull"), HttpStatus.OK);
	}

	// admin enable patient account
	@GetMapping("/enable/patients/{patientId}")
	public ResponseEntity<?> enablePatient(@PathVariable Integer patientId) {
		
		Patient patient = patientService.findById(patientId);
		User user = userService.findById(patient.getUser().getId());
		user.setEnabled(true);
		userService.saveUser(user);
		return ResponseEntity.ok(user);
	}

	 //add account doctor
	@PostMapping("/addAccountDoctor")
	public ResponseEntity<?> addDoctor(@RequestBody DoctorDTO doctorDTO, HttpServletRequest request)
			throws MessagingException, UnsupportedEncodingException {
		// check the database if user already exists
		if (userService.userExists(doctorDTO.getEmail()))
			return new ResponseEntity<>("Email existed", HttpStatus.BAD_REQUEST);

		// create new user
		User user = doctorDTO.convertToUser();

		// encrypt password from plaintext to Bcrypt and save to database
		String password = user.getPassword();
		user.setPassword(passwordEncoder.encode(password));
		String randomCode = UUID.randomUUID().toString();
		user.setVerificationCode(randomCode);
		user.setEnabled(false);
		userService.saveUser(user);

		// get site URL (http:localhost:8080)
		String siteURL = request.getRequestURL().toString().replace(request.getServletPath(), "");
		userService.sendVerificationEmail(user, siteURL);

		// create doctor which is the user
		Doctor doctor = new Doctor();
		doctor.setUser(userService.findByEmail(doctorDTO.getEmail()));
		doctor.setClinic(clinicService.findById(doctorDTO.getClinicId()));
		doctor.setSpecialization(specializationService.findById(doctorDTO.getSpecializationId()));
		doctorService.save(doctor);

		return ResponseEntity.status(HttpStatus.CREATED).header("Location", "users/")
                .body("Add Account DocTor SuccessFully!");
	}
	
	
	
}
