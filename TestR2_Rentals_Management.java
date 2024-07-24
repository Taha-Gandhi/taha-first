package detailedTests;

import static org.junit.Assert.*;

import java.util.SortedMap;

import org.junit.Before;
import org.junit.Test;

import it.polito.library.LibException;
import it.polito.library.LibraryManager;

public class TestR2_Rentals_Management {

	
    private LibraryManager lib;
	
	@Before
	public void setUp() {
		lib = new LibraryManager();
		
    	lib.addBook("Dance Dance Dance");	// 1000
	    lib.addBook("Lolita");				// 1001
	    lib.addBook("Master and Margarita");// 1002
  	    lib.addBook("Dance Dance Dance");	// 1003

	    
		lib.addReader("Maria", "Verdi");		// 1000
	    lib.addReader("Gianni", "Fidenza");	// 1001
	    lib.addReader("Maria", "Lonza");		// 1002
	    lib.addReader("Gianni", "Fidenza");	// 1003
	}
	
    @Test
    public void testGetAvailableBook() throws LibException {
    	
		assertEquals("1000", lib.getAvailableBook("Dance Dance Dance"));
		assertEquals("1001", lib.getAvailableBook("Lolita"));
		assertEquals("1002", lib.getAvailableBook("Master and Margarita"));
    }
    
    @Test
    public void testGetAvailableBookExcept()  {
    	assertThrows("Books titles not in the archive must throw an exception",
    		LibException.class, ()-> lib.getAvailableBook("Iliade"));  	
    }
    
    @Test
    public void testStartRental() throws LibException {
		
	    assertEquals("1000", lib.getAvailableBook("Dance Dance Dance"));
		
	    lib.startRental("1000", "1000", "14-07-2023");
        assertEquals("1003", lib.getAvailableBook("Dance Dance Dance"));
        
        lib.startRental("1003", "1001", "14-07-2023");
        assertEquals("Not available", lib.getAvailableBook("Dance Dance Dance"));        
    }

    @Test
    public void testAvailEndRental() throws LibException {
		
		lib.startRental("1000", "1000", "14-07-2023");
        lib.startRental("1003", "1001", "14-07-2023");

        assertEquals("Not available", lib.getAvailableBook("Dance Dance Dance"));

        lib.endRental("1000", "1000","15-07-2023");        
        assertEquals("1000", lib.getAvailableBook("Dance Dance Dance"));
        
        lib.endRental("1003", "1001","15-07-2023");
        assertEquals("1000", lib.getAvailableBook("Dance Dance Dance"));        
    }

    @Test
    public void testEndRental2() throws LibException {   

	   lib.startRental("1001", "1000","15-07-2023");
	   assertEquals("Not available", lib.getAvailableBook("Lolita"));
	   lib.endRental("1001", "1000","15-07-2023");
	   assertEquals("1001", lib.getAvailableBook("Lolita"));

    }
       
    @Test
    public void testStartRentalUserOngoing() throws LibException {
    	
    	lib.startRental("1002", "1000", "14-07-2023");
        assertThrows("Readers with ongoing rentals must throw an exception", LibException.class,
        		    ()-> lib.startRental("1000", "1000", "14-07-2023"));
    }
    
    @Test
    public void testStartRentalBookOnRental() throws LibException {
    	
    	lib.startRental("1002", "1000", "14-07-2023");
        assertThrows("Books currently being rented must throw an exception", LibException.class,
        		    ()-> lib.startRental("1002", "1001", "14-07-2023"));    
    }

    @Test
    public void testStartRentalWrongReader() {
    	
        assertThrows("Reader IDs not present in the archive must throw an exception", LibException.class,
        		    ()-> lib.startRental("1000", "2000", "14-07-2023"));
    }

    @Test
    public void testStartRentalWrongBook() {

    	assertThrows("Books not present in the archive must throw an exception", LibException.class,
    			    ()-> lib.startRental("1024", "1000", "14-07-2023"));  
    
    }

    @Test
    public void testEndRentalWrongReader() {
    	assertThrows("Reader IDs not present in the archive must throw an exception", LibException.class, ()-> lib.endRental("1004", "1000", "14-07-2023"));  
    }

    @Test
    public void testEndRentalWrongBook()  {
    	assertThrows("Books not present in the archive must throw an exception", LibException.class, ()-> lib.endRental("1041", "1007", "14-07-2023"));
    }

    @Test
    public void testEndRentalBookNotRented() {
    	assertThrows("Books not rented must throw an exception", LibException.class, ()-> lib.endRental("1001", "1007", "14-07-2023"));
    }

    @Test
    public void testEndRentalNotRentingUser() {
    	assertThrows("Readers who are not renting must throw an exception", LibException.class, ()-> lib.endRental("1001", "1000", "14-07-2023"));
    }

    @Test
    public void testGetRentals() throws LibException {
    	
	    lib.startRental("1000", "1000", "10-07-2023");
        lib.endRental("1000", "1000", "11-07-2023");
        lib.startRental("1000", "1001", "10-05-2023");
        lib.endRental("1000", "1001", "11-06-2023");
        lib.startRental("1001", "1001", "10-05-2023");
        lib.endRental("1001", "1001", "11-06-2023");
          	
    	SortedMap<String, String> rentals = lib.getRentals("1001");
    	assertNotNull("Missing rentals", rentals);
        assertEquals("Wrong number of rentals", 1, rentals.size());
        
        rentals = lib.getRentals("1000");
    	assertNotNull("Missing rentals", rentals);
        assertEquals("Wrong number of rentals", 2, rentals.size());
        assertTrue("Missing reader id", rentals.containsKey("1000"));
        assertTrue("Missing reader id", rentals.containsKey("1001"));
    }
    
    @Test
    public void testGetRentalsDetails() throws LibException {
    	
        lib.startRental("1001", "1001", "10-05-2023");
        lib.endRental("1001", "1001", "11-06-2023");
          	
    	SortedMap<String, String> rentals = lib.getRentals("1001");
    	assertNotNull("Missing rentals", rentals);
        assertEquals("Wrong number of rentals", 1, rentals.size());
        assertTrue("Missing reader id", rentals.containsKey("1001"));
        assertEquals("Wrong rental period", "10-05-2023 11-06-2023", rentals.get("1001"));
        
    }

    @Test
    public void testGetRentalsDetailsOngoing() throws LibException {
    	
        lib.startRental("1002", "1001", "10-05-2023");
          	
    	SortedMap<String, String> rentals = lib.getRentals("1002");
    	assertNotNull("Missing rentals", rentals);
        assertEquals("Wrong number of rentals", 1, rentals.size());
        assertTrue("Missing reader id", rentals.containsKey("1001"));
        assertEquals("Wrong rental period", "10-05-2023 ONGOING", rentals.get("1001"));
        
    }
    
    @Test
    public void testGetRentalsExcept() {
    	
        assertThrows("Book ID not found must throw an exception", 
           LibException.class, ()-> lib.getRentals("1024"));

    }
}


