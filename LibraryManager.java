package it.polito.library;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

///// ALL DONE!!! ALL REQUIREMENTS SATISFIED WOOOOHHOOOOO
/// FINISHED COMPLETELY

public class LibraryManager {
	    
    // R1: Readers and Books 
    
    /**
	 * adds a book to the library archive
	 * The method can be invoked multiple times.
	 * If a book with the same title is already present,
	 * it increases the number of copies available for the book
	 * 
	 * @param title the title of the added book
	 * @return the ID of the book added 
	 */
	Map<String, Book> books = new HashMap<>(); 
	Map<String, Copy> copies = new HashMap<>();
	SortedMap<String, Integer> titlesCopies = new TreeMap<>();
	int id = 1000;
    public String addBook(String title) {
		if (!books.containsKey(title)){
			Book b = new Book(title);
			books.put(title, b);
			titlesCopies.put(title, 1);
			Copy c = new Copy(title, String.valueOf(id), books.get(title));
			b.copies.put(c.id, c);
			b.availableCopies.put(c.id, c);
			copies.put(c.id, c);
			id++;
			return c.id;
		}
		else {
			Copy c = new Copy(title, String.valueOf(id), books.get(title));
			copies.put(c.id, c);
			int a = titlesCopies.get(title) + 1;
			books.get(title).copies.put(c.id, c);
			books.get(title).availableCopies.put(c.id, c);
			titlesCopies.put(title, a);
			id++;
			return c.id;
		}
    }
    
    /**
	 * Returns the book titles available in the library
	 * sorted alphabetically, each one linked to the
	 * number of copies available for that title.
	 * 
	 * @return a map of the titles liked to the number of available copies
	 */
    public SortedMap<String, Integer> getTitles() {    	
        return titlesCopies;
    }
    
    /**
	 * Returns the books available in the library
	 * 
	 * @return a set of the titles liked to the number of available copies
	 */
    public Set<String> getBooks() {    	    	
        return copies.keySet();
    }
    
    /**
	 * Adds a new reader
	 * 
	 * @param name first name of the reader
	 * @param surname last name of the reader
	 */
	Map<String, Reader> readers = new HashMap<>();
	int readerId = 1000;
    public void addReader(String name, String surname) {
		Reader r = new Reader(name, surname, String.valueOf(readerId));
		readers.put(r.readerId, r);
		readerId++;		
    }
    
    
    /**
	 * Returns the reader name associated to a unique reader ID
	 * 
	 * @param readerID the unique reader ID
	 * @return the reader name
	 * @throws LibException if the readerID is not present in the archive
	 */
    public String getReaderName(String readerID) throws LibException {
		if (!readers.containsKey(readerID)){
			throw new LibException();
		}
		Reader r = readers.get(readerID);
        return r.name+" "+r.surname;
    }    
    
    
    // R2: Rentals Management
    
    
    /**
	 * Retrieves the bookID of a copy of a book if available
	 * 
	 * @param bookTitle the title of the book
	 * @return the unique book ID of a copy of the book or the message "Not available"
	 * @throws LibException  an exception if the book is not present in the archive
	 */

    public String getAvailableBook(String bookTitle) throws LibException {
		if (!books.containsKey(bookTitle)){
			throw new LibException();
		}
		if (books.get(bookTitle).availableCopies.isEmpty()){
			return "Not available";
		}
        else {
			String str = null;
			for (String s: books.get(bookTitle).availableCopies.keySet()) {
				str = s;
			}
			return str;
		}
    }   

    /**
	 * Starts a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param startingDate the starting date of the rental
	 * @throws LibException  an exception if the book copy or the reader are not present in the archive,
	 * if the reader is already renting a book, or if the book copy is already rented
	 */
	public void startRental(String bookID, String readerID, String startingDate) throws LibException {
		if (!copies.containsKey(bookID) || !readers.containsKey(readerID)){
			throw new LibException();
		}
		Reader r = readers.get(readerID);
		Copy c = copies.get(bookID);
		if (r.rentalStatus == true){
			throw new LibException();
		}
		else if ( c.rentalStatus == true){
			throw new LibException();
		}
		else {
			r.rentalStatus = true; c.rentalStatus = true;
			c.startingDate = startingDate;
			c.pastreaders.put(r, startingDate+" ONGOING");
			c.currentReader = readers.get(readerID);
			r.noOfRentals += 1;
			books.get(c.title).availableCopies.remove(c.id);
		}
    }
    
	/**
	 * Ends a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param endingDate the ending date of the rental
	 * @throws LibException  an exception if the book copy or the reader are not present in the archive,
	 * if the reader is not renting a book, or if the book copy is not rented
	 */
    public void endRental(String bookID, String readerID, String endingDate) throws LibException {
		if (!copies.containsKey(bookID) || !readers.containsKey(readerID)){
			throw new LibException();
		}
		Reader r = readers.get(readerID);
		Copy c = copies.get(bookID);
		if (r.rentalStatus == false || c.rentalStatus == false){
			throw new LibException();
		}
		else {
			r.rentalStatus = false; c.rentalStatus = false;
			c.pastreaders.put(r, c.startingDate+" "+endingDate);
			books.get(c.title).availableCopies.put(c.id, c);
		}
    }
    
    
   /**
	* Retrieves the list of readers that rented a specific book.
	* It takes a unique book ID as input, and returns the readers' reader IDs and the starting and ending dates of each rental
	* 
	* @param bookID the unique book ID of the book copy
	* @return the map linking reader IDs with rentals starting and ending dates
	* @throws LibException  an exception if the book copy or the reader are not present in the archive,
	* if the reader is not renting a book, or if the book copy is not rented
	*/
    public SortedMap<String, String> getRentals(String bookID) throws LibException {
		if (!copies.containsKey(bookID)){
			throw new LibException();
		}
		SortedMap<String, String> ans = new TreeMap<>();
		for (Reader r : copies.get(bookID).pastreaders.keySet()) {
			ans.put(r.readerId, copies.get(bookID).pastreaders.get(r));
		}
        return ans;
	}
    
    
    // R3: Book Donations
    
    /**
	* Collects books donated to the library.
	* 
	* @param donatedTitles It takes in input book titles in the format "First title,Second title"
	*/
    public void receiveDonation(String donatedTitles) {
		String[] temp = donatedTitles.split(",");
		for (String s : temp) {
			addBook(s);
		}
    }
    
    // R4: Archive Management

    /**
	* Retrieves all the active rentals.
	* 
	* @return the map linking reader IDs with their active rentals

	*/
    public Map<String, String> getOngoingRentals() {
		Map<String, String> ans = new HashMap<>();
		for (Copy c : copies.values()) {
			if (c.rentalStatus == true){
				ans.put(c.currentReader.readerId, c.id);
			}
		}
        return ans;
    }
    
    /**
	* Removes from the archives all book copies, independently of the title, that were never rented.
	* 
	*/
    public void removeBooks() {
		Map<String, Copy> abc = new HashMap<>();
		for (Copy c : copies.values()) {
			if (!c.pastreaders.isEmpty()){
				abc.put(c.id, c);
			}
		}
		copies.clear();
		copies.putAll(abc);
    }
    	
    // R5: Stats
    
    /**
	* Finds the reader with the highest number of rentals
	* and returns their unique ID.
	* 
	* @return the uniqueID of the reader with the highest number of rentals
	*/
    public String findBookWorm() {
		int max = 0;
		String reader = null;
		for (Reader r : readers.values()) {
			if (r.noOfRentals > max){
				max = r.noOfRentals;
				reader = r.readerId;
			}
		}
        return reader;
    }
    
    /**
	* Returns the total number of rentals by title. 
	* 
	* @return the map linking a title with the number of rentals
	*/
    public Map<String,Integer> rentalCounts() {
		Map<String, Integer> ans = new HashMap<>();
		for (Copy c : copies.values()) {
			if (!ans.containsKey(c.title)){
				ans.put(c.title, c.pastreaders.size());
			}
			else{
				int a = ans.get(c.title);
				a += c.pastreaders.size();
				ans.put(c.title, a);
			}
		}
        return ans;
    }

}

