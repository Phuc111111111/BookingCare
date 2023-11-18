package bookingcare.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bookingcare.entity.Patient;
import bookingcare.entity.Schedule;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query(value = "SELECT top(5) specialization_id FROM schedule GROUP BY specialization_id ORDER BY count(*) desc", nativeQuery = true)
    List<Integer> getTop5SpecializationId();

    @Query(value = "SELECT top(5) clinic_id FROM schedule GROUP BY clinic_id ORDER BY count(*) desc", nativeQuery = true)
    List<Integer> getTop5ClinicId();

    @Query(value = "SELECT distinct patient_id FROM schedule WHERE doctor_id = ?1", nativeQuery = true)
    List<Integer> findByDoctorId(Integer id);

    @Query("SELECT s FROM Schedule s WHERE s.doctor.id = ?1")
    List<Schedule> findScheduleByDoctorId(Integer id);

    @Query("SELECT s FROM Schedule s WHERE s.patient.id = ?1")
    List<Schedule> findByPatientId(Integer patientId);
    
    
    @Query(value = "SELECT p.* FROM doctor d join schedule s on d.id = s.doctor_id join patient p on  s.patient_id = p.id WHERE d.id = ?1", nativeQuery = true)
	List<Patient> findPatientByIdDoctor(Integer doctorId);
    
    @Query("SELECT s.patient FROM Schedule s WHERE s.doctor.id = :doctorId")
    List<Patient> findPatientsByDoctorId(Integer doctorId);
}
