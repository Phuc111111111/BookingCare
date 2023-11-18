package bookingcare.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import bookingcare.entity.Clinic;
import bookingcare.entity.Doctor;
import bookingcare.entity.Specialization;
import bookingcare.service.ClinicService;
import bookingcare.service.DoctorService;
import bookingcare.service.IUserService;
import bookingcare.service.PatientService;
import bookingcare.service.ScheduleService;
import bookingcare.service.SpecializationService;

@RestController
@RequestMapping("/home")
public class HomeController {
	private IUserService userService;
	private DoctorService doctorService;
	private PatientService patientService;
	private ClinicService clinicService;
	private SpecializationService specializationService;
	private PasswordEncoder passwordEncoder;
	private ScheduleService scheduleService;

	@Autowired
	public HomeController(IUserService userService, DoctorService doctorService, PatientService patientService,
			ScheduleService scheduleService,ClinicService clinicService,SpecializationService specializationService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.doctorService = doctorService;
		this.patientService = patientService;
		this.clinicService = clinicService;
		this.scheduleService = scheduleService;
		this.specializationService = specializationService;
		this.passwordEncoder = passwordEncoder;
	}
	
	// Hiển thị thông tin của các cơ sở y tế nổi bật
	 @GetMapping("/topSpecialization")
	    public ResponseEntity<?> getTopSpecialization() {
		 List<Integer> top5SpecializationId = scheduleService.getTop5SpecializationId();
	        List<Specialization> specializations = new ArrayList<>();
	        for (int i=0; i<top5SpecializationId.size();i++) {
	            Specialization specialization = specializationService.findById(top5SpecializationId.get(i));
	            specializations.add(specialization);
	        }

	        return ResponseEntity.ok(specializations);
	    }
	 
	  @GetMapping("/topClinic")
	    public ResponseEntity<?> getTopClinic() {
	        List<Integer> top5ClinicId = scheduleService.getTop5ClinicId();
	        List<Clinic> clinics = new ArrayList<>();
	        for (int i=0; i<top5ClinicId.size();i++) {
	            Clinic clinic = clinicService.findById(top5ClinicId.get(i));
	            clinics.add(clinic);
	        }
	        return ResponseEntity.ok(clinics);
	    }

	  
	  // find doctor by specializationName
	    @GetMapping("/specializations/{specializationName}")
	    public ResponseEntity<?> findDoctorBySpecialization(@PathVariable String specializationName) {
	        List<Specialization> specializations = specializationService.findByName(specializationName);
	        List<Doctor> doctors = new ArrayList<>();
	        for (Specialization specialization: specializations) {
	            List<Doctor> doctorList = doctorService.findBySpecializationId(specialization.getId());
	            doctors.addAll(doctorList);
	        }
	        return ResponseEntity.ok(doctors);
	    }
	    //find clinics by address
	    @GetMapping()
	    public ResponseEntity<?> searchByAddress(@RequestParam("address") String address) {
	        List<Clinic> clinics = clinicService.findByAddress(address);

	        return ResponseEntity.ok(clinics);
	    }

}
