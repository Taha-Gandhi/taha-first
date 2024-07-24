package it.polito.library;

import java.util.HashMap;
import java.util.Map;

public class Copy {
    String id;
    String title;
    Book book;
    String startingDate; // used for  updating ending time in the map pastreaders
    Reader currentReader ; // used for R4
    Boolean rentalStatus = false;
    Map<Reader, String> pastreaders = new HashMap<>(); // string here is the starting & ending date
    //Map<Reader, String> pastReadersEndDate = new HashMap<>(); // string here is the end date
    
    public Copy(String title, String id, Book book) {
        this.title = title;
        this.book = book;
        this.id = id;
    }

}
