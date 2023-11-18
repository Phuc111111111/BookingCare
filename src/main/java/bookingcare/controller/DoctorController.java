package bookingcare.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import bookingcare.entity.Doctor;
import bookingcare.entity.Patient;
import bookingcare.entity.Schedule;
import bookingcare.entity.User;
import bookingcare.service.ClinicService;
import bookingcare.service.DoctorService;
import bookingcare.service.IUserService;
import bookingcare.service.PatientService;
import bookingcare.service.ScheduleService;
import bookingcare.service.SpecializationService;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
	
	private IUserService userService;
	private DoctorService doctorService;
	private PatientService patientService;
	private ClinicService clinicService;
	private ScheduleService scheduleService;
	private SpecializationService specializationService;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public DoctorController(IUserService userService, DoctorService doctorService, PatientService patientService,
			ScheduleService scheduleService, ClinicService clinicService,SpecializationService specializationService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.doctorService = doctorService;
		this.patientService = patientService;
		this.scheduleService = scheduleService;
		this.clinicService = clinicService;
		this.specializationService = specializationService;
		this.passwordEncoder = passwordEncoder;
	}
	 // Doctor get patient list
    @GetMapping("/patients")
    public ResponseEntity<?> getPatients(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Doctor doctor = doctorService.findByUserId(user.getId());
        List<Patient> patientList =patientService.findPatientsByDoctorId(doctor.getId());
        
        return ResponseEntity.ok(patientList);
    }
    
 // Doctor accept schedule with Patient
    @GetMapping("/accept/{scheduleId}")
    public ResponseEntity<?> acceptSchedule(@PathVariable Integer scheduleId, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Doctor doctor = doctorService.findByUserId(user.getId());
        Schedule schedule = scheduleService.findById(scheduleId);
        if(schedule.getDoctor().getId()!=doctor.getId()) return new ResponseEntity<>("Bạn không có lịch khám này!", HttpStatus.BAD_REQUEST);
        schedule.setStatus(1); // status = 1 : accepted
        scheduleService.save(schedule);
        return ResponseEntity.ok(schedule);
    }
    
 // Doctor cancel schedule with Patient
    @GetMapping("/cancel/{scheduleId}")
    public ResponseEntity<?> cancelSchedule(@PathVariable Integer scheduleId, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Doctor doctor = doctorService.findByUserId(user.getId());
        Schedule schedule = scheduleService.findById(scheduleId);
        if(schedule.getDoctor().getId()!=doctor.getId()) return new ResponseEntity<>("Bạn không có lịch khám này!", HttpStatus.BAD_REQUEST);
        schedule.setStatus(2); // status = 2 : cancel
        scheduleService.save(schedule);
        return ResponseEntity.ok(schedule);
    }
    // get list schedules of doctor
    @GetMapping("doctors/{doctorId}")
    public ResponseEntity<?> getScheduleOfDoctor(@PathVariable Integer doctorId) {
        List<Schedule> schedules = scheduleService.findScheduleByDoctorId(doctorId);
        if(schedules.isEmpty()) return new ResponseEntity<>("Not found any schedule with doctorId " + doctorId, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(schedules);
    }
    
    // doctor send email to patient
    @GetMapping("/email/patients/{patientId}")
    public ResponseEntity<?> sendEmailToPatient(@PathVariable Integer patientId, Principal principal) throws MessagingException, UnsupportedEncodingException {
        User user = userService.findByEmail(principal.getName());
        Doctor doctor = doctorService.findByUserId(user.getId());
        Patient patient = patientService.findById(patientId);
        doctorService.sendEmailToPatient(doctor, patient);
        return ResponseEntity.ok("Sent email successfully!");
    }
}
