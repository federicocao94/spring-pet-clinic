package petclinic.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import petclinic.model.Owner;
import petclinic.services.OwnerService;

@RequestMapping("/owners")
@Controller
public class OwnerController {
	
	private final OwnerService ownerService;
	
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	
	public OwnerController(OwnerService ownerService) {
		this.ownerService = ownerService;
	}
	
	
	@RequestMapping("/find")
	public String findOwners(Model model) {
		model.addAttribute("owner", Owner.builder().build());
		
		return "owners/findOwners";
	}
	
	
	@GetMapping
	public String processFindForm(Owner owner, BindingResult result, Model model) {
		//allow parameterless GET request for /owners to return all records
		if(owner.getLastName() == null) {
			owner.setLastName("");
		}
		
		List<Owner> ownersResults = ownerService.findAllByLastNameLike(owner.getLastName());
		
		if(ownersResults.isEmpty()) {
			result.rejectValue("lastName", "notFound", "not found");
			return "owners/findOwners";
			
		} else if(ownersResults.size() == 1) {
			owner = ownersResults.get(0);
			return "redirect:/owners/" + owner.getId();
			
		} else {
			model.addAttribute("selections", ownersResults);
			return "owners/ownersList";
		}
		
	}
	
	
	@GetMapping("/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		mav.addObject(ownerService.findById(ownerId));
		
		return mav;
	}
	
}