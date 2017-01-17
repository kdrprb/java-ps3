package library;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/** 
 * SmallLibrary represents a small collection of books, like a single person's home collection.
 */
public class SmallLibrary implements Library {

    // This rep is required! 
    // Do not change the types of inLibrary or checkedOut, 
    // and don't add or remove any other fields.
    // (BigLibrary is where you can create your own rep for
    // a Library implementation.)

    // rep
    private Set<BookCopy> inLibrary;
    private Set<BookCopy> checkedOut;
    
    // rep invariant:
    //    the intersection of inLibrary and checkedOut is the empty set
    //
    // abstraction function:
    //    represents the collection of books inLibrary union checkedOut,
    //      where if a book copy is in inLibrary then it is available,
    //      and if a copy is in checkedOut then it is checked out

    // TODO: safety from rep exposure argument

    // Safety from rep exposure:
    //   none of the mutator method directly access the rep
    
    public SmallLibrary() {
        //throw new RuntimeException("not implemented yet");
        inLibrary = (Set<BookCopy>) new HashSet<BookCopy>();
        checkedOut = (Set<BookCopy>) new HashSet<BookCopy>();
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        //throw new RuntimeException("not implemented yet");
        for (BookCopy b : inLibrary)
        {
            assertFalse("checkedout should not contain this inLibrary book: " + b.toString(), checkedOut.contains(b));
        }
        for (BookCopy b : checkedOut)
        {
            assertFalse("library should not contain this checkedOut book: " + b.toString(), inLibrary.contains(b));
        }
    }

    @Override
    public BookCopy buy(Book book) {
        //throw new RuntimeException("not implemented yet");
        BookCopy bc = new BookCopy(book);
        inLibrary.add(bc);
        return bc;
    }
    
    @Override
    public void checkout(BookCopy copy) {
        //throw new RuntimeException("not implemented yet");
        inLibrary.remove(copy);
        checkedOut.add(copy);
        checkRep();
    }
    
    @Override
    public void checkin(BookCopy copy) {
        //throw new RuntimeException("not implemented yet");
        checkedOut.remove(copy);
        inLibrary.add(copy);
        checkRep();
    }
    
    @Override
    public boolean isAvailable(BookCopy copy) {
        //throw new RuntimeException("not implemented yet");
        return inLibrary.contains(copy);
    }
    
    @Override
    public Set<BookCopy> allCopies(Book book) {
        //throw new RuntimeException("not implemented yet");
        Set<BookCopy> mySet = new HashSet<BookCopy>();
        for (BookCopy b : inLibrary)
        {
            if(b.getBook().equals(book)) mySet.add(b);
        }
        for (BookCopy b : checkedOut)
        {
            if(b.getBook().equals(book)) mySet.add(b);
        }
        return mySet;
    }
    
    @Override
    public Set<BookCopy> availableCopies(Book book) {
        //throw new RuntimeException("not implemented yet");
        Set<BookCopy> mySet = new HashSet<BookCopy>();
        for (BookCopy b : inLibrary)
        {
            if(b.getBook().equals(book)) mySet.add(b);
        }
        return mySet;
    }

    @Override
    public List<Book> find(String query) {
        //throw new RuntimeException("not implemented yet");
        TreeMap<Integer,List<Book>> myBooksByYear = new TreeMap<Integer,List<Book>>();
        List<Book> myList = new ArrayList<Book>();
        boolean foundBook;
        for (BookCopy bc : inLibrary)
        {
            Book b = bc.getBook();
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
        }

        for (BookCopy bc : checkedOut)
        {
            Book b = bc.getBook();
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
        }

        Iterator<Integer> i = myBooksByYear.keySet().iterator();
        while(i.hasNext())
        {
            Integer currentYear = i.next();
            //System.out.println("current year = " + currentYear);
            List<Book> currentList = myBooksByYear.get(currentYear);
            for(Book b: currentList) if(!myList.contains(b)) myList.add(b);
        }
        
        Collections.reverse(myList);
        //System.out.println("Found Book List: " + myList.toString());
        return myList;
    }
    
    @Override
    public void lose(BookCopy copy) {
        //throw new RuntimeException("not implemented yet");
        if(inLibrary.contains(copy)) inLibrary.remove(copy);
        else checkedOut.remove(copy);
        checkRep();
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
