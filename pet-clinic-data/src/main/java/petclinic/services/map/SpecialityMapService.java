package petclinic.services.map;

import java.util.Set;

import org.springframework.stereotype.Service;

import petclinic.model.Speciality;
import petclinic.services.SpecialitiesService;

@Service
public class SpecialityMapService extends AbstractMapService<Speciality, Long> 
	implements SpecialitiesService {

	public Set<Speciality> findAll() {
		return super.findAll();
	}

	public Speciality findById(Long id) {
		return super.findById(id);
	}

	public Speciality save(Speciality object) {
		return super.save(object);
	}

	public void deleteById(Long id) {
		super.deleteById(id);
	}

	public void delete(Speciality object) {
		super.delete(object);
	}

}
