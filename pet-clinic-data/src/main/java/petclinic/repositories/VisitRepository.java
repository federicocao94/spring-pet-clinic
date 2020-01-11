package petclinic.repositories;

import org.springframework.data.repository.CrudRepository;

import petclinic.model.Visit;

public interface VisitRepository extends CrudRepository<Visit, Long> {

}
