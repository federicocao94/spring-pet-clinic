package petclinic.services.springdatajpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import petclinic.model.Owner;
import petclinic.repositories.OwnerRepository;
import petclinic.repositories.PetRepository;
import petclinic.repositories.PetTypeRepository;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {
	
	@Mock
	OwnerRepository ownerRepository;
	
	@Mock
	PetRepository petRepository;
	
	@Mock
	PetTypeRepository petTypeRepository;
	
	@InjectMocks
	OwnerSDJpaService service;
	
	private static final String LAST_NAME = "Smith";
	
	Owner returnOwner;

	
	@BeforeEach
	void setUp() throws Exception {
		returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
	}
	

	@Test
	void testFindAll() {
		Set<Owner> owners = new HashSet<>();
		owners.add(Owner.builder().id(1L).build());
		owners.add(Owner.builder().id(2L).build());
		
		when(ownerRepository.findAll()).thenReturn(owners);
		
		Set<Owner> ownersFound = service.findAll();
		
		assertNotNull(ownersFound);
		assertEquals(2, ownersFound.size());
	}

	
	@Test
	void testFindById() {
		when(ownerRepository.findById(1L)).thenReturn(Optional.of(returnOwner));
		
		assertNotNull(service.findById(1L));
	}
	
	
	@Test
	void testFindByIdNotFound() {
		when(ownerRepository.findById(1L)).thenReturn(Optional.empty());
		
		assertNull(service.findById(1L));
	}

	
	@Test
	void testSave() {
		Owner ownerToSave = Owner.builder().id(1L).build();
		
		when(ownerRepository.save(ownerToSave)).thenReturn(returnOwner);
		
		assertNotNull(service.save(ownerToSave));
		verify(ownerRepository).save(any());
	}

	
	@Test
	void testDelete() {
		service.delete(returnOwner);
		
		verify(ownerRepository, times(1)).delete(any());
	}

	
	@Test
	void testDeleteById() {
		service.deleteById(1L);
		service.deleteById(2L);
		
		verify(ownerRepository, times(2)).deleteById(any());
	}

	
	@Test
	void testFindByLastName() {		
		when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);
		
		Owner owner = service.findByLastName(LAST_NAME);
		
		assertEquals(LAST_NAME, owner.getLastName());
		verify(ownerRepository).findByLastName(any());
	}

}
