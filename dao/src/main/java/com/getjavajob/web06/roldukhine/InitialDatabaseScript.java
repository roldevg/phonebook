package com.getjavajob.web06.roldukhine;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitialDatabaseScript {

    public static final String CREATE_DATE_MODEL_SQL_FILE_NAME = "./create-data-model.sql";

    public static void executeScript() {

        String filepath = "";
        ClassLoader classLoader = InitialDatabaseScript.class.getClassLoader();
        URL resource = classLoader.getResource(CREATE_DATE_MODEL_SQL_FILE_NAME);
        if (resource != null && resource.getFile() != null) {
            filepath = resource.getFile();
        }

        String script = "";
        try {
            script = getInitScript(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(script);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String getInitScript(String filename) throws IOException {
        InputStream inputStream = new FileInputStream(filename);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (reader.ready()) {
                String line = reader.readLine();
                builder.append(line);
            }
        }
        return builder.toString();
    }
}
