package com.example.springsample.login.domain.repository.jdbc;

import org.springframework.jdbc.core.RowCallbackHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowCallbackHandler implements RowCallbackHandler {
    @Override
    public void processRow(ResultSet resultSet) throws SQLException {
        Path file = Paths.get("sample.csv");
        try (FileWriter fw = new FileWriter(file.toAbsolutePath().toFile());
             BufferedWriter bw = new BufferedWriter(fw)) {
            do {
                StringBuilder line = new StringBuilder();
                line.append(resultSet.getString("user_id") + ",");
                line.append(resultSet.getString("password") + ",");
                line.append(resultSet.getString("user_name") + ",");
                line.append(resultSet.getDate("birthday") + ",");
                line.append(resultSet.getInt("age") + ",");
                line.append(resultSet.getBoolean("marriage") + ",");
                line.append(resultSet.getString("role"));

                bw.write(line.toString());
                bw.newLine();
            } while (resultSet.next());

            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }
}
