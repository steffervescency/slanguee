package cc.languee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AlignmentParser {
	
	private String alignment_file;
	
	public AlignmentParser(String alignment_file) {
		this.alignment_file = alignment_file;
	}
	
	public class LineAlignment {
		int line_1;
		int line_2;
		
		public LineAlignment(int line_1, int line_2) {
			this.line_1 = line_1;
			this.line_2 = line_2;
		}
	}
	
	public class FileAlignment {
		
		ArrayList<LineAlignment> lines;
		String file_1;
		String file_2;
		
		public FileAlignment(String file_1, String file_2) {
			this.file_1 = file_1;
			this.file_2 = file_2;
			this.lines = new ArrayList<LineAlignment>();
		}
		
		public void addLine(LineAlignment line) {
			this.lines.add(line);
		}

	}
	
	public class SaxHandler extends DefaultHandler {
		
		public List<FileAlignment> fileAlignments;
		// the current file alignment
		private FileAlignment fileAlignment;
		
		public void startDocument() throws SAXException {
	        fileAlignments = new ArrayList<FileAlignment>();
	    }
		
		public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
			if(qName == "linkGrp") {
				String source_file = atts.getValue("fromDoc");
				String target_file = atts.getValue("toDoc");
				fileAlignment = new FileAlignment(source_file, target_file);
				fileAlignments.add(fileAlignment);
			} else if(qName == "link") {
				String xtargets = atts.getValue("xtargets");
				parseXTargets(xtargets, fileAlignment);
			}
		}
	}
	
	private void parseXTargets(String xtargetString, FileAlignment fileAlignment) {
		String[] xtargets = xtargetString.split(";");
		
		// ignore lines without a corresponding translation
		if(xtargets.length != 2 || xtargets[0].length() < 1 || xtargets[1].length() < 1) {
			return;
		}
		
		// Either the source has multiple lines
		if (xtargets[0].contains(" ")) {
			String[] source_lines = xtargets[0].split(" ");
			int line_2 = Integer.parseInt(xtargets[1]);
			for(int k = 0; k < source_lines.length; k++) {
				int line_1 = Integer.parseInt(source_lines[k]);
				LineAlignment lineAlignment = new LineAlignment(line_1, line_2);
				fileAlignment.addLine(lineAlignment);
			}
		// Or the target has multiple lines
		} else if (xtargets[1].contains(" ")) {
			String[] target_lines = xtargets[1].split(" ");
			int line_1 = Integer.parseInt(xtargets[0]);
			for(int k = 0; k < target_lines.length; k++) {
				int line_2 = Integer.parseInt(target_lines[k]);
				LineAlignment lineAlignment = new LineAlignment(line_1, line_2);
				fileAlignment.addLine(lineAlignment);
			}
		// Or neither
		} else {
			int line_1 = Integer.parseInt(xtargets[0]);
			int line_2 = Integer.parseInt(xtargets[1]);
			LineAlignment lineAlignment = new LineAlignment(line_1, line_2);
			fileAlignment.addLine(lineAlignment);
		}	
	}
	
	public List<FileAlignment> parseAlignmentFile() throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser parser = parserFactory.newSAXParser();
		SaxHandler handler = new SaxHandler();
		parser.parse(this.alignment_file, handler);
		
		return handler.fileAlignments;
	}
}