package detailedTests;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.SortedMap;

import org.junit.Before;
import org.junit.Test;

import it.polito.library.LibException;
import it.polito.library.LibraryManager;

public class TestR1_Readers_and_Books {
	
	private LibraryManager lib;
	
	@Before
	public void setUp() {
		lib = new LibraryManager();
	}
	
	@Test
	public void testAddBook() {
			
		String id = lib.addBook("Dance Dance Dance");
		assertEquals("Wrong id for first book",
				    "1000",id);
	    lib.addBook("Lolita");
	    lib.addBook("Master and Margarita");
	    lib.addBook("Lolita");
	    lib.addBook("Dance Dance Dance");
	    lib.addBook("Dance Dance Dance");
	    assertEquals("Wrong progressive id for book",
	    		    "1006",lib.addBook("Dance Dance Dance"));
		
	}
	
	@Test
	public void testGetTitles() {
		
		lib.addBook("Lolita");
	    lib.addBook("Lolita");
	    lib.addBook("Dance Dance Dance");

		SortedMap<String, Integer> titles = lib.getTitles();
	    assertNotNull("Missing titles", titles);
	    assertEquals(2, titles.size());
	    assertTrue(titles.containsKey("Dance Dance Dance"));
		
	}

	@Test
	public void testGetTitlesCount() {
		lib.addBook("Lolita");
	    lib.addBook("Lolita");
	    lib.addBook("Dance Dance Dance");

	    SortedMap<String, Integer> titles = lib.getTitles();
	    assertNotNull("Missing titles", titles);
	    Integer n = titles.get("Lolita");
	    assertNotNull("Missing title count", n);
	    assertEquals("Wrong book count for title", 2, n.intValue());
	}

	@Test
	public void testGetBooks() {
		
	    lib.addBook("Lolita");
	    lib.addBook("Lolita");
	    lib.addBook("Dance Dance Dance");

		Set<String> bookSet = lib.getBooks();	    
	    assertEquals(3, bookSet.size());
	    assertTrue("Missing first id in book set", bookSet.contains("1000"));
	    assertTrue("Missing last id in book set", bookSet.contains("1002"));
	}
	
	@Test
	public void testGetNoBooks() {
		
		Set<String> bookSet = lib.getBooks();	    
	    assertEquals("Expected no books", 0, bookSet.size());
	    
	}
	
	@Test
	public void testReaders() throws LibException {
		
		lib.addReader("Maria", "Verdi");
	    lib.addReader("Gianni", "Fidenza");
	    lib.addReader("Maria", "Lonza");
		
    	assertEquals("Wrong id for first reader", 
    				"Maria Verdi", lib.getReaderName("1000"));
	    assertEquals("Wrong id for reader", 
					"Gianni Fidenza", lib.getReaderName("1001"));
	    assertEquals("Wrong id for reader", 
					"Maria Lonza", lib.getReaderName("1002"));
    }

	@Test
	public void testReadersSameName() throws LibException {
	    lib.addReader("Gianni", "Fidenza");
	    lib.addReader("Gianni", "Fidenza");
		
	    assertEquals("Wrong id for reader", 
				    "Gianni Fidenza", lib.getReaderName("1000"));
	    assertEquals("Wrong id for reader", 
				    "Gianni Fidenza", lib.getReaderName("1001"));
    }

	@Test
	public void testReadersExcept() {
		lib.addReader("Maria", "Verdi");
	    lib.addReader("Gianni", "Fidenza");
	    lib.addReader("Maria", "Lonza");
	    lib.addReader("Gianni", "Fidenza");
	    
	    assertThrows("Reader IDs not present in the archive must throw an exception",
	       LibException.class, ()-> lib.getReaderName("1004"));
	}
	

}
