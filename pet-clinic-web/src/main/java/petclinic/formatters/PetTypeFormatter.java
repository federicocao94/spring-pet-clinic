package petclinic.formatters;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import petclinic.model.PetType;
import petclinic.services.PetTypeService;

@Component
public class PetTypeFormatter implements Formatter<PetType> {
	
	PetTypeService petTypeService;
	
	public PetTypeFormatter(PetTypeService petTypeService) {
		this.petTypeService = petTypeService;
	}

	@Override
	public String print(PetType petType, Locale locale) {
		return petType.getName();
	}
	
	@Override
	public PetType parse(String text, Locale locale) throws ParseException {
		Collection<PetType> petTypes = petTypeService.findAll();
		
		for(PetType type : petTypes) {
			if(type.getName().equals(text)) {
				return type;
			}
		}
		
		throw new ParseException("type not found" + text, 0);
	}
}
