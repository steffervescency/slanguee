/**
 * 
 */
package cc.languee;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;

/**
 * @author adam
 *
 */
public class SlangueePreferences {

	private static String configPath = "./conf/index.ini";
	public static String englishIndexPath;
	public static String germanIndexPath;
	
	static {
		try {
			Ini prefs = new Ini(new File(configPath));
			englishIndexPath = prefs.get("index").get("englishIndexPath");
			germanIndexPath = prefs.get("index").get("germanIndexPath");
		} catch (InvalidFileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
