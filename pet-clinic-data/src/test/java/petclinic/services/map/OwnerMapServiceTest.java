package petclinic.services.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import petclinic.model.Owner;

class OwnerMapServiceTest {
	
	OwnerMapService ownerMapService;
	
	final Long ownerId = 1L;
	final String lastName = "Smith";
	

	@BeforeEach
	void setUp() throws Exception {
		ownerMapService = new OwnerMapService(new PetTypeMapService(), 
				new PetMapService());
		
		ownerMapService.save(Owner.builder()
				.id(ownerId)
				.lastName(lastName)
				.build());
	}
	

	@Test
	void testFindAll() {
		Set<Owner> ownerSet = ownerMapService.findAll();
		
		assertEquals(1, ownerSet.size());
	}
	

	@Test
	void testFindByIdLong() {
		Owner owner = ownerMapService.findById(ownerId);
		
		assertEquals(ownerId, owner.getId());
	}
	

	@Test
	void testSaveOwnerWithId() {
		Long id = 2L;
		Owner owner2 = Owner.builder().id(id).build();
		
		Owner savedOwner = ownerMapService.save(owner2);
		
		assertEquals(id, savedOwner.getId());
	}
	
	
	@Test
	void testSaveOwnerNoId() {
		Owner savedOwner = ownerMapService.save(Owner.builder().build());
		
		assertNotNull(savedOwner);
		assertNotNull(savedOwner.getId());
	}
	

	@Test
	void testDeleteByIdLong() {
		ownerMapService.deleteById(ownerId);
		
		assertEquals(0, ownerMapService.findAll().size());
	}

	
	@Test
	void testDeleteOwner() {
		ownerMapService.delete(ownerMapService.findById(ownerId));;
		
		assertEquals(0, ownerMapService.findAll().size());
	}
	

	@Test
	void testFindByLastName() {
		Owner smith = ownerMapService.findByLastName("Smith");
		
		assertNotNull(smith);
		
		smith = ownerMapService.findByLastName("foo");
		
		assertNull(smith);
	}

}
