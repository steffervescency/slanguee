package cc.languee.fileiteration;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


public class MovieIterator implements Iterator<Movie> {
	private MovieTranscriptParser parser;
	private Iterator<File> dictIter;
	private String language;
	
	public MovieIterator(File directory, String language){
		parser = new MovieTranscriptParser();
		String[] extensions = new String[1];
		//extensions[0] = "xml.gz";
		extensions[0] = "xml";
		dictIter = FileUtils.iterateFiles(directory, extensions, true);
		this.language = language;
	}
	
	public boolean hasNext() {
		return dictIter.hasNext();
	}
	
	public Movie next() {
		File file = dictIter.next();
		System.out.println(file);
		Movie movie = null;
		try {
			movie = parser.parse(file, language);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movie;
	}
	
	public void remove() {
		/* do nothing */
	}
}
