package ru.eltech.sapr.web;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionManager {
    public static DataSource createDataSource() throws IOException {
        try (InputStream input = ConnectionManager.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties dbProperties = new Properties();
            dbProperties.load(input);

            JdbcDataSource source = new JdbcDataSource();
            source.setURL(dbProperties.getProperty("db.url"));
            source.setUser(dbProperties.getProperty("db.user"));
            source.setPassword(dbProperties.getProperty("db.pass"));
            return source;
        }
    }
}
