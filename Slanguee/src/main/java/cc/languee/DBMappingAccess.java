package cc.languee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBMappingAccess {

	private Connection connect;

	public DBMappingAccess(String sqlUser, String sqlPassword) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connect = DriverManager.getConnection("jdbc:mysql://localhost/ibelieveispider?" + "user="+sqlUser+"&password="+sqlPassword);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String getMappedFilename(String fromFileName) {
		// try {
		// java.sql.PreparedStatement preparedStatement = this.connect
		// .prepareStatement("select file_id2 from file_map where file_id1=?");
		// preparedStatement.setString(1, "de");
		// preparedStatement.setString(2, "German");
		// resultSet = preparedStatement.executeQuery();

		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return null;
	}

	public String getFileNameMapping(String originFilename, String originLang, String targetLang) {
		String result = "";
		// TODO use 'file' table to retrieve internal id of originFilename wrt originLang
		// then use 'file_map' table to retrieve id of matching file wrt targetLang
		// do this for all file pairs until file with targetLang has been found.
		try {
			
			Statement stmt = null;
			ResultSet rs;
			
			stmt = connect.createStatement();
			String query = "SELECT * FROM file WHERE path='"+originFilename+"'";
			rs = stmt.executeQuery(query);
			String originId = "";
			while (rs.next()) {
				String entryLang = rs.getString(2);
				if (entryLang.equals(originLang)) {
					originId = rs.getString(1);
					break;
				}
            }
            rs.close();

            stmt = connect.createStatement();
			query = "SELECT * FROM file_map WHERE file_id_1='"+originId+"'";
			rs = stmt.executeQuery(query);
			
			rs.next();
			String targetId = rs.getString(3);
            rs.close();

            stmt = connect.createStatement();
			query = "SELECT * FROM file WHERE id='"+targetId+"'";
			rs = stmt.executeQuery(query);
			
			rs.next();
			result = rs.getString(3);
			rs.close();
		
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result; //"5156735_1of1.xml";
	}

	public ArrayList<Integer> getLineNumberMapping(String originFileName, int originLineNumber, String originLang, String targetLang) {
		ArrayList<Integer> lines = new ArrayList<Integer>();
		lines.add(704);
		lines.add(705);
		return lines;
	}
}
