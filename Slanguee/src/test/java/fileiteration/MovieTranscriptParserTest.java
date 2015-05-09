package fileiteration;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import cc.languee.fileiteration.Movie;
import cc.languee.fileiteration.MovieTranscriptParser;
import cc.languee.fileiteration.Sentence;
import cc.languee.fileiteration.Transcript;


public class MovieTranscriptParserTest {
	private static MovieTranscriptParser p = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		p = new MovieTranscriptParser();
	}

	@Test
	public void testParse() throws ParserConfigurationException, SAXException, IOException {
		File file = new File("src/test/resources/xml/de/0/158515/5177953_1of1.xml");
		Movie movie = p.parse(file, "de");

		Transcript transcript = movie.getTranscript("de");
		assertNotNull(transcript);
		List<Sentence> sentences = transcript.getSentences();
		
		Sentence sentence = sentences.get(0);
		assertEquals("2", sentence.getId());
		assertEquals("Geheimnisvolle Musik ", sentence.toString());
		sentence = transcript.getSentenceById("2");
		assertEquals("Geheimnisvolle Musik ", sentence.toString());
		sentence = transcript.getSentenceById("474"); // sentence divided by times
		assertEquals("Wenn da was raschelt , weiß ich , ob das nicht die Wildsau ist ? ", sentence.toString());
	}

}
