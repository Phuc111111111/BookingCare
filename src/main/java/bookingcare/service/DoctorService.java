package bookingcare.service;


import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

import bookingcare.entity.Doctor;
import bookingcare.entity.Patient;


public interface DoctorService {

    List<Doctor> findBySpecializationId(Integer id);

    Doctor findById(Integer doctorId);

    Doctor findByUserId(Integer id);

    void save(Doctor doctor);
    
    List<Patient> findPatientByIdDoctor(Integer doctorId);

    void sendEmailToPatient(Doctor doctor, Patient patient) throws MessagingException, UnsupportedEncodingException;
}
