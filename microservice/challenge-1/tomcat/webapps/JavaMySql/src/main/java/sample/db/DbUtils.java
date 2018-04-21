package main.java.sample.db;

import java.sql.*;
import java.util.*;

public class DbUtils {

    private String dbName;
    private String dbUrl;
    private String dbPort;
    private String dbUser;
    private String dbPassword;
    private String jdbcUrl;
    private Boolean initialzed = false;
    private Connection conn = null;
    private Statement stmt = null;


    public DbUtils(String url, String port, String name, String user, String password) {

        this.dbName = name;
        this.dbUrl = url;
        this.dbPort = port;
        this.dbUser = user;
        this.dbPassword = password;
        this.jdbcUrl = "jdbc:mysql://" + dbUrl + ":" + dbPort + "/" + dbName;

        initialize();
    }

    private void initialize() {
        try {
            // The following is needed for java web apps only.  Comment out the line below for command line apps
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();
            conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

            stmt = conn.createStatement();

            createUserTable();

            initialzed = true;
        }
        catch (Exception ex) {

        }
    }

    public void createUserTable() {

        try {

            stmt.execute("create table user(name varchar(255), email varchar(255), description varchar(255));");
        } catch (Exception e) {

        }
    }


    public void disconnect() {
        if (initialzed) {
            try {
                if (stmt != null)
                    stmt.close();

                if (conn != null)
                    conn.close();

            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void addUser (String user, String email, String description)
    {
        if (initialzed) {

            try {
                String insert = "INSERT INTO user(name,description,email) VALUES(?, ?, ?);";

                PreparedStatement ps = conn.prepareStatement(insert);
                ps.setString(1, user);
                ps.setString(2, description);
                ps.setString(3, email);
                ps.execute();
            }
            catch (Exception  ex)
            {

            }

        }
    }

    public List<HashMap<String,Object>> getUsers () {

        List<HashMap<String,Object>> data = new ArrayList<HashMap<String, Object>>();

        if (initialzed) {
            try {
                String queryData = "SELECT * FROM user";

                ResultSet rs = stmt.executeQuery(queryData);


                data = convertResultSetToList(rs);

            } catch (Exception ex) {

            }

        }
        return data;

    }


    public List<HashMap<String,Object>> convertResultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();

        while (rs.next()) {
            HashMap<String,Object> row = new HashMap<String, Object>(columns);
            for(int i=1; i<=columns; ++i) {
                row.put(md.getColumnName(i),rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }
}
