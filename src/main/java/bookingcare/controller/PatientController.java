package bookingcare.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bookingcare.dto.request.ScheduleRequest;
import bookingcare.dto.response.ResponMessage;
import bookingcare.entity.Doctor;
import bookingcare.entity.Patient;
import bookingcare.entity.Schedule;
import bookingcare.entity.User;
import bookingcare.service.DoctorService;
import bookingcare.service.IUserService;
import bookingcare.service.PatientService;
import bookingcare.service.ScheduleService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
	 private PatientService patientService;
	    private IUserService userService;
	    private DoctorService doctorService;
	    private ScheduleService scheduleService;

	    @Autowired
	    public PatientController(PatientService patientService, IUserService userService, DoctorService doctorService, ScheduleService scheduleService) {
	        this.patientService = patientService;
	        this.userService = userService;
	        this.doctorService = doctorService;
	        this.scheduleService = scheduleService;
	    }
	    
	    // Patient set schedule with doctor
	    @PostMapping("/doctors/{doctorId}")
	    public ResponseEntity<?> addSchedule(Principal principal, @PathVariable Integer doctorId,
	                                         @RequestBody ScheduleRequest scheduleRequest) {
	        User user = userService.findByEmail(principal.getName());
	        Patient patient = patientService.findByUserId(user.getId());
	        
	        // if Patient does not exist, creat Patient
	        if (patient == null) {
	            patient = new Patient(user.getName(), null, null, null,user);
	        } else { // if patient existed, set Description
	            patient.setDescription(scheduleRequest.getDescription());
	        }
	        patientService.save(patient);

	        // creat Schedule and save to database(if status =0 is watting doctor confirm set status =1;
	        Doctor doctor = doctorService.findById(doctorId);
	        
	        Schedule schedule = new Schedule(scheduleRequest.getDate(), scheduleRequest.getTime(),
	                scheduleRequest.getDescription(), 0, doctor, patient,
	                doctor.getClinic(), doctor.getSpecialization());
	        scheduleService.save(schedule);
	        
	        return new ResponseEntity<>(new ResponMessage("set schedule successfull"), HttpStatus.OK);
	    }
}
