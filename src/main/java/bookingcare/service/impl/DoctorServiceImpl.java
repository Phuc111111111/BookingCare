package bookingcare.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookingcare.entity.Doctor;
import bookingcare.entity.Patient;
import bookingcare.exception.DoctorNotFoundException;
import bookingcare.repository.DoctorRepository;
import bookingcare.service.DoctorService;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService{
    private DoctorRepository doctorRepository;
    private JavaMailSender mailSender;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, JavaMailSender mailSender) {
        this.doctorRepository = doctorRepository;
        this.mailSender = mailSender;
    }

    @Override
    @Transactional
    public List<Doctor> findBySpecializationId(Integer id) {
        return doctorRepository.findBySpecializationId(id);
    }

    @Override
    @Transactional
    public Doctor findById(Integer id) {
        Optional<Doctor> result = doctorRepository.findById(id);
        Doctor doctor = null;

        if (result.isPresent()) {
            doctor = result.get();
        } else {
            throw new DoctorNotFoundException("Did not find doctor id - " + id);
        }
        return doctor;
    }

    @Override
    @Transactional
    public Doctor findByUserId(Integer id) {
        return doctorRepository.findByUserId(id);
    }

    @Override
    @Transactional
    public void save(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Override
    public void sendEmailToPatient(Doctor doctor, Patient patient) throws MessagingException, UnsupportedEncodingException {
        String fromAddress = doctor.getUser().getEmail();
        String senderName = "Doctor Care";
        String toAddress = patient.getUser().getEmail();
        String subject = "Thông tin khám chữa bệnh";

        String content = "Dear " + patient.getUser().getName() + ",<br>"
                + "Dưới đây là thông tin khám chữa bệnh của bạn:<br>"
                + "Tình trạng: "+ patient.getDescription() +"<br>"
                + "Bác sĩ " + doctor.getUser().getName() + "<br>"
                + "Doctor Care";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

	@Override
	public List<Patient> findPatientByIdDoctor(Integer doctorId) {
		// TODO Auto-generated method stub
		return doctorRepository.findPatientByIdDoctor(doctorId);
	}
    
    
}
