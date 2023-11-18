package bookingcare.service;


import java.util.List;

import bookingcare.entity.Clinic;

public interface ClinicService {
    Clinic findById(Integer id);

    List<Clinic> findByAddress(String address);
}
