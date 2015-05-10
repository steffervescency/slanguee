package cc.languee;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.sql.Statement;

public class AlignmentLoader {
	
	Connection conn;
	
	// add a file to the mysql database
	// TODO: should check if it's already there to avoid duplicates
	private int addFile(String file_name, String language) throws SQLException {
		String file_name_path[] = file_name.split("/");
		String file_identifier = file_name_path[file_name_path.length - 1].split("\\.")[0];
		String query = "INSERT INTO file(path, language_id) VALUES('" + file_identifier + "', '" + language + "');";
		return executeQueryWithLastId(query);
	}
	
	private int addFileMap(int file_1, int file_2) throws SQLException {
		String query = "INSERT INTO file_map(file_id_1, file_id_2) VALUES(" + file_1 + ", " + file_2 + ");";
		return executeQueryWithLastId(query);
	}
	
	private int executeQueryWithLastId(String query) throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(query);
		query = "SELECT LAST_INSERT_ID() AS last_id FROM file;";
		ResultSet rs = stmt.executeQuery(query);
		rs.next();
		String lastid = rs.getString("last_id");
		stmt.close();
		return Integer.parseInt(lastid);
	}
	
	private void addAlignment(int file_map_id, int line_1, int line_2) throws SQLException {
		Statement stmt = conn.createStatement();
		String query = "INSERT INTO alignment(file_map_id, line_1, line_2) VALUES(" + file_map_id +
				", " + line_1 + "," + line_2 + ")";
		stmt.executeUpdate(query);
		stmt.close();
	}
	
	
	
	public void loadAlignmentFileToDatabase(String alignment_file, String source_lang, String target_lang) throws ParserConfigurationException, SAXException, IOException, SQLException {
		try {
		    conn =
		       DriverManager.getConnection("jdbc:mysql://localhost/ibelieveispider?" +
		                                   "user=spider&password=itssausagetome");

		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return;
		}
		
		AlignmentParser parser = new AlignmentParser(alignment_file);
		
		
		List<AlignmentParser.FileAlignment> fileAlignments = parser.parseAlignmentFile();
		
		for(AlignmentParser.FileAlignment alignment : fileAlignments) {
			int file_1_id = addFile(alignment.file_1, source_lang);
			int file_2_id = addFile(alignment.file_2, target_lang);
			int file_map_id = addFileMap(file_1_id, file_2_id);
			
			for(AlignmentParser.LineAlignment line : alignment.lines) {
				addAlignment(file_map_id, line.line_1, line.line_2);
			}
		}
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, SQLException {
		AlignmentLoader loader = new AlignmentLoader();
		loader.loadAlignmentFileToDatabase("de-en.xml", "de", "en");
		System.out.println("finished");
	}
	
}
