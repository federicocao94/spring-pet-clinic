package petclinic.repositories;

import org.springframework.data.repository.CrudRepository;

import petclinic.model.Pet;

public interface PetRepository extends CrudRepository<Pet, Long> {

}
