package br.com.gvt.integrator.utils;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




/**
 * A collection of JDBC static helper methods. Its part of Apache's {@code org.apache.commons.dbutils} class file.
 * 
 * @author José Júnior
 *         GVT - 2014.08
 * 
 */
public class DatabaseUtils {
	
	
	/**
	 * Close a <code>ResultSet</code>, avoid closing if null and hide any SQLExceptions that occur.
	 * 
	 * @param rs
	 *            ResultSet to close.
	 */
	public static void closeQuietly(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		}
		catch (SQLException e) {
			// Do nothing!
		}
	}
	
	
	
	
	/**
	 * Close a <code>Statement</code>, avoid closing if null and hide any SQLExceptions that occur.
	 * 
	 * @param stmt
	 *            Statement to close.
	 */
	public static void closeQuietly(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		}
		catch (SQLException e) {
			// Do nothing!
		}
	}
	
	
	
	
	/**
	 * Close a <code>Connection</code>, avoid closing if null and hide any SQLExceptions that occur.
	 * 
	 * @param conn
	 *            Connection to close.
	 */
	public static void closeQuietly(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		}
		catch (SQLException e) {
			// Do nothing!
		}
	}
	
	
	
	
	/**
	 * Close a <code>Connection</code>, <code>Statement</code> and <code>ResultSet</code>. Avoid closing if null and
	 * hide any SQLExceptions that occur.
	 * 
	 * @param conn
	 *            Connection to close.
	 * @param stmt
	 *            Statement to close.
	 * @param rs
	 *            ResultSet to close.
	 */
	public static void closeQuietly(Connection conn, Statement stmt, ResultSet rs) {
		
		try {
			// Result sets must be the first to be closed.
			closeQuietly(rs);
		}
		finally {
			try {
				closeQuietly(stmt);
			}
			finally {
				closeQuietly(conn);
			}
		}
		
	}
}
