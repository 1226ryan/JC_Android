package restaurant.study.com.chatting_server_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.text.SimpleDateFormat;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Locale;

public class ClientManagerThread extends Thread{

    private Socket m_socket;
    private String login_ID, other_ID;
    private String sconnectID, sconnectCP, simgpath, selectDATE, selectCONT, selectCount, schatroom, select_chatroom=null;
    String myDB = "db";

    @Override
    public void run() {
        super.run();
// db create
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("jdbc 드라이버 로딩 성공");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            String url = "jdbc:mysql://115.71.238.109:3306/db?useUnicode=true& useUnicode=true&characterEncoding=euc_kr";
            Connection con = DriverManager.getConnection(url, "root", "cnwlcjf1");

            String select_url = "jdbc:mysql://115.71.238.109:3306/db";
            Connection select_con = DriverManager.getConnection(select_url, "root", "cnwlcjf1");

            System.out.println("mysql 접속 성공");
            Statement stmt = con.createStatement();
            Statement select_stmt = select_con.createStatement();

// Chatting room & Chatting cont

            BufferedReader tmpbuffer = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));

            String text;

            while(true) {
                text = tmpbuffer.readLine();

                if(text == null) {
                    System.out.println(login_ID + "이(가) 나갔습니다.");
                    //for(int i = 0; i < ChatServer.m_OutputList.size(); ++i) {
                    //ChatServer.m_OutputList.get(i).println(login_ID + "이(가) 나갔습니다.");
                    //ChatServer.m_OutputList.get(i).flush();
                    //}
                    break;
                }
// connect하기
                String[] csplit = text.split("`");
                if(csplit.length == 2) {
                    if(sconnectID != csplit[0]) {
                        sconnectID  = csplit[0];
                        System.out.println(sconnectID+"-접속");
                        System.out.println("-----------------------------------------------");
                        continue;
                    }
                }

// 아이디 나누기
                String[] split = text.split("`");
                if(split.length >= 3) {
                    if(login_ID != split[0]) {
                        login_ID = split[0];
                        other_ID = split[1];
                        simgpath = split[2];
                        schatroom = login_ID+"."+other_ID;
// chattingroom db 입력
                        String select_sql = "SELECT chat_room FROM chattingroom where chat_room='"+schatroom+"'";
                        ResultSet select_rs = select_stmt.executeQuery(select_sql);
                        while(select_rs.next()) {
                            select_chatroom = select_rs.getString(1);
                        }
                        System.out.println(schatroom+"방에 "+login_ID+"님이 입장하였습니다.");
                        System.out.println("-----------------------------------------------");
                        if(select_chatroom == null) {
                            String room_sql = "INSERT INTO chattingroom(chat_room, member_id, last_send_date, last_send_cont, other_img_path) VALUES ("+"'"+schatroom+"',"+"'"+login_ID+"',"+"'"+null+"',"+"'"+null+"',"+"'"+simgpath+"')";
                            int room_cnt=stmt.executeUpdate(room_sql);
                            System.out.println(room_cnt>0 ? login_ID+"님과 "+other_ID+"님의 방이 개설되었습니다.":"등록 실패");
                            System.out.println("-----------------------------------------------");
                        }

                        select_rs.close();
                        select_stmt.close();
                        select_con.close();
                        continue;
                    }
                }
//방 나누기

// 시간출력
                long time = System.currentTimeMillis();
                Date date = new Date(time);

                SimpleDateFormat formatter_one = new SimpleDateFormat ( "EEE, dd MMM yyyy hh:mm:ss",Locale.ENGLISH );
                SimpleDateFormat formatter_two = new SimpleDateFormat ( "yyyy년 MM월 dd일 E요일" );
                ParsePosition pos = new ParsePosition ( 0 );
                Date frmTime = formatter_one.parse ( formatter_one.format(date), pos );
                String strD = formatter_two.format ( frmTime );

                SimpleDateFormat time1 = new SimpleDateFormat("a hh:mm");
                String strT = time1.format(date);
                String str = strD+"."+strT;
                System.out.println("str : "+str);

// 내용 출력
                for(int i = 0; i < ChatServer.m_OutputList.size(); ++i) {
                    ChatServer.m_OutputList.get(i).println(text);
                    ChatServer.m_OutputList.get(i).flush();
                }
                System.out.println(login_ID+" : "+text+"("+str+")");

// chattingcont db에 등록
                String sql = "INSERT INTO chattingcont(chat_room, member_id, chat_date, chat_cont) VALUES ("+"'"+schatroom+"',"+"'"+login_ID+"',"+"'"+str+"',"+"'"+text+"')";
                int cnt=stmt.executeUpdate(sql);
                System.out.println(cnt>0?"내용 등록 성공":"등록 실패");

                String update_sql = "UPDATE chattingroom SET last_send_date='"+str+"', last_send_cont='"+text+"' WHERE chat_room='"+login_ID+"."+other_ID+"'";
                int update_cnt = stmt.executeUpdate(update_sql);
                String update_sql_2 = "UPDATE chattingroom SET last_send_date='"+str+"', last_send_cont='"+text+"' WHERE chat_room='"+other_ID+"."+login_ID+"'";
                int update_cnt_2 = stmt.executeUpdate(update_sql_2);

                System.out.println(update_cnt>0?"수정 성공":"수정 실패-글 번호가 없어요");

                //stmt.close();
                //con.close();


            }

            ChatServer.m_OutputList.remove(new PrintWriter(m_socket.getOutputStream()));
            m_socket.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch(Exception ex) {
            ex.printStackTrace();
            System.out.println("here5");
        }
    }

    public void setSocket(Socket _socket) {
        m_socket = _socket;
    }
}
