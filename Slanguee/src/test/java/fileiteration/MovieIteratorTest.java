package fileiteration;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import cc.languee.fileiteration.Movie;
import cc.languee.fileiteration.MovieIterator;


public class MovieIteratorTest {

	private static MovieIterator iter = null; 
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		iter = new MovieIterator(new File("src/test/resources/xml/de"), "de");
	}

	@Test
	public void testNext() {
		assertTrue(iter.hasNext());
		Movie m = iter.next();
		assertEquals("3476445", m.getId());
	}

}
