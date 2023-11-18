package bookingcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bookingcare.entity.Specialization;

import java.util.List;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    @Query("select s from Specialization s where s.name like %?1%")
    List<Specialization> findByName(String specializationName);
}
