package petclinic.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import petclinic.model.Owner;
import petclinic.model.Pet;
import petclinic.services.PetService;
import petclinic.services.VisitService;

@ExtendWith(MockitoExtension.class)
public class VisitControllerTest {
	
	@InjectMocks
	VisitController visitController;
	
	@Mock
	PetService petService;
	
	@Mock
	VisitService visitService;

	MockMvc mockMvc;
	
	Pet pet;
	
	Owner owner;
	
	
	@BeforeEach
	void setUp() throws Exception {
		owner = Owner.builder().id(1L).build();
		pet = Pet.builder().id(1L).owner(owner).build();
		
		mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
	}
	
	
	@Test
	void processNewVisitForm() throws Exception {
		when(petService.findById(anyLong())).thenReturn(pet);
		
		mockMvc.perform(post("/owners/1/pets/1/visits/new"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/1"));
		
		verify(visitService).save(ArgumentMatchers.any());
	}
}
