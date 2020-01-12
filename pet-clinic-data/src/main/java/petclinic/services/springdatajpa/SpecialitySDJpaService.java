package petclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import petclinic.model.Speciality;
import petclinic.repositories.SpecialityRepository;
import petclinic.services.SpecialityService;

@Service
@Profile("springdatajpa")
public class SpecialitySDJpaService implements SpecialityService {

	private final SpecialityRepository speciailtyRepository;
	
	
	public SpecialitySDJpaService(SpecialityRepository speciailtyRepository) {
		this.speciailtyRepository = speciailtyRepository;
	}

	@Override
	public Set<Speciality> findAll() {
		Set<Speciality> specialities = new HashSet<>();
		
		speciailtyRepository.findAll().forEach(specialities::add);
		
		return specialities;
	}

	@Override
	public Speciality findById(Long id) {
		return speciailtyRepository.findById(id).orElse(null);
	}

	@Override
	public Speciality save(Speciality object) {
		return speciailtyRepository.save(object);
	}

	@Override
	public void delete(Speciality object) {
		speciailtyRepository.delete(object);
	}

	@Override
	public void deleteById(Long id) {
		speciailtyRepository.deleteById(id);
	}

}
