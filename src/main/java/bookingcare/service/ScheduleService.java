package bookingcare.service;



import java.util.List;

import bookingcare.entity.Patient;
import bookingcare.entity.Schedule;

public interface ScheduleService {
    List<Integer> getTop5SpecializationId();

    List<Integer> getTop5ClinicId();

    void save(Schedule schedule);

    List<Integer> findByDoctorId(Integer id);

    List<Schedule> findScheduleByDoctorId(Integer id);

    Schedule findById(Integer scheduleId);

    List<Schedule> findByPatientId(Integer patientId);
    
     List<Patient> findPatientsByDoctorId(Integer doctorId);
}
