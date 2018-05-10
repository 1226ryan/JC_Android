package com.example.administrator.test_kotlin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**             DAO(Data Access Object)
 * Database 의 data 에 접근을 위한 객체
 * Database 에 접근을 하기 위한 로직과 비지니스 로직을 분리하기 위해서 사용
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

/**
 * 웹서버는 DB와 연결하기 위해서 매번 커넥션 객체를 생성하는데, 이것을 해결하기 위해 나온것이 컨넥션 풀입니다.
 * ConnectionPool 이란 connection 객체를 미리 만들어 놓고 그것을 가져다 쓰는것입니다. 또 다쓰고 난 후에는 반환해 놓는 것.
 * 하지만 유저 한명이 접속해서 한번에 하나의 커넥션만 일으키지 않고 게시판 하나만 봐도 목록볼때 한번, 글읽을때마다 한번, 글쓸때 한번 등등… 엄청나게 많은 커넥션이 일어나기에,
 * 커넥션풀은 커넥션을 또 만드는 오버헤드를 효율적으로 하기 위해 DB에 접속하는 객체를 전용으로 하나만 만들고, 모든 페이지에서 그 객체를 호출해다 사용한다면?
 * 이렇게 커넥션을 하나만 가져오고 그 커넥션을 가져온 객체가 모든 DB와의 연결을 하는것이 바로 DAO 객체입니다
 *
 * DAO(Data Access Object)는 DB를 사용해 데이터를 조화하거나 조작하는 기능을 전담하도록 만든 오브젝트를 말한다.
 *
 * DB에 대한 접근을 DAO가 담당하도록 하여 데이터베이스 엑세스를 DAO에서만 하게 되면
 * 다수의 원격호출을 통한 오버헤드를 VO나 DTO를 통해 줄일 수 있고 다수의 DB 호출문제를 해결할 수 있습니다.
 * 또한 단순히 읽기만 하는 연산이므로 트랜잭션 간의 오버헤드를 감소할 수 있습니다.
 *
 * 참고 : https://jungwoon.github.io/common%20sense/2017/11/16/DAO-VO-DTO/
 */