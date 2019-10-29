package petclinic.bootstrap;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import petclinic.model.Owner;
import petclinic.model.Pet;
import petclinic.model.PetType;
import petclinic.model.Speciality;
import petclinic.model.Vet;
import petclinic.services.OwnerService;
import petclinic.services.PetTypeService;
import petclinic.services.SpecialityService;
import petclinic.services.VetService;

@Component
public class DataLoader implements CommandLineRunner {
	
	private final OwnerService ownerService;
	
	private final VetService vetService;
	
	private final PetTypeService petTypeService;
	
	private final SpecialityService specialityService;
	

	public DataLoader(OwnerService ownerService, 
			VetService vetService,
			PetTypeService petTypeService,
			SpecialityService specialityService) {
		this.ownerService = ownerService;
		this.vetService = vetService;
		this.petTypeService = petTypeService;
		this.specialityService = specialityService;
	}
	

	@Override
	public void run(String... args) throws Exception {
		int count = petTypeService.findAll().size();
		
		if(count == 0) {
			loadData();
		}
	}


	private void loadData() {
		PetType dog = new PetType();
		dog.setName("Dog");
		PetType savedDogPetType = petTypeService.save(dog);
		
		PetType cat = new PetType();
		dog.setName("Cat");
		PetType savedCatPetType = petTypeService.save(cat);
		
		
		Speciality radiology = new Speciality();
		radiology.setDescription("Radiology");
		Speciality savedRadiology = specialityService.save(radiology);
		
		Speciality surgery = new Speciality();
		surgery.setDescription("Surgery");
		Speciality savedSurgery = specialityService.save(surgery);
		
		Speciality dentistry = new Speciality();
		dentistry.setDescription("Dentistry");
		Speciality savedDentistry = specialityService.save(dentistry);
		
		
		Owner owner1 = new Owner();
		owner1.setFirstName("Michael");
		owner1.setLastName("Weston");
		owner1.setAddress("123 Brickerel");
		owner1.setCity("Miami");
		owner1.setTelephone("1234567890");
		
		Pet mikesPet = new Pet();
		mikesPet.setPetType(savedDogPetType);
		mikesPet.setOwner(owner1);
		mikesPet.setBirthday(LocalDate.now());
		mikesPet.setName("Rosco");
		owner1.getPets().add(mikesPet);
		
		ownerService.save(owner1);
		
		
		Owner owner2 = new Owner();
		owner2.setFirstName("Fiona");
		owner2.setLastName("Glenanne");
		owner2.setAddress("123 Brickerel");
		owner2.setCity("Miami");
		owner2.setTelephone("1234567890");
		
		Pet fionasPet = new Pet();
		fionasPet.setPetType(savedCatPetType);
		fionasPet.setOwner(owner2);
		fionasPet.setBirthday(LocalDate.now());
		fionasPet.setName("Rosco");
		owner2.getPets().add(fionasPet);
		
		ownerService.save(owner2);
		
		System.out.println("Loaded owners");
		
		
		Vet vet1 = new Vet();
		vet1.setFirstName("Sam");
		vet1.setLastName("Axie");
		vet1.getSpecialities().add(savedRadiology);
		
		vetService.save(vet1);
		
		
		Vet vet2 = new Vet();
		vet2.setFirstName("Jessie");
		vet2.setLastName("Porter");
		vet2.getSpecialities().add(savedSurgery);
		
		vetService.save(vet2);
		
		System.out.println("Loaded vets");
	}
	
}
