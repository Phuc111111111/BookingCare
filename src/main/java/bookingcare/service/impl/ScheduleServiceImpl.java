package bookingcare.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookingcare.entity.Patient;
import bookingcare.entity.Schedule;
import bookingcare.repository.ScheduleRepository;
import bookingcare.service.ScheduleService;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    private ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
    @Override
    @Transactional
    public List<Integer> getTop5SpecializationId() {
        return scheduleRepository.getTop5SpecializationId();
    }

    @Override
    @Transactional
    public List<Integer> getTop5ClinicId() {
        return scheduleRepository.getTop5ClinicId();
    }

    @Override
    @Transactional
    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Override
    @Transactional
    public List<Integer> findByDoctorId(Integer id) {
        return scheduleRepository.findByDoctorId(id);
    }

    @Override
    @Transactional
    public List<Schedule> findScheduleByDoctorId(Integer id) {
        return scheduleRepository.findScheduleByDoctorId(id);
    }

    @Override
    @Transactional
    public Schedule findById(Integer id) {
        Optional<Schedule> result = scheduleRepository.findById(id);
        Schedule schedule = null;

        if (result.isPresent()) {
            schedule = result.get();
        } else {
            throw new RuntimeException("Did not find schedule id - " + id);
        }
        return schedule;
    }

    @Override
    @Transactional
    public List<Schedule> findByPatientId(Integer patientId) {
        return scheduleRepository.findByPatientId(patientId);
    }
	@Override
	public List<Patient> findPatientsByDoctorId(Integer doctorId) {
		// TODO Auto-generated method stub
		return scheduleRepository.findPatientsByDoctorId(doctorId);
	}
    
    
}
