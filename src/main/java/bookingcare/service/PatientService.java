package bookingcare.service;

import java.util.List;

import bookingcare.entity.Patient;

public interface PatientService {
    Patient findByUserId(Integer id);

    void save(Patient patient);

    Patient findById(Integer patientId);
    
    List<Patient> findPatientsByDoctorId(Integer doctorId);
}
