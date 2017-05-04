package cn.blmdz.book.g8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * jdbc-mysql 工具类
 * @author yongzongyang
 * @date 2017年4月25日
 */
public class JdbcUtil {
	public static void connection(CallBackNoResult callBack) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test", "root", "root");
			stmt = conn.createStatement();
			callBack.execute(stmt, rs);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException");
		} catch (SQLException e) {
			System.out.println("SQLException");
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (rs != null) rs.close();
			} catch (SQLException e) {
				System.out.println("SQLException");
			}
		}
	}

	public static <T> T connection(CallBack<T> callBack) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test", "root", "root");
			stmt = conn.createStatement();
			return callBack.execute(stmt, rs);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException");
		} catch (SQLException e) {
			System.out.println("SQLException");
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (rs != null) rs.close();
			} catch (SQLException e) {
				System.out.println("SQLException");
			}
		}
		return null;
	}
	
	public interface CallBackNoResult {
		void execute(Statement stmt, ResultSet rs) throws SQLException;
	}
	public interface CallBack<T> {
		T execute(Statement stmt, ResultSet rs) throws SQLException;
	}
}
