package petclinic.services;

import petclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {
	
	Owner findByLastName(String lastName);
	
}
