package bookingcare.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookingcare.entity.Specialization;
import bookingcare.repository.SpecializationRepository;
import bookingcare.service.SpecializationService;

import java.util.List;
import java.util.Optional;

@Service
public class SpecializationServiceImpl implements SpecializationService{

    private SpecializationRepository specializationRepository;

    @Autowired
    public SpecializationServiceImpl(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    @Override
    @Transactional
    public Specialization findById(Integer id) {
        Optional<Specialization> result = specializationRepository.findById(id);
        Specialization specialization = null;

        if (result.isPresent()) {
            specialization = result.get();
        } else {
            throw new RuntimeException("Did not find specialization id - " + id);
        }
        return specialization;
    }

    @Override
    @Transactional
    public List<Specialization> findByName(String specializationName) {
        return specializationRepository.findByName(specializationName);
    }
}
