package com.example.administrator.test_kotlin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**             DAO(Data Access Object)
 * Database 의 data 에 접근을 위한 객체
 * Database 에 접근을 하기 위한 로지과 비지니스 로직을 분리하기 위해서 사용
 */
public class DAO_test {
    public void add(DTO_test dto) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "root");

        PreparedStatement preparedStatement = connection.prepareStatement("insert into users(id,name,password) value(?,?,?)");


        preparedStatement.setString(1, dto.getName());
        preparedStatement.setInt(2, dto.getValue());
        preparedStatement.setString(3, dto.getData());
        preparedStatement.executeUpdate();
        preparedStatement.close();

        connection.close();
    }
}
