package bookingcare.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bookingcare.entity.Doctor;
import bookingcare.entity.Patient;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    @Query("select d from Doctor d where d.specialization.id = ?1")
    List<Doctor> findBySpecializationId(Integer id);

    @Query("select d from Doctor d where d.user.id = ?1")
    Doctor findByUserId(Integer id);
    
    @Query(value = "SELECT p.name FROM doctor d join schedule s on d.id = s.doctor_id join patient p on  s.patient_id = p.id WHERE d.id = ?1", nativeQuery = true)
	List<Patient> findPatientByIdDoctor(Integer doctorId);
}
