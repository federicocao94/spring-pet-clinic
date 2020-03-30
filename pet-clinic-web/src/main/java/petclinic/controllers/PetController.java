package petclinic.controllers;

import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import petclinic.model.Owner;
import petclinic.model.PetType;
import petclinic.services.OwnerService;
import petclinic.services.PetService;
import petclinic.services.PetTypeService;

@Controller
@RequestMapping("owners/{ownerId}")
public class PetController {
	
	private String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
	
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
	
	
}
