package detailedTests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import it.polito.library.LibException;
import it.polito.library.LibraryManager;
public class TestR5_Stats {
	
private LibraryManager lib;
	
	@Before
	public void setUp() {
		lib = new LibraryManager();

		lib.addBook("Dance Dance Dance");
		lib.addBook("Dance Dance Dance");
	    lib.addBook("Lolita");
	    lib.addBook("Master and Margarita");

	    lib.addReader("Maria", "Verdi");
	    lib.addReader("Viola", "Verdi");
	    lib.addReader("Norberto", "Laguzzi");
	    lib.addReader("Gianni", "Fidenza");
	    lib.addReader("Maria", "Lonza");

	}
	
	
	@Test
	public void testFindBookWorm() throws LibException {
			    
	    lib.startRental("1002", "1000", "10-07-2023");
	    lib.endRental("1002", "1000", "11-07-2023");
	    lib.startRental("1001", "1000", "10-05-2023");	    
	    lib.endRental("1001", "1000", "11-06-2023");
	    lib.startRental("1002", "1001", "10-07-2020");
	    lib.endRental("1002", "1001", "11-07-2020");
	    lib.startRental("1001", "1000", "10-04-2023");	    
	    lib.endRental("1001", "1000", "11-04-2023");
	    lib.startRental("1002", "1003", "10-07-2021");
	    lib.endRental("1002", "1003", "11-07-2021");
	    lib.startRental("1001", "1001", "10-01-2023");	    
	    lib.endRental("1001", "1001", "11-01-2023");
	    
	    assertEquals("1000", lib.findBookWorm());	    
		
	}
	
	@Test
	public void testRentalCounts()  throws LibException {
		
	    lib.startRental("1000", "1000", "10-07-2023");
	    lib.endRental("1000", "1000", "11-07-2023");
	    lib.startRental("1001", "1001", "10-05-2023");	    
	    lib.endRental("1001", "1001", "11-06-2023");
	    lib.startRental("1001", "1002", "10-05-2023");	    
	    lib.endRental("1001", "1002", "11-06-2023");
	    lib.startRental("1002", "1000", "10-05-2023");	    
	    lib.endRental("1002", "1000", "11-06-2023");
	    lib.startRental("1002", "1001", "10-05-2023");	    
	    lib.endRental("1002", "1001", "11-06-2023");
	    
	    Map<String, Integer> rentalCounts = lib.rentalCounts();
	    
	    assertNotNull("Missing rental counts", rentalCounts);
	    assertEquals("Wrong number of titles in rental count", 3, rentalCounts.size());
	    assertTrue("Missing title in rental count", rentalCounts.containsKey("Dance Dance Dance"));
	    assertEquals(3, rentalCounts.get("Dance Dance Dance").intValue());	
		
	}

}
