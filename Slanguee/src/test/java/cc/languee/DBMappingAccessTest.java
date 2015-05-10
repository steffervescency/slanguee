package cc.languee;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;


public class DBMappingAccessTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testGetFileNameMapping() {
		
		DBMappingAccess a = new DBMappingAccess("root", "");
		String originFilename = "5156735_1of1.xml";
		String targetFile = a.getFileNameMapping(originFilename, "en", "de");
		assertEquals("5156735_1of1_de.xml", targetFile);
	}

}
