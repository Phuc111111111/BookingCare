package bookingcare.service.impl;



import bookingcare.entity.Clinic;
import bookingcare.repository.ClinicRepository;
import bookingcare.service.ClinicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicServiceImpl implements ClinicService{
    private ClinicRepository clinicRepository;

    @Autowired
    public ClinicServiceImpl(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    @Override
    @Transactional
    public Clinic findById(Integer id) {
        Optional<Clinic> result = clinicRepository.findById(id);
        Clinic clinic = null;

        if (result.isPresent()) {
            clinic = result.get();
        } else {
            throw new RuntimeException("Did not find clinic id - " + id);
        }
        return clinic;
    }

    @Override
    @Transactional
    public List<Clinic> findByAddress(String address) {
        return clinicRepository.findByAddress(address, Sort.by("id"));
    }


}
