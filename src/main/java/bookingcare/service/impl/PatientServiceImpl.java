package bookingcare.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookingcare.entity.Patient;
import bookingcare.exception.PatientNotFoundException;
import bookingcare.repository.PatientRepository;
import bookingcare.service.PatientService;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService{
    private PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    @Override
    @Transactional
    public Patient findByUserId(Integer id) {
        return patientRepository.findByUserId(id);
    }

    @Override
    @Transactional
    public void save(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    @Transactional
    public Patient findById(Integer id) {
        Optional<Patient> result = patientRepository.findById(id);
        Patient patient = null;

        if (result.isPresent()) {
            patient = result.get();
        } else {
            throw new PatientNotFoundException("Did not find patient id - " + id);
        }
        return patient;
    }


	@Override
	public List<Patient> findPatientsByDoctorId(Integer doctorId) {
		// TODO Auto-generated method stub
		return patientRepository.findPatientByIdDoctor(doctorId);
	}
    
}
