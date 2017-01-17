package library;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Book is an immutable type representing an edition of a book -- not the physical object, 
 * but the combination of words and pictures that make up a book.  Each book is uniquely
 * identified by its title, author list, and publication year.  Alphabetic case and author 
 * order are significant, so a book written by "Fred" is different than a book written by "FRED".
 */
public class Book {

    // TODO: rep
    private final String bookTitle;
    private final int bookYear;
    private final ArrayList<String> bookAuthors;
    
    // TODO: rep invariant
    // TODO: abstraction function
    // TODO: safety from rep exposure argument
    
    // Rep invariant:
    //   Book is immutable
    //   It is uniquely identified by 3 primary attributes:
    //      1) Title
    //      2) Year Published
    //      3) List of Authors
    //   Title must have 1+ non-space character
    //   There must be 1+ author
    //   Each author must have 1+ non-space character
    //   Alphabetic case matters i.e. "My Book Title" <> "MY BOOK TITLE"
    //   Order matters i.e. {Fred,George} <> {George,Fred}
    // Abstraction Function:
    //   represents a book with title T published by authors A in year Y
    // Safety from rep exposure:
    //   All fields are private;
    //   title is a String <== guaranteed immutable;
    //   year is an int primitive <== guaranteed immutable;
    //   authors is mutable but we make defensive copies to avoid rep exposure

    /**
     * Make a Book.
     * @param title Title of the book. Must contain at least one non-space character.
     * @param authors Names of the authors of the book.  Must have at least one name, and each name must contain 
     * at least one non-space character.
     * @param year Year when this edition was published in the conventional (Common Era) calendar.  Must be nonnegative. 
     */
    public Book(String title, List<String> authors, int year) {
        //throw new RuntimeException("not implemented yet");
        bookTitle = title;
        bookYear = year;
        bookAuthors = new ArrayList<String>();
        for(int i=0; i<authors.size(); i++)
        {
            bookAuthors.add(authors.get(i));
        }
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        //throw new RuntimeException("not implemented yet");
        assertTrue("year must be non-negative",bookYear>0);
        assertTrue("title must have 1+ non-space character", bookTitle.trim().length() > 0);
        assertTrue("book must have 1+ authors",bookAuthors.size()>0);
        for(int i=0; i<bookAuthors.size(); i++)
        {
            assertTrue("author name must have 1+ non-space character", bookAuthors.get(i).trim().length() > 0); 
        }

    }
    
    /**
     * @return the title of this book
     */
    public String getTitle() {
        //throw new RuntimeException("not implemented yet");
        return bookTitle;
    }
    
    /**
     * @return the authors of this book
     */
    @SuppressWarnings("unchecked")
    public List<String> getAuthors() {
        //throw new RuntimeException("not implemented yet");
        return (List<String>) bookAuthors.clone();
    }

    /**
     * @return the year that this book was published
     */
    public int getYear() {
        //throw new RuntimeException("not implemented yet");
        return bookYear;
    }

    /**
     * @return human-readable representation of this book that includes its title,
     *    authors, and publication year
     */
    public String toString() {
        //throw new RuntimeException("not implemented yet");
        StringBuilder sb = new StringBuilder();
        sb.append(bookTitle + " (" + bookYear + ") - by " + bookAuthors);
/*        for(int n=1; n<bookAuthors.size(); n++)
        {
            sb.append(", " + bookAuthors.get(n) );
        }
        sb.append("]");*/
        return sb.toString();
    }

    // uncomment the following methods if you need to implement equals and hashCode,
    // or delete them if you don't
/*    @Override
    public boolean equals(Object that) {
        //throw new RuntimeException("not implemented yet");
        if(!(that instanceof Book)) return false;
        //return this.toString().equals(thatBook.toString());
        
        Boolean isEqual=true;
        Book thatBook = (Book) that;
        if(this.getYear() != thatBook.getYear()) isEqual=false;
        if(!this.getTitle().equals(thatBook.getTitle())) isEqual=false;
        if(this.getAuthors().size() != thatBook.getAuthors().size()) isEqual=false;
        for(String a : this.getAuthors()) if(!thatBook.getAuthors().contains(a)) isEqual=false;
        return isEqual;
    }
    
    @Override
    public int hashCode() {
        //throw new RuntimeException("not implemented yet");
        return java.util.Objects.hashCode(this);
    }*/



    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
