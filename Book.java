package it.polito.library;

import java.util.HashMap;
import java.util.Map;

public class Book {
    String title;
    Map<String, Copy> copies = new HashMap<>();
    Map<String, Copy> availableCopies = new HashMap<>(); // string is the copy id
    public Book(String title) {
        this.title = title;
    }

}
