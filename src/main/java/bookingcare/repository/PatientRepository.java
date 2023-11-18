package bookingcare.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bookingcare.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query("select p from Patient p where p.user.id = ?1")
    bookingcare.entity.Patient findByUserId(Integer id);
    
    @Query(value = "SELECT p.* FROM doctor d join schedule s on d.id = s.doctor_id join patient p on  s.patient_id = p.id WHERE d.id = ?1", nativeQuery = true)
   	List<Patient> findPatientByIdDoctor(Integer doctorId);
}
