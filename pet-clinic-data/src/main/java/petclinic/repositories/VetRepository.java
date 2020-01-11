package petclinic.repositories;

import org.springframework.data.repository.CrudRepository;

import petclinic.model.Vet;

public interface VetRepository extends CrudRepository<Vet, Long> {

}
