package cc.languee.fileiteration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


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
		Movie movie = null;
		File file = dictIter.next();
		System.out.println(file);
		
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			//in = decompress(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			movie = parser.parse(file.getName(), in, language);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movie;
	}
	
	private InputStream decompress(File file) throws FileNotFoundException, IOException {
		return new GZIPInputStream(new FileInputStream(file));
	}

	public void remove() {
		/* do nothing */
	}
}
