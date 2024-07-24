package detailedTests;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import it.polito.library.LibException;
import it.polito.library.LibraryManager;

public class TestR4_Archive_Management {
	
    private LibraryManager lib;
	
	@Before
	public void setUp() {
		lib = new LibraryManager();

		lib.addBook("Dance Dance Dance");
	    lib.addBook("Lolita");
	    lib.addBook("Master and Margarita");
	    
	    lib.addReader("Maria", "Verdi");
	    lib.addReader("Gianni", "Fidenza");
	}

	@Test
	public void testGetOngoingRentalsNone() {
        Map<String, String> activeRentals = lib.getOngoingRentals();
	    assertNotNull("Missing active rentals", activeRentals);
	    assertEquals("There should be no ongoing rental", 0, activeRentals.size());	    
	}
	
	@Test
	public void testGetOngoingRentals() throws LibException {
        Map<String, String> activeRentals = lib.getOngoingRentals();
	    assertNotNull("Missing active rentals", activeRentals);
	    
	    lib.startRental("1002", "1001", "10-07-2023");
	    activeRentals = lib.getOngoingRentals();
	    
	    assertEquals("Wrong number of ongoing rentals", 1, activeRentals.size());
	    
	    lib.startRental("1001", "1000", "10-05-2023");
	    activeRentals = lib.getOngoingRentals();
	    
	    assertEquals("Wrong number of ongoing rentals",2, activeRentals.size());
	    
	    lib.endRental("1001", "1000", "11-06-2023");
	    lib.endRental("1002", "1001", "11-07-2023");
	    activeRentals = lib.getOngoingRentals();
	    
	    assertEquals("Wrong number of ongoing rentals",0, activeRentals.size());
		
	}

	@Test
	public void testGetOngoingRentalsDetails() throws LibException {
	    lib.startRental("1002", "1001", "10-07-2023");
	    lib.startRental("1001", "1000", "10-05-2023");
		Map<String, String> activeRentals = lib.getOngoingRentals();

		assertNotNull("Missing active rentals", activeRentals);
		
		assertTrue("Missing reader who rented", activeRentals.containsKey("1001"));
		assertEquals("Wong on rental book for reader", "1002", activeRentals.get("1001"));
	}

	@Test
	public void testRemoveBooks() throws LibException {
		
	    lib.startRental("1002", "1001", "10-07-2023");
	    lib.endRental("1002", "1001", "11-07-2023");
	    lib.startRental("1001", "1000", "10-05-2023");	    
	    lib.endRental("1001", "1000", "11-06-2023");
	    
		Set<String> bookSet = lib.getBooks();	    

		assertNotNull("Missing books", bookSet);
		assertEquals("Wrong number of books",3, bookSet.size());

		lib.removeBooks();
	    bookSet = lib.getBooks();

	    assertEquals(2, bookSet.size());		
	    assertFalse("Book should have been removed", bookSet.contains("Dance Dance Dance"));
	}
}
