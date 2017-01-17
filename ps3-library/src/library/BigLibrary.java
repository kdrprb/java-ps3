package library;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * BigLibrary represents a large collection of books that might be held by a city or
 * university library system -- millions of books.
 * 
 * In particular, every operation needs to run faster than linear time (as a function of the number of books
 * in the library).
 */
public class BigLibrary implements Library {

    // TODO: rep
    HashMap<Book,HashSet<BookCopy>> checkedIn;
    HashSet<BookCopy> allCheckedInCopies;
    HashMap<Book,HashSet<BookCopy>> checkedOut;
    HashSet<BookCopy> allCheckedOutCopies;
    
    // TODO: rep invariant
    // TODO: abstraction function
    // TODO: safety from rep exposure argument

    // Rep invariant:
    //   BigLibrary is mutable
    //   BigLibrary data is stored in to HashMaps: checkedIn and checkedOut
    //   Every book edition (i.e. Book) is a key in both HashMaps
    //   Every physical book (i.e. BookCopy) is an element in the mapped Set of BookCopies
    //   If the book is available / checked in, it belongs in checkedIn
    //   If the booked is unavailable / check out, it belongs in checkedOut
    // Abstraction Function:
    //   represents a collection of books
    //   each physical book is represented by a BookCopy
    //   the edition of each BookCopy is represented by Book
    //   there can be many copies (BookCopy) of an edition (Book)
    // Safety from rep exposure:
    //   none of the mutator method directly access the rep
    
    
    public BigLibrary() {
        //throw new RuntimeException("not implemented yet");
        checkedIn = new HashMap<Book,HashSet<BookCopy>>();
        allCheckedInCopies = new HashSet<BookCopy>();
        checkedOut = new HashMap<Book,HashSet<BookCopy>>();
        allCheckedOutCopies = new HashSet<BookCopy>();
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        //throw new RuntimeException("not implemented yet");
        for (Book b : checkedIn.keySet())
        {
            assertTrue("checkedin book missing from checkedout books", checkedOut.keySet().contains(b));
            for (BookCopy bc : checkedIn.get(b))
            {
                assertTrue("bookcopy missing from allcheckedInCopies list", allCheckedInCopies.contains(bc));
            }
        }
        for (Book b : checkedOut.keySet())
        {
            assertTrue("checkedout book missing from checkedin books", checkedIn.keySet().contains(b));
            for (BookCopy bc : checkedOut.get(b))
            {
                assertTrue("bookcopy missing from allcheckedOutCopies list", allCheckedOutCopies.contains(bc));
            }
        }
        
        for (BookCopy b : allCheckedInCopies)
        {
            assertFalse("allCheckedOutCopies should not contain this inLibrary book: ", allCheckedOutCopies.contains(b));
        }
        for (BookCopy b : allCheckedOutCopies)
        {
            assertFalse("allCheckedInCopies should not contain this checkedOut book: ", allCheckedInCopies.contains(b));
        }

    }

    @Override
    public BookCopy buy(Book book) {
        //throw new RuntimeException("not implemented yet");
        //System.out.println("started buy operation");
        if(!checkedIn.containsKey(book)) checkedIn.put(book, new HashSet<BookCopy>());
        //System.out.println("created new checkedIn entry");
        if(!checkedOut.containsKey(book)) checkedOut.put(book, new HashSet<BookCopy>());
        //System.out.println("created new checkedOut entry");
        
        /*checkedIn.containsKey(book);
        checkedIn.get(book);
        checkedIn.put(book,null);
        checkedOut.containsKey(book);
        checkedOut.get(book);
        checkedOut.put(book,null);*/

        BookCopy newBC = new BookCopy(book);
        
        
        /*allCheckedInCopies.contains(newBC);
        allCheckedInCopies.add(newBC);
        allCheckedOutCopies.contains(newBC);
        allCheckedOutCopies.add(newBC);*/

        
        checkedIn.get(book).add(newBC);
        //System.out.println("added BookCopy to checkedIn");
        allCheckedInCopies.add(newBC);
        //System.out.println("added BookCopy to allCheckedInCopies");

        checkRep();
        return newBC;
    }
    
    @Override
    public void checkout(BookCopy copy) {
        //throw new RuntimeException("not implemented yet");
        
        Book b = copy.getBook();
        checkedIn.get(b).remove(copy);
        checkedOut.get(b).add(copy);
        
        allCheckedInCopies.remove(copy);
        allCheckedOutCopies.add(copy);
        
        checkRep();
    }
    
    @Override
    public void checkin(BookCopy copy) {
        //throw new RuntimeException("not implemented yet");
        
        Book b = copy.getBook();
        checkedOut.get(b).remove(copy);
        checkedIn.get(b).add(copy);
        
        allCheckedOutCopies.remove(copy);
        allCheckedInCopies.add(copy);
        
        checkRep();
    }
    
    @Override
    public Set<BookCopy> allCopies(Book book) {
        //throw new RuntimeException("not implemented yet");
        
        HashSet<BookCopy> toReturn = new HashSet<BookCopy>();
        if(checkedIn.containsKey(book)) toReturn.addAll(checkedIn.get(book));
        if(checkedOut.containsKey(book)) toReturn.addAll(checkedOut.get(book));
        return toReturn;
    }

    @Override
    public Set<BookCopy> availableCopies(Book book) {
        //throw new RuntimeException("not implemented yet");

        HashSet<BookCopy> toReturn = new HashSet<BookCopy>();        
        if(checkedIn.containsKey(book)) toReturn.addAll(checkedIn.get(book));
        return toReturn;
    }
    
    @Override
    public boolean isAvailable(BookCopy copy) {
        //throw new RuntimeException("not implemented yet");
        return allCheckedInCopies.contains(copy);
    }
    
    @Override
    public List<Book> find(String query) {
        //throw new RuntimeException("not implemented yet");
        TreeMap<Integer,List<Book>> myBooksByYear = new TreeMap<Integer,List<Book>>();
        
        boolean foundBook;
        for (Book b : checkedIn.keySet())
        {
            foundBook = false;
            //System.out.println("Current Book: " + b.toString());
            if(b.getTitle().equals(query)) foundBook=true;
            //if(foundBook) System.out.println("book matched on title");            
            else
            {
                for(String author : b.getAuthors())
                {
                    if(author.equals(query)) foundBook=true;                    
                }
            }
            
            if(foundBook)
            {
                Integer yr = new Integer(b.getYear());
                if(myBooksByYear.get(yr) != null) 
                {
                    myBooksByYear.get(yr).add(b);
                }
                else
                {
                    List<Book> newList = new ArrayList<Book>();
                    newList.add(b);
                    myBooksByYear.put(yr, newList);
                }
            }
        }// end for (Book b : checkedIn.keySet())
        
        List<Book> myList = new ArrayList<Book>();
        Iterator<Integer> i = myBooksByYear.keySet().iterator();
        while(i.hasNext())
        {
            Integer currentYear = i.next();
            //System.out.println("current year = " + currentYear);
            List<Book> currentList = myBooksByYear.get(currentYear);
            for(Book b: currentList) myList.add(b);
        }
        
        Collections.reverse(myList);
        //System.out.println("Found Book List: " + myList.toString());
        return myList;

    }
    
    @Override
    public void lose(BookCopy copy) {
        //throw new RuntimeException("not implemented yet");
        
        if(allCheckedInCopies.contains(copy))
        {
            allCheckedInCopies.remove(copy);
            checkedIn.get(copy.getBook()).remove(copy);
        }
        else
        {
            allCheckedOutCopies.remove(copy);
            checkedOut.get(copy.getBook()).remove(copy);
        }
        
    }

    // uncomment the following methods if you need to implement equals and hashCode,
    // or delete them if you don't
    // @Override
    // public boolean equals(Object that) {
    //     throw new RuntimeException("not implemented yet");
    // }
    // 
    // @Override
    // public int hashCode() {
    //     throw new RuntimeException("not implemented yet");
    // }


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
