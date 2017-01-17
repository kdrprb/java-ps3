package library;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Test suite for Book ADT.
 */
public class BookTest {

    /*
     * Testing strategy
     * ==================
     * 
     * TODO: your testing strategy for this ADT should go here.
     * Make sure you have partitions.
     * 
     * public String getTitle(): 
     *  verify that title attribute is not mutable
     *  verify that title is stored correctly
     * 
     * public List<String> getAuthors(): 
     *  # authors: 0, 1, 2+
     *  verify that authors attribute is not mutable
     *  verify that author order is maintained
     * 
     * public int getYear(): 
     *  verify that year is not mutable
     *  verify that year is stored correctly
     * 
     * public String toString():
     *  objects: same, different
     * 
     */
    

    
    // TODO: put JUnit @Test methods here that you developed from your testing strategy
    @Test
    public void testExampleTest() {
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        assertEquals("This Test Is Just An Example", book.getTitle());
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testTitleDefensiveCopy() {
        String titleToModify = "A";
        Book book = new Book(titleToModify, Arrays.asList("author1"), 1900);
        String toString1 = book.toString();
        titleToModify = "B";
        String toString2 = book.toString();        
        assertTrue("title defensive copy failed", toString1.equals(toString2));
    }

    @Test
    public void testTitleMutability() {
        Book book = new Book("A", Arrays.asList("author1"), 1900);
        String toString1 = book.toString();
        @SuppressWarnings("unused")
        String titleToModify = book.getTitle();
        titleToModify = "B";
        String toString2 = book.toString();        
        assertTrue("title immutability violated", toString1.equals(toString2));
    }

    @Test
    public void testTitle() {
        Book book = new Book("Here is my TITLE!", Arrays.asList("author1"), 1900);
        String myTitle = book.getTitle();
        assertTrue("title stored incorrectly", myTitle.equals("Here is my TITLE!"));
    }

    @Test
    public void testYearDefensiveCopy() {
        int yearToModify = 1900;
        Book book = new Book("testTitle", Arrays.asList("author1"), yearToModify);
        String toString1 = book.toString();
        yearToModify = 2000;
        String toString2 = book.toString();        
        assertTrue("year defensive copy failed", toString1.equals(toString2));
    }

    @Test
    public void testYearMutability() {
        Book book = new Book("testTitle", Arrays.asList("author1"), 1900);
        String toString1 = book.toString();
        @SuppressWarnings("unused")
        int yearToModify = book.getYear();
        yearToModify = 2000;
        String toString2 = book.toString();        
        assertTrue("year immutability violated", toString1.equals(toString2));
    }

    @Test
    public void testYear() {
        Book book = new Book("testTitle", Arrays.asList("author1"), 1900);
        int myYear = book.getYear();
        assertTrue("year stored incorrectly", myYear == 1900);
    }

    @Test
    public void testAuthorsDefensiveCopy() {
        ArrayList<String> a = new ArrayList<String>();
        a.add("author1");
        Book book = new Book("testTitle", a, 1995);
        String toString1 = book.toString();
        a.add("author2");
        String toString2 = book.toString();        
        assertTrue("author defensive copy failed", toString1.equals(toString2));
    }

    @Test
    public void testAuthorsMutability() {
        Book book = new Book("testTitle", Arrays.asList("author1"), 1995);
        String toString1 = book.toString();
        ArrayList<String> a = (ArrayList<String>)book.getAuthors();
        a.add("author2");
        a.remove(0);
        String toString2 = book.toString();        
        assertTrue("author immutability violated", toString1.equals(toString2));
    }

    @Test
    public void testSingleAuthor() {
        Book book = new Book("testTitle", Arrays.asList("author1"), 1995);
        String firstAuthor = book.getAuthors().get(0);
        assertTrue("single author test failed", firstAuthor.equals("author1"));
    }

    @Test
    public void testMultipleAuthors() {
        Book book = new Book("testTitle", Arrays.asList("author1","author2","author3"), 1995);
        List<String> myAuthors = book.getAuthors();
        String author1 = myAuthors.get(0);
        String author2 = myAuthors.get(1);
        String author3 = myAuthors.get(2);
        assertTrue("multiple author test failed: number of authors", myAuthors.size()==3);
        assertTrue("multiple author test failed: author1", author1.equals("author1"));
        assertTrue("multiple author test failed: author2", author2.equals("author2"));
        assertTrue("multiple author test failed: author3", author3.equals("author3"));
    }

    @Test
    public void testToString() {
        Book book1 = new Book("testTitle", Arrays.asList("author1","author2","author3"), 1995);
        assertTrue("toString() does not contain title", book1.toString().contains("testTitle"));
        assertTrue("toString() does not contain author1", book1.toString().contains("author1"));
        assertTrue("toString() does not contain author2", book1.toString().contains("author2"));
        assertTrue("toString() does not contain author3", book1.toString().contains("author3"));
        assertTrue("toString() does not contain year", book1.toString().contains("1995"));
    }

    @Test
    public void testToStringSame() {
        Book book1 = new Book("testTitle", Arrays.asList("author1","author2","author3"), 1995);
        Book book2 = new Book("testTitle", Arrays.asList("author1","author2","author3"), 1995);
        assertTrue("same book should produce same toString()", book1.toString().equals(book2.toString()));
    }

    @Test
    public void testToStringDifferentTitle() {
        Book book1 = new Book("testTitle", Arrays.asList("author1","author2","author3"), 1995);
        Book book2 = new Book("anotherTitle", Arrays.asList("author1","author2","author3"), 1995);
        assertFalse("different title should result in different toString()", book1.toString().equals(book2.toString()));
    }

    @Test
    public void testToStringTitleCapitalization() {
        Book book1 = new Book("title", Arrays.asList("author1","author2","author3"), 1995);
        Book book2 = new Book("TITLE", Arrays.asList("author1","author2","author3"), 1995);
        assertFalse("title capitalization should result in different toString()", book1.toString().equals(book2.toString()));
    }

    @Test
    public void testToStringDifferentAuthors() {
        Book book1 = new Book("title", Arrays.asList("author1","author2","author3"), 1995);
        Book book2 = new Book("title", Arrays.asList("author1","random author","author3"), 1995);
        assertFalse("title capitalization should result in different toString()", book1.toString().equals(book2.toString()));
    }

    @Test
    public void testToStringAuthorCapitalization() {
        Book book1 = new Book("title", Arrays.asList("author1","author2","author3"), 1995);
        Book book2 = new Book("title", Arrays.asList("author1","AUTHOR2","author3"), 1995);
        assertFalse("title capitalization should result in different toString()", book1.toString().equals(book2.toString()));
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
