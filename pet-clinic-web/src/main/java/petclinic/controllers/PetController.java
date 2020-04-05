package petclinic.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import petclinic.model.Owner;
import petclinic.model.Pet;
import petclinic.model.PetType;
import petclinic.services.OwnerService;
import petclinic.services.PetService;
import petclinic.services.PetTypeService;

@RequestMapping("/owners/{ownerId}")
@Controller
public class PetController {
	
	private final String VIEWS_PET_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
	
	private final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
	
	private final OwnerService ownerService;
	
	private final PetService petService;
	
	private final PetTypeService petTypeService;
	

	public PetController(OwnerService ownerService, 
			PetService petService,
			PetTypeService petTypeService) {
		
		this.ownerService = ownerService;
		this.petService = petService;
		this.petTypeService = petTypeService;
	}
	
	@ModelAttribute("types")	//Add attribute types to all controllers
	public Collection<PetType> populatePetTypes() {
		return petTypeService.findAll();
	}

	@ModelAttribute("owner")	//Add attribute owner to all controllers
	public Owner findOwner(@PathVariable Long ownerId) {
		return ownerService.findById(ownerId);
	}

	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	
	@GetMapping("/pets/new")
	public String initCreationForm(Owner owner, Model model) {
		Pet pet = new Pet();
		owner.getPets().add(pet);
		pet.setOwner(owner);
		
		model.addAttribute("pet", pet);
		
		return VIEWS_PET_CREATE_OR_UPDATE_FORM;
	}
	
	
	@PostMapping("/pets/new")
	public String processCreationForm(Owner owner,
			@Valid Pet pet,
			BindingResult result,
			Model model) {
		
		if(StringUtils.hasLength(pet.getName())
				&& pet.isNew()
				&& owner.getPet(pet.getName(), false) != null) {
			result.rejectValue("name", "duplicate", "already exists");
		}
		
		owner.getPets().add(pet);
		
		if(result.hasErrors()) {
			model.addAttribute("pet", pet);
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		
		pet.setOwner(owner);
		
		petService.save(pet);
		return "redirect:/owners/" + owner.getId();
	}
	
	
	@GetMapping("/pets/{petId}/edit")
	public String initUpdateForm(@PathVariable Long petId, Model model) {		
		model.addAttribute("pet", petService.findById(petId));
		
		return VIEWS_PET_CREATE_OR_UPDATE_FORM;
	}
	
	
	@PostMapping("/pets/{petId}/edit")
	public String processUpdateForm(Owner owner,
			@Valid Pet pet,
			BindingResult result,
			Model model) {
		
		if(result.hasErrors()) {
			pet.setOwner(owner);
			model.addAttribute("pet", pet);
			return VIEWS_PET_CREATE_OR_UPDATE_FORM;
		}
		
		pet.setOwner(owner);
		
		petService.save(pet);
		return "redirect:/owners/" + owner.getId();
	}
}
