package cc.languee.fileiteration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Parser {

	private Document xmlDocument = null;

	public Movie parse(String filepath, String language) throws ParserConfigurationException, SAXException, IOException {

		Movie movie = new Movie();
		Transcript transcript;
		transcript = new Transcript();
		transcript.setLanguage(language);

		File xmlFile = new File(filepath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		xmlDocument = dBuilder.parse(xmlFile);
		xmlDocument.getDocumentElement().normalize();

		NodeList sentenceNodes = xmlDocument.getElementsByTagName("s");

		int sentenceNodesLength = sentenceNodes.getLength();

		for (int sentenceIndex = 0; sentenceIndex < sentenceNodesLength; sentenceIndex++) {
			Node node = sentenceNodes.item(sentenceIndex);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Sentence sentence = new Sentence();

				Element sentenceElement = (Element) node;
				String id = sentenceElement.getAttribute("id");
				sentence.setId(id);

				NodeList childNodes = node.getChildNodes();
				for (int simNodeIndex = 0; simNodeIndex < childNodes.getLength(); simNodeIndex++) {
					Node simNode = childNodes.item(simNodeIndex);
					if (simNode.getNodeType() == Node.ELEMENT_NODE) {
						Element simElement = (Element) simNode;
						if(simElement.getNodeName().equals("w")){
							String word = simNode.getTextContent();
							sentence.addWord(word);
						}

					}
				}
				if(sentence.getWords().size()!=0){
					transcript.addSentence(sentence);
				}
			}
		}

		movie.addTranscript(transcript.getLanguage(), transcript);

		return movie;
	}

	public Movie next() {

		return null;
	}
}
