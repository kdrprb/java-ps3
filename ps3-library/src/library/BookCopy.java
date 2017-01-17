package library;
import static org.junit.Assert.*;

/**
 * BookCopy is a mutable type representing a particular copy of a book that is held in a library's
 * collection.
 */
public class BookCopy {

    // TODO: rep
    private final Book b;
    private Condition c;
    
    // TODO: rep invariant
    // TODO: abstraction function
    // TODO: safety from rep exposure argument

    // Rep invariant:
    //   BookCopy is mutable
    //   It refers to an immutable book
    //   The condition may change over time
    // Abstraction Function:
    //   represents a copy of book B with condition C
    // Safety from rep exposure:
    //   All fields are private;
    //   Book reference is final
    //   The Book object itself is immutable;
    //   Condition is mutable but we make defensive copies to avoid rep exposure
    
    public static enum Condition {
        GOOD, DAMAGED
    };
    
    /**
     * Make a new BookCopy, initially in good condition.
     * @param book the Book of which this is a copy
     */
    public BookCopy(Book book) {
        //throw new RuntimeException("not implemented yet");
        b=book;
        c=Condition.GOOD;
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        //throw new RuntimeException("not implemented yet");
        assertFalse("book is null", b==null);
        assertTrue("condition is uninitialized", c.ordinal()>=0);
    }
    
    /**
     * @return the Book of which this is a copy
     */
    public Book getBook() {
        //throw new RuntimeException("not implemented yet");
        return b;
    }
    
    /**
     * @return the condition of this book copy
     */
    public Condition getCondition() {
        //throw new RuntimeException("not implemented yet");
        //return c;
        // use the line below if rep exposure occurs
        return Condition.valueOf(c.name());
    }

    /**
     * Set the condition of a book copy.  This typically happens when a book copy is returned and a librarian inspects it.
     * @param condition the latest condition of the book copy
     */
    public void setCondition(Condition condition) {
        //throw new RuntimeException("not implemented yet");
        //c=condition;
        // use the line below if rep exposure occurs
        c=Condition.valueOf(condition.name());
        checkRep();
    }
    
    /**
     * @return human-readable representation of this book that includes book.toString()
     *    and the words "good" or "damaged" depending on its condition
     */
    public String toString() {
        //throw new RuntimeException("not implemented yet");
        StringBuilder sb = new StringBuilder();
        sb.append(b.toString());
        sb.append("-- CONDITION: " + c);
        return sb.toString();
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
