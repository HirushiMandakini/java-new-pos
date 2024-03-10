package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

        private static DbConnection dbConnection;
        private Connection connection;
        public static String email;

        private DbConnection() throws SQLException {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/possystem",
                    "root",
                    "1234"
            );
        }
        public static DbConnection getInstance() throws SQLException {
            DbConnection Dbconnection;
            return (null == dbConnection) ? Dbconnection = new DbConnection() : dbConnection;
        }
        public Connection getConnection() {
            return connection;
        }
    }

