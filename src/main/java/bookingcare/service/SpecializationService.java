package bookingcare.service;



import java.util.List;

import bookingcare.entity.Specialization;

public interface SpecializationService {
    Specialization findById(Integer id);

    List<Specialization> findByName(String specializationName);
}
