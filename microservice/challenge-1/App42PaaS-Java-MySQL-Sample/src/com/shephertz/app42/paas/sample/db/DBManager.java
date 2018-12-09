package com.shephertz.app42.paas.sample.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.shephertz.app42.paas.sample.util.Util;

public class DBManager {

	private DriverManagerDataSource dataSource = null;
	private static final DBManager dsManager = new DBManager();
	static {
		try {
			createTable("create table user(name varchar(255), email varchar(255), description text)");
		} catch (Exception e) {
			// do nothing
			// table already created
			System.out.println("Table Already Created");	
		}
	}

	/*
	 * Initialize MySql
	 */
	public DBManager() {
		try {
			dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			String dbUrl = Util.getDBIp();
			String username = Util.getDBUser();
			String password = Util.getDBPassword();
			String dbName = Util.getDBName();
			int port = Util.getDBPort();
			System.out.println("DBURL: " + dbUrl + " UserName: " + username
					+ " Password: " + password + " Port: " + port + " DBName: "
					+ dbName);
			dataSource.setUrl("jdbc:mysql://" + dbUrl + ":" + port + "/"
					+ dbName + "?autoReconnect=true");
			dataSource.setUsername(username);
			dataSource.setPassword(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Get DBManager Instance
	 */
	public static DBManager getInstance() {
		return dsManager;
	}

	/*
	 * Get DriverManagerDatasource Instance
	 */
	public DriverManagerDataSource getDataSource() {
		return dataSource;
	}

	/**
	 * Select rows (This function fetches the rows from the table)
	 * 
	 * @param query
	 * @return ArrayList<Map<String, Object>>
	 * @throws SQLException
	 */
	public ArrayList<Map<String, Object>> select(String sqlQuery)
			throws SQLException {
		JdbcTemplate db = null;
		try {
			db = new JdbcTemplate(DBManager.getInstance().getDataSource());
		} catch (Exception e) {
			throw new SQLException(e.getStackTrace().toString());
		}

		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		try {
			List<Map<String, Object>> result = db.queryForList(sqlQuery);
			for (int i = 0; i < result.size(); i++) {
				Map<String, Object> rowMap = result.get(i);
				resultList.add(rowMap);
			}
		} catch (Exception e) {
			throw new SQLException("Error executing query: " + sqlQuery);
		}
		dataSource.getConnection().close();
		return resultList;

	}

	/**
	 * Insert (This function inserts data into the table)
	 * 
	 * @param query
	 * @throws SQLException
	 */
	public void insert(final String query) throws Exception{
		JdbcTemplate db = new JdbcTemplate(DBManager.getInstance()
				.getDataSource());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		db.update(new PreparedStatementCreator() {
			@Override
			public java.sql.PreparedStatement createPreparedStatement(
					java.sql.Connection connection) throws SQLException {
				PreparedStatement ps = (PreparedStatement) connection
						.prepareStatement(query, new String[] { "id" });
				// ps.setString(1, name);
				return ps;
			}
		}, keyHolder);
		dataSource.getConnection().close();
	}

	public static void createTable(String query) throws SQLException {
		JdbcTemplate db = new JdbcTemplate(DBManager.getInstance()
				.getDataSource());
		try {
			db.execute(query);
		} catch (Exception e) {
			throw new SQLException("Error while executing query: ' " + query
					+ " '");
		}
		
	}

}
