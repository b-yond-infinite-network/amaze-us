package main.java.sample.util;

import javax.naming.InitialContext;

public class Utility {

    private String dburl = "";
    private String dbname = "";
    private String dbuser = "";
    private String dbpassword = "";
    private String dbPort = "";


    public Utility() {

        {
            try {

                InitialContext initialContext = new javax.naming.InitialContext();

                dbpassword = (String) initialContext.lookup("java:comp/env/java.mysql.password");
                dbuser = (String) initialContext.lookup("java:comp/env/java.mysql.user");
                dbname = (String) initialContext.lookup("java:comp/env/java.mysql.name");
                dburl = (String) initialContext.lookup("java:comp/env/java.mysql.ip");
                dbPort = (String) initialContext.lookup("java:comp/env/java.mysql.port");

            }
            catch (Exception e) {

            }
        }

    }

    public String getDburl() {
        return dburl;
    }

    public String getDbname() {
        return dbname;
    }

    public String getDbuser() {
        return dbuser;
    }

    public String getDbpassword() {
        return dbpassword;
    }

    public String getDbPort() {
        return dbPort;
    }


}
