package library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test suite for Library ADT.
 */
@RunWith(Parameterized.class)
public class LibraryTest {

    /*
     * Note: all the tests you write here must be runnable against any
     * Library class that follows the spec.  JUnit will automatically
     * run these tests against both SmallLibrary and BigLibrary.
     */

    /**
     * Implementation classes for the Library ADT.
     * JUnit runs this test suite once for each class name in the returned array.
     * @return array of Java class names, including their full package prefix
     */
    @Parameters(name="{0}")
    public static Object[] allImplementationClassNames() {
        return new Object[] { 
            "library.SmallLibrary", 
            "library.BigLibrary"
        }; 
    }

    /**
     * Implementation class being tested on this run of the test suite.
     * JUnit sets this variable automatically as it iterates through the array returned
     * by allImplementationClassNames.
     */
    @Parameter
    public String implementationClassName;    

    /**
     * @return a fresh instance of a Library, constructed from the implementation class specified
     * by implementationClassName.
     */
    public Library makeLibrary() {
        try {
            Class<?> cls = Class.forName(implementationClassName);
            return (Library) cls.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    /*
     * Testing strategy
     * ==================
     * 
     * TODO: your testing strategy for this ADT should go here.
     * Make sure you have partitions.
     * 
     * public BookCopy buy(Book book): 
     *  verify new book is available for checkout
     * 
     * public void checkout(BookCopy copy):
     *  verify that copy is no longer available for checkout
     * 
     * public void checkin(BookCopy copy):
     *  verify that copy is now available for checkin
     * 
     * 
     * public boolean isAvailable(BookCopy copy):
     *  verify that checkout removes copy from available
     *  verify that checkin adds copy to available
     * 
     * 
     * public Set<BookCopy> allCopies(Book book):
     *  # copies: 0, 1+
     *  all checked-in / all checked-out / mix
     * 
     * public Set<BookCopy> availableCopies(Book book):
     * # copies: 0, 1+
     * For 0: all checked-out / no copies exist
     * 
     * 
     * public List<Book> find(String query):
     * # found: 0, 1+
     * For 1+: all checked-in / all checked-out / mix
     * 
     * public void lose(BookCopy copy):
     * copy checked-in / copy checked-out
     * 
     */
    
    
    
    // TODO: put JUnit @Test methods here that you developed from your testing strategy
    @Test
    public void testExampleTest() {
        Library library = makeLibrary();
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        assertEquals(Collections.emptySet(), library.availableCopies(book));
    }
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testBuy() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("author1","author2"), 1990);
        //System.out.println("created new book");
        BookCopy bc = library.buy(book);
        //System.out.println("added new copy to library");
        assertTrue("buy failed for book: " + book.toString(), library.allCopies(book).size() == 1);
        //System.out.println("checked all copies");
        assertTrue("buy failed for book: " + book.toString(), library.availableCopies(book).size() == 1);
        //System.out.println("checked available copies");
        assertTrue("buy failed for book: " + book.toString(), library.isAvailable(bc));
        //System.out.println("checked this copy is available");
    }
    
    
    @Test
    public void testCheckout1BookLibrary() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("author1","author2"), 1990);
        BookCopy bc = library.buy(book);
        library.checkout(bc);
        assertTrue("checkout failed for book: " + book.toString(), library.allCopies(book).size() == 1);
        assertTrue("checkout failed for book: " + book.toString(), library.availableCopies(book).size() == 0);
        assertFalse("checkout failed for book: " + book.toString(), library.isAvailable(bc));
    }
    
    @Test
    public void testCheckoutManyBookLibrary() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("author1","author2"), 1990);
        Book book2 = new Book("B", Arrays.asList("author3","author4"), 1995);
        BookCopy bc = library.buy(book);
        library.buy(book);
        library.buy(book2);
        library.buy(book2);
        library.checkout(bc);
        assertTrue("checkout failed for book: " + book.toString(), library.allCopies(book).size() == 2);
        assertTrue("checkout failed for book: " + book.toString(), library.availableCopies(book).size() == 1);
        assertFalse("checkout failed for book: " + book.toString(), library.isAvailable(bc));
    }
    
    @Test
    public void testCheckin1BookLibrary() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("author1","author2"), 1990);
        BookCopy bc = library.buy(book);
        library.checkout(bc);
        library.checkin(bc);
        assertTrue("checkin failed for book: " + book.toString(), library.allCopies(book).size() == 1);
        assertTrue("checkin failed for book: " + book.toString(), library.availableCopies(book).size() == 1);
        assertTrue("checkin failed for book: " + book.toString(), library.isAvailable(bc));
    }
    
    
    @Test
    public void testCheckinManyBookLibrary() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("author1","author2"), 1990);
        Book book2 = new Book("B", Arrays.asList("author3","author4"), 1995);
        BookCopy bc = library.buy(book);
        library.buy(book);
        BookCopy bc2 = library.buy(book2);
        library.buy(book2);
        library.checkout(bc);
        library.checkout(bc2);
        library.checkin(bc);
        assertTrue("checkin failed for book: " + book.toString(), library.allCopies(book).size() == 2);
        assertTrue("checkin failed for book: " + book.toString(), library.availableCopies(book).size() == 2);
        assertTrue("checkin failed for book: " + book.toString(), library.isAvailable(bc));
    }
    
    @Test
    public void testAllCopies1BookLibrary() {
        Library library = makeLibrary();
        Book book = new Book("My Title", Arrays.asList("author1","author2"), 2005);
        BookCopy bc = library.buy(book);
        Set<BookCopy> bcSet = library.allCopies(book);
        assertTrue("allcopies() failed for book: " + book.toString(), library.allCopies(book).size() == 1);
        assertTrue("allcopies() failed for book: " + book.toString(), bcSet.contains(bc));
    }
    
    @Test
    public void testAllCopiesMissingBookLibrary() {
        Library library = makeLibrary();
        Book book = new Book("My Title", Arrays.asList("author1","author2"), 1999);
        Book book2 = new Book("Missing Title", Arrays.asList("author3","author4"), 2100);
        library.buy(book);
        BookCopy bc2 = new BookCopy(book2);
        Set<BookCopy> bcSet = library.allCopies(book);
        assertTrue("allcopies() failed for book: " + book2.toString(), library.allCopies(book2).size() == 0);
        assertFalse("allcopies() failed for book: " + book2.toString(), bcSet.contains(bc2));
    }
    
    @Test
    public void testAllCopiesAllCheckedOut() {
        Library library = makeLibrary();
        Book book = new Book("My Title", Arrays.asList("author1","author2"), 2005);
        BookCopy bc1 = library.buy(book);
        BookCopy bc2 = library.buy(book);
        BookCopy bc3 = library.buy(book);
        library.checkout(bc1);
        library.checkout(bc2);
        library.checkout(bc3);
        Set<BookCopy> bcSet = library.allCopies(book);
        assertTrue("allcopies() failed for book: " + book.toString(), library.allCopies(book).size() == 3);
        assertTrue("allcopies() failed for book: " + book.toString(), bcSet.contains(bc1));
        assertTrue("allcopies() failed for book: " + book.toString(), bcSet.contains(bc2));
        assertTrue("allcopies() failed for book: " + book.toString(), bcSet.contains(bc3));
    }
    
    
    @Test
    public void testAllCopiesMixedCheckout() {
        Library library = makeLibrary();
        Book book = new Book("My Title", Arrays.asList("author1","author2"), 2005);
        BookCopy bc1 = library.buy(book);
        BookCopy bc2 = library.buy(book);
        BookCopy bc3 = library.buy(book);
        BookCopy bc4 = library.buy(book);
        library.checkout(bc1);
        library.checkout(bc3);
        Set<BookCopy> bcSet = library.allCopies(book);
        assertTrue("allcopies() failed for book: " + book.toString(), library.allCopies(book).size() == 4);
        assertTrue("allcopies() failed for book: " + book.toString(), bcSet.contains(bc1));
        assertTrue("allcopies() failed for book: " + book.toString(), bcSet.contains(bc2));
        assertTrue("allcopies() failed for book: " + book.toString(), bcSet.contains(bc3));
        assertTrue("allcopies() failed for book: " + book.toString(), bcSet.contains(bc4));
    }
    
    
    @Test
    public void testAvailableCopiesAllCheckout() {
        Library library = makeLibrary();
        Book book = new Book("My Title", Arrays.asList("author1","author2"), 2005);
        BookCopy bc1 = library.buy(book);
        BookCopy bc2 = library.buy(book);
        BookCopy bc3 = library.buy(book);
        BookCopy bc4 = library.buy(book);
        library.checkout(bc1);
        library.checkout(bc2);
        library.checkout(bc3);
        library.checkout(bc4);
        Set<BookCopy> bcSet = library.availableCopies(book);
        assertTrue("availableCopies() failed for book: " + book.toString(), library.allCopies(book).size() == 4);
        assertTrue("availableCopies() failed for book: " + book.toString(), bcSet.size() == 0);
    }
    
    @Test
    public void testFindNoBooksFound() {
        Library library = makeLibrary();
        Book book = new Book("My Title", Arrays.asList("author1","author2"), 2005);
        library.buy(book);
        List<Book> bookList = library.find("hello");
        assertTrue("find() failed for book: " + book.toString(), bookList.size() == 0);
    }

    @Test
    public void testFindManyCopies1BookFound() {
        Library library = makeLibrary();
        Book book = new Book("My Title", Arrays.asList("author1","author2"), 2005);
        library.buy(book);
        library.buy(book);
        library.buy(book);
        library.buy(book);
        List<Book> bookList = library.find("My Title");
        assertTrue("find() failed for book: " + book.toString(), bookList.size() == 1);
        assertTrue("find() failed for book: " + book.toString(), bookList.contains(book));
    }
    
    @Test
    public void testFindManyBooksFound() {
        Library library = makeLibrary();
        Book book1 = new Book("My Title", Arrays.asList("author1","author2"), 2005);
        library.buy(book1);
        Book book2 = new Book("My Title", Arrays.asList("author1","author2"), 2004);
        BookCopy bc2 = library.buy(book2);
        library.checkout(bc2);
        Book book3 = new Book("My Title", Arrays.asList("author1","author2"), 2003);
        library.buy(book3);
        Book book4 = new Book("My Title", Arrays.asList("author1","author2"), 2002);
        BookCopy bc4 = library.buy(book4);
        library.checkout(bc4);
        List<Book> bookList = library.find("My Title");
        assertTrue("find() failed", bookList.size() == 4);
        assertTrue("find() failed to find book1", bookList.get(0) == book1);
        assertTrue("find() failed to find book2", bookList.get(1) == book2);
        assertTrue("find() failed to find book3", bookList.get(2) == book3);
        assertTrue("find() failed to find book4", bookList.get(3) == book4);
    }
    
    @Test
    public void testLoseBookCopyfromLibrary() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("author1","author2"), 1990);
        Book book2 = new Book("B", Arrays.asList("author3","author4"), 1995);
        BookCopy bc = library.buy(book);
        library.buy(book);
        BookCopy bc2 = library.buy(book2);
        library.buy(book2);
        
        library.checkout(bc);
        library.lose(bc2);
        
        assertTrue("lose failed for book: " + book2.toString(), library.allCopies(book2).size() == 1);
        assertTrue("lose failed for book: " + book2.toString(), library.availableCopies(book2).size() == 1);
        assertFalse("lose failed for book: " + book2.toString(), library.isAvailable(bc2));
    }

    @Test
    public void testLoseBookCopyCheckedOut() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("author1","author2"), 1990);
        Book book2 = new Book("B", Arrays.asList("author3","author4"), 1995);
        BookCopy bc = library.buy(book);
        library.buy(book);
        library.buy(book2);
        library.buy(book2);
        library.checkout(bc);
        library.lose(bc);
        assertTrue("checkout failed for book: " + book.toString(), library.allCopies(book).size() == 1);
        assertTrue("checkout failed for book: " + book.toString(), library.availableCopies(book).size() == 1);
        assertFalse("checkout failed for book: " + book.toString(), library.isAvailable(bc));
    }

    

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
