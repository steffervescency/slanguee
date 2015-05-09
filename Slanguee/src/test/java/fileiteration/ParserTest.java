package fileiteration;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import cc.languee.fileiteration.Movie;
import cc.languee.fileiteration.Parser;


public class ParserTest {
	private static Parser p = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		p = new Parser();
	}

	@Test
	public void testParse() {
		String filepath = "";
		List<Movie> movies = p.parse(filepath);

		assertEquals(1, movies.size());
		Movie movie = movies.get(0);
		assertNotNull(movie.getTranscript("de"));
		assertNotNull(movie.getTranscript("en"));
	}

}
