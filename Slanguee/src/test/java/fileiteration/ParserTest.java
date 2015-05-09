package fileiteration;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import cc.languee.fileiteration.Movie;
import cc.languee.fileiteration.Parser;
import cc.languee.fileiteration.Sentence;
import cc.languee.fileiteration.Transcript;


public class ParserTest {
	private static Parser p = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		p = new Parser();
	}

	@Test
	public void testParse() throws ParserConfigurationException, SAXException, IOException {
		String filepath = "src/test/resources/xml/de/0/158515/5177953_1of1.xml";
		Movie movie = p.parse(filepath, "de");

		Transcript transcript = movie.getTranscript("de");
		assertNotNull(transcript);
		List<Sentence> sentences = transcript.getSentences();
		
		Sentence sentence = sentences.get(0);
		assertEquals("2", sentence.getId());
		assertEquals("Geheimnisvolle Musik ", sentence.toString());
	}

}
