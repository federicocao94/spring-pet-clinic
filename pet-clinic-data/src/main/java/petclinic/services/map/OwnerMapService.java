package petclinic.services.map;

import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import petclinic.model.Owner;
import petclinic.model.Pet;
import petclinic.services.OwnerService;
import petclinic.services.PetService;
import petclinic.services.PetTypeService;

@Service
@Profile({"default", "map"})
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

	private final PetTypeService petTypeService;
	private final PetService petService;
	
	public OwnerMapService(PetTypeService petTypeService, PetService petService) {
		this.petTypeService = petTypeService;
		this.petService = petService;
	}

	@Override
	public Set<Owner> findAll() {
		return super.findAll();
	}

	@Override
	public Owner findById(Long id) {
		return super.findById(id);
	}

	@Override
	public Owner save(Owner object) {		
		if(object != null) {
			if(object.getPets() != null) {
				object.getPets().forEach(pet -> {
					if(pet.getPetType() != null) {
						if(pet.getPetType().getId() == null) {
							pet.setPetType(petTypeService.save(pet.getPetType()));
						}
					} else {
						throw new RuntimeException("PetType is required.");
					}
					
					if(pet.getId() == null) {
						Pet savedPet = petService.save(pet);
						pet.setId(savedPet.getId());
					}
				});
			}
			
			return super.save(object);
		} else {
			return null;
		}
	}

	@Override
	public void deleteById(Long id) {
		super.deleteById(id);
	}

	@Override
	public void delete(Owner object) {
		super.delete(object);
	}

	@Override
	public Owner findByLastName(String lastName) {
		return this.findAll()
			.stream()
			.filter(owner -> owner.getLastName().equalsIgnoreCase(lastName))
			.findFirst()
			.orElse(null);
	}
	
	@Override
	public List<Owner> findAllByLastNameLike(String lastName) {
		List<Owner> ownersList = this.findAll()
				.stream()
				.filter(owner -> owner.getLastName().toLowerCase().contains(lastName.toLowerCase()))
				.collect(Collectors.toList());
		
		return ownersList;
	}

}
