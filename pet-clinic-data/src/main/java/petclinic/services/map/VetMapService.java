package petclinic.services.map;

import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import petclinic.model.Speciality;
import petclinic.model.Vet;
import petclinic.services.SpecialityService;
import petclinic.services.VetService;

@Service
@Profile({"default", "map"})
public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {

	private final SpecialityService specialityService;
	
	public VetMapService(SpecialityService specialityService) {
		this.specialityService = specialityService;
	}
	
	
	@Override
	public Vet save(Vet object) {
		if(object.getSpecialities().size() > 0) {
			object.getSpecialities().forEach(speciality -> {
				if(speciality.getId() == null) {
					Speciality savedSpeciality = specialityService.save(speciality);
					speciality.setId(savedSpeciality.getId());
				}
			});
		}
		
		return super.save(object);
	}

	
	@Override
	public Set<Vet> findAll() {
		return super.findAll();
	}
	

	@Override
	public Vet findById(Long id) {
		return super.findById(id);
	}

	
	@Override
	public void deleteById(Long id) {
		super.deleteById(id);
	}

	
	@Override
	public void delete(Vet object) {
		super.delete(object);
	}
}
