package detailedTests;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.SortedMap;

import org.junit.Before;
import org.junit.Test;

import it.polito.library.LibException;
import it.polito.library.LibraryManager;

public class TestR3_Book_Donations {
	
    private LibraryManager lib;
	
	@Before
	public void setUp() {
		lib = new LibraryManager();
		lib.addBook("Cindarella");
	}
	
	
	@Test
	public void testReceiveDonation() throws LibException {
		
        lib.receiveDonation("Beauty and the Beast,Cindarella,Snowhite");
	    
		SortedMap<String, Integer> titles = lib.getTitles();
		Set<String> books = lib.getBooks();
		
		assertNotNull("Missing titles", titles);
		assertNotNull("Missing books", books);

		assertEquals("Wrong number of titles after donation", 3, titles.size());
	    assertEquals("Wrong number of books after donation",4, books.size());
	    	    
	    assertEquals("Wrong available book", "1000", lib.getAvailableBook("Cindarella"));
	    assertEquals("Wrong available book","1001", lib.getAvailableBook("Beauty and the Beast"));
	    assertEquals("Wrong available book","1003", lib.getAvailableBook("Snowhite"));
	}
	
}
