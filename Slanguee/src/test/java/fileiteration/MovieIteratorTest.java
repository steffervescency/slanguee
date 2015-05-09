package fileiteration;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import cc.languee.fileiteration.Movie;
import cc.languee.fileiteration.MovieIterator;
import cc.languee.fileiteration.Transcript;


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
		assertEquals("4720898_1of1.xml.gz", m.getId());
		Transcript transcription = m.getTranscript("de");
		assertEquals("12 JAHRE SPÄTER ", transcription.getSentenceById("1").toString());
		
	}

}
