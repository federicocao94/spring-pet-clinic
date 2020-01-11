package petclinic.repositories;

import org.springframework.data.repository.CrudRepository;

import petclinic.model.Speciality;

public interface SpecialityRepository extends CrudRepository<Speciality, Long> {

}
