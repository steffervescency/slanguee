package cc.languee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBMappingAccess {

	private Connection connect;

	public DBMappingAccess() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connect = DriverManager.getConnection("jdbc:mysql://localhost/ibelieveispider?" + "user=root&password=tgekWo8G");

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

		// TODO implement;
		return null;
	}

	public int getLineNumberMapping(String originFileName, int originLineNumber, String originLang, String targetLang) {
		return 0;
	}
}
