package petclinic.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import petclinic.model.Pet;
import petclinic.model.Visit;
import petclinic.services.PetService;
import petclinic.services.VisitService;

@RequestMapping("/owners/{ownerId}/pets/{petId}")
@Controller
public class VisitController {

	private final VisitService visitService;

	private final PetService petService;

	public VisitController(VisitService visitService, PetService petService) {
		this.visitService = visitService;
		this.petService = petService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("visit")	//Add attribute visit to all controllers
	public Visit loadPetWithVisit(@PathVariable Long petId, Model model) {
		Pet pet = petService.findById(petId);
		model.addAttribute("pet", pet);
		
		Visit visit = new Visit();
		pet.getVisits().add(visit);
		visit.setPet(pet);
		
		return visit;
	}


	@GetMapping("/visits/new")
	public String initNewVisitForm(@PathVariable Long petId, 
			Model model) {
		return "pets/createOrUpdateVisitForm";
	}

	
	@PostMapping("/visits/new")
	public String processNewVisitForm(@Valid Visit visit, 
			Pet pet,
			BindingResult result) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateVisitForm";
		}
		
		visitService.save(visit);
		return "redirect:/owners/" + pet.getOwner().getId();
	}
}
