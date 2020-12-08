package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection connection = null;

    private static Properties loadProperties(){
        try(FileInputStream  fis = new FileInputStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            return properties;
        } catch(IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static Connection getConnection() {
        if(connection == null) {
            try {
                Properties properties = loadProperties();
                String dburl = properties.getProperty("dburl");
                connection = DriverManager.getConnection(dburl, properties);
            } catch(SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
    public static void closeStatement(Statement stm) {
        if(stm != null) {
            try {
                stm.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
