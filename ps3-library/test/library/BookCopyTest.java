package library;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

/**
 * Test suite for BookCopy ADT.
 */
public class BookCopyTest {

    /*
     * Testing strategy
     * ==================
     * 
     * TODO: your testing strategy for this ADT should go here.
     * Make sure you have partitions.
     * 
     * public Book getBook():
     *  verify that the Book is not mutable
     * 
     * 
     * public Condition getCondition():
     *  condition: good, damaged
     *  verify that condition is not exposed
     * 
     * public void setCondition(Condition condition):
     *   operations: good==>damaged, damaged==>good
     * 
     * 
     * public String toString():
     *  condition: same, different
     * 
     * 
     */

    
    // TODO: put JUnit @Test methods here that you developed from your testing strategy
    @Test
    public void testExampleTest() {
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = new BookCopy(book);
        assertEquals(book, copy.getBook());
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGetBookDefensiveCopy() {
        Book book = new Book("A", Arrays.asList("author1"), 1900);
        BookCopy bc = new BookCopy(book);
        String toString1 = bc.toString();
        book = new Book("B", Arrays.asList("author1"), 1900);
        String toString2 = bc.toString();        
        assertTrue("Book defensive copy failed", toString1.equals(toString2));
    }

    @Test
    public void testGetConditionRepExposure() {
        Book book = new Book("A", Arrays.asList("author1"), 1900);
        BookCopy bc = new BookCopy(book);
        @SuppressWarnings("unused")
        BookCopy.Condition myCondition = bc.getCondition();
        myCondition = BookCopy.Condition.DAMAGED;
        assertFalse("GetCondition() Exposed Rep!", bc.getCondition() == BookCopy.Condition.DAMAGED);
    }

    @Test
    public void testGetSetConditionGoodBad() {
        Book book = new Book("A", Arrays.asList("author1"), 1900);
        BookCopy bc = new BookCopy(book);
        assertTrue("Book Condition Good Failed", bc.getCondition().equals(BookCopy.Condition.GOOD));
        bc.setCondition(BookCopy.Condition.DAMAGED);
        assertTrue("Book Condition Bad Failed", bc.getCondition().equals(BookCopy.Condition.DAMAGED));
        bc.setCondition(BookCopy.Condition.GOOD);
        assertTrue("Book Condition Good Failed", bc.getCondition().equals(BookCopy.Condition.GOOD));
    }

    @Test
    public void testSetConditionRepExposure() {
        BookCopy.Condition myCondition = BookCopy.Condition.GOOD;
        Book book = new Book("A", Arrays.asList("author1"), 1900);
        BookCopy bc = new BookCopy(book);
        bc.setCondition(myCondition);
        myCondition = BookCopy.Condition.DAMAGED;
        assertFalse("SetCondition() Exposed Rep!", bc.getCondition() == BookCopy.Condition.DAMAGED);
    }

    @Test
    public void testToString() {
        Book book1 = new Book("HELLO", Arrays.asList("my stupid author"), 1999);
        BookCopy bc1 = new BookCopy(book1);
        assertTrue("toString() does not contain initial condition (GOOD)", bc1.toString().contains(BookCopy.Condition.GOOD.toString()));
        assertTrue("toString() does not contain title", bc1.toString().contains("HELLO"));
        assertTrue("toString() does not contain my stupid author", bc1.toString().contains("my stupid author"));
        assertTrue("toString() does not contain year", bc1.toString().contains("1999"));
    }

    @Test
    public void testToStringSame1() {
        Book book1 = new Book("A", Arrays.asList("author1"), 1900);
        BookCopy bc1 = new BookCopy(book1);
        BookCopy bc2 = new BookCopy(book1);
        assertTrue("same book copy should produce same toString()", bc1.toString().equals(bc2.toString()));
    }

    @Test
    public void testToStringSame2() {
        Book book1 = new Book("A", Arrays.asList("author1"), 1900);
        Book book2 = new Book("A", Arrays.asList("author1"), 1900);
        BookCopy bc1 = new BookCopy(book1);
        BookCopy bc2 = new BookCopy(book2);
        assertTrue("same book copy should produce same toString()", bc1.toString().equals(bc2.toString()));
    }

    @Test
    public void testToStringDifferentBooks() {
        Book book1 = new Book("A", Arrays.asList("author1"), 1900);
        Book book2 = new Book("B", Arrays.asList("author2"), 1900);
        BookCopy bc1 = new BookCopy(book1);
        BookCopy bc2 = new BookCopy(book2);
        assertFalse("different book copy should produce different toString()", bc1.toString().equals(bc2.toString()));
    }

    @Test
    public void testToStringSameBookDifferentConditions() {
        Book book1 = new Book("A", Arrays.asList("author1"), 1900);
        BookCopy bc1 = new BookCopy(book1);
        BookCopy bc2 = new BookCopy(book1);
        bc2.setCondition(BookCopy.Condition.DAMAGED);
        assertFalse("different book copy should produce different toString()", bc1.toString().equals(bc2.toString()));
    }


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
