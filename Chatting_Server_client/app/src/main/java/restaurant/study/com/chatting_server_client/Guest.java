package restaurant.study.com.chatting_server_client;

import java.net.Socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class Guest extends Thread {
    Socket s;
    Server server;
    BufferedReader br;
    BufferedWriter bw;
    String id, roomtitle, say, roomNumber, sendImgpath, enter="", name;
    String re_roomtitle, imgpath, select_roomtitle;

    public Guest(Socket s, Server server) {
        this.s = s;
        this.server = server;

        try {
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String line = "";
        try {
            // jdbc 사용
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("jdbc 드라이버 로딩 성공");

            String insert_url = "jdbc:mysql://115.71.238.109:3306/db?useUnicode=true&useUnicode=true&characterEncoding=euc_kr";
            Connection insert_con = DriverManager.getConnection(insert_url, "root", "cnwlcjf1");

            String select_url = "jdbc:mysql://115.71.238.109:3306/db";
            Connection select_con = DriverManager.getConnection(select_url, "root", "cnwlcjf1");

            System.out.println("mysql 접속 성공");
            Statement insert_stmt = insert_con.createStatement();
            Statement select_stmt = select_con.createStatement();

            while ((line = br.readLine()) != null) {
                String[] arr = parseMsg(line.substring(line.indexOf("]") + 1));

                // 날짜, 시간 출력
                long time = System.currentTimeMillis();
                Date date = new Date(time);

                SimpleDateFormat formatter_one = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss", Locale.ENGLISH);
                SimpleDateFormat formatter_two = new SimpleDateFormat("yyyy년 MM월 dd일 E요일");

                ParsePosition pos = new ParsePosition(0);
                Date frmTime = formatter_one.parse(formatter_one.format(date), pos);
                String strD = formatter_two.format(frmTime);

                SimpleDateFormat time1 = new SimpleDateFormat("a hh:mm");
                String strT = time1.format(date);

                String str = strD + "." + strT;
                System.out.println("날짜.시간 str : " + str);

                if (line.startsWith("in]")) {

                    System.out.println("----------------대기실 입장----------------");

                    id = arr[0];
                    System.out.println("in] id(" + id + ") 대기실 입장");

                    server.broadcastGuestList();

                } else if (line.startsWith("out]")) {

                    System.out.println("----------------대기실 퇴장----------------");

                    id = arr[0];
                    System.out.println("out] id : " + id);

                    server.removeGuest(this);
                    terminate();

                } else if (line.startsWith("makeroom]")) {

                    System.out.println("----------------대화방 만들기----------------");
                    // arr[0] = 내 cp, arr[1] = 상대방 cp, arr[2] = 내 imgpath, arr[3] = 상대 imgpath, arr[4] = 방 번호

                    for (int a = 0; a < arr.length; a++) {
                        System.out.println("arr[" + a + "] : " + arr[a]);
                    }

                    roomtitle = arr[0] + "." + arr[1];
                    re_roomtitle = arr[1] + "." + arr[0];
                    id = arr[0] + "(king)";
                    imgpath = arr[2]+"%"+arr[3];

                    select_roomtitle = null;

                    // select db 해서 chat_room 찾기
                    if(arr[4].equals("null")) {
                        // friendListA(1:1) or carpoolA(1:1) or FriendInviteA(1:N) 에서 방 만들 때
                        String select_sql_chat_room = "SELECT chat_room FROM chattingroom where "
                                + "chat_room='"+roomtitle+"' OR chat_room='"+re_roomtitle+"'";
                        ResultSet select_rs_chat_room = select_stmt.executeQuery(select_sql_chat_room);
                        while (select_rs_chat_room.next()) {
                            select_roomtitle = select_rs_chat_room.getString(1);
                            System.out.println("arr[4] == null -> select_roomtitle : " + select_roomtitle);
                            roomtitle = select_roomtitle;
                        }
                    } else {
                        // ChattingRoomA에서 no값으로 select 하기.
                        String select_sql_no = "SELECT chat_room FROM chattingroom where no = '"+arr[4]+"'";
                        ResultSet select_rs_no = select_stmt.executeQuery(select_sql_no);
                        while (select_rs_no.next()) {
                            select_roomtitle = select_rs_no.getString(1);
                            System.out.println("arr[4] != null -> select_roomtitle : " + select_roomtitle);
                            roomtitle = select_roomtitle;
                        }
                    }

                    // id 에서 (king) 빼기 -> A.B 가 roomtitle 일때 -> A와 arr[0]이 같지 않을 때 id 가 arr[0]임.
                    StringTokenizer st = new StringTokenizer(roomtitle, ".");
                    String[] room = new String[st.countTokens()];
                    for (int k = 0; st.hasMoreTokens(); k++) {
                        room[k] = st.nextToken();
                        System.out.println("room["+k+"] : "+room[k]);
                    }
                    if ( !room[0].equals(arr[0]) ) {
                        System.out.println("id : "+id);
                        id = arr[0];
                        System.out.println("id = arr[0] : "+id);
                    }

                    System.out.println("방(" + roomtitle + ")을 " + id + "님이 만들었습니다.");
                    System.out.println("-----------------------------------------------");

                    // chattingroom db insert
                    if (select_roomtitle == null) {
                        String room_sql = "INSERT INTO chattingroom(chat_room, img_path) VALUES ("
                                + "'" + roomtitle + "'," + "'" + imgpath + "')";
                        int room_cnt = insert_stmt.executeUpdate(room_sql);
                        System.out.println(room_cnt>0?"첫번째 방("+roomtitle+")이 개설되었습니다.":"등록 실패");
                        System.out.println("-----------------------------------------------");

                        // cp별 이름 뽑아내서 입장 시에 ㅇㅇ님이 ㅇㅇ님과 ㅇㅇ님을 초대했습니다. 라는 메세지 띄우기
                        if( arr[1].length() > 15 ) {
                            @SuppressWarnings("unused")
                            String My_name="", name="";
                            String sql = "SELECT name, imgpath FROM member where cp='"+arr[0]+"'";
                            ResultSet sql_rs = select_stmt.executeQuery(sql);
                            while (sql_rs.next()) {
                                My_name = sql_rs.getString(1);
                                name = sql_rs.getString(1);
                                name += "님이 ";
                            }

                            StringTokenizer stz = new StringTokenizer(arr[1], ".");
                            String[] arrName = new String[stz.countTokens()];
                            for (int i = 0; stz.hasMoreTokens(); i++) {
                                arrName[i] = stz.nextToken();
                            }

                            for(int i=0; i<arrName.length-1; i++) {
                                String sql2 = "SELECT name FROM member where cp='"+arrName[i]+"'";
                                ResultSet sql_rs2 = select_stmt.executeQuery(sql2);
                                while (sql_rs2.next()) {
                                    name += sql_rs2.getString(1)+"님과 ";
                                }
                            }
                            String sql2 = "SELECT name FROM member where cp='"+arrName[arrName.length-1]+"'";
                            ResultSet sql_rs2 = select_stmt.executeQuery(sql2);
                            while (sql_rs2.next()) {
                                name += sql_rs2.getString(1)+"님을 초대했습니다.";
                                System.out.println("name : " + name);
                            }
                            enter = "enter]"+name+"]with";
                            System.out.println("enter : " + enter);


                            // 초대하겠습니다. 등록
                            String room_no="";
                            String select_sql = "SELECT no FROM chattingroom where chat_room= '"+roomtitle+"'";
                            ResultSet select_rs = select_stmt.executeQuery(select_sql);
                            while (select_rs.next()) {
                                room_no = select_rs.getString(1);
                                System.out.println("room_no : " + room_no);
                            }
                            String insert_sql = "INSERT INTO chattingcont(room_no, chat_room, member_id, chat_date, chat_cont) "
                                    + "VALUES ('"+room_no+"', '"+roomtitle+"', '"+arr[0]+"', '"+str+"', '"+name+"')";
                            int cnt = insert_stmt.executeUpdate(insert_sql);
                            System.out.println(cnt > 0 ? "내용 등록 성공" : "등록 실패");
                        }
                    }

                    int same_roomName=0;
                    System.out.println("arrRoom.size() : "+Server.arrRoom.size());
                    for(int i=0; i<Server.arrRoom.size(); i++) {
                        if(Server.arrRoom.get(i).equals(roomtitle)) {
                            same_roomName=1;
                        }
                    }
                    System.out.println("same_roomName : "+same_roomName);

                    // 맨 처음
                    if( same_roomName == 0 ) {
                        server.addRoom(roomtitle);
                        server.broadcastRoomList();
                    }

                } else if (line.startsWith("enterroom]")) {

                    System.out.println("----------------대화방 입장----------------");
                    // arr[0] : 나, arr[1] : 상대, arr[2] : 방번호

                    for (int a = 0; a < arr.length; a++) {
                        System.out.println("arr[" + a + "] : " + arr[a]);
                    }

                    roomtitle = arr[0] + "." + arr[1];
                    re_roomtitle = arr[1] + "." + arr[0];
                    id = arr[0];
                    roomNumber = arr[2];

                    // 대화방 찾기 - chattingroom db select
                    if(arr[2].equals("null")) {
                        // FriendListA, CarpoolA -> 내cp.상대cp 로 select 하기.
                        String select_sql = "SELECT chat_room FROM chattingroom where chat_room='" + roomtitle
                                + "' OR chat_room='" + re_roomtitle + "'";
                        ResultSet select_rs = select_stmt.executeQuery(select_sql);
                        while (select_rs.next()) {
                            select_roomtitle = select_rs.getString(1);
                            System.out.println("arr[2] = null -> select_roomtitle : " + select_roomtitle);
                            roomtitle = select_roomtitle;
                        }
                    } else {
                        // ChattingRoomA -> no값으로 select 하기.
                        String select_sql_no = "SELECT chat_room FROM chattingroom where no = '"+arr[2]+"'";
                        ResultSet select_rs_no = select_stmt.executeQuery(select_sql_no);
                        while (select_rs_no.next()) {
                            select_roomtitle = select_rs_no.getString(1);
                            System.out.println("arr[2] != null -> select_roomtitle : " + select_roomtitle);
                            roomtitle = select_roomtitle;
                        }
                    }

                    server.addRoomGuest(this);
                    server.removeGuest(this);
                    server.broadcastRoom(roomtitle, line);

                    if( !enter.equals("") && enter.startsWith("enter]") ) {
                        server.broadcastRoom(roomtitle, enter);
                        enter = "";
                    }

                } else if (line.startsWith("say]")) {

                    System.out.println("----------------대화방 대화----------------");

                    for(int a=0; a<arr.length; a++) {
                        System.out.println("arr["+a+"] : "+arr[a]);
                    }

                    roomtitle = arr[0] + "." + arr[1];
                    re_roomtitle = arr[1] + "." + arr[0];
                    id = arr[0];
                    say = arr[2];
                    System.out.println("say : "+say);
                    roomNumber = arr[3];
                    sendImgpath = arr[6];
                    name="";

                    String select_sql_name = "SELECT name FROM member where cp= '"+id+"'";
                    ResultSet select_rs_name = select_stmt.executeQuery(select_sql_name);
                    while (select_rs_name.next()) {
                        name = select_rs_name.getString(1);
                        System.out.println("name : " + name);
                    }

                    // chattingroom db select
                    if( arr[3].equals("null") ) {
                        String select_sql = "SELECT no, chat_room FROM chattingroom where chat_room="
                                + "'"+roomtitle+ "' OR chat_room='" + re_roomtitle + "'";
                        ResultSet select_rs = select_stmt.executeQuery(select_sql);
                        while (select_rs.next()) {
                            roomNumber = select_rs.getString(1);
                            select_roomtitle = select_rs.getString(2);
                            System.out.println("arr[3] == null select_roomtitle : " + select_roomtitle);
                            roomtitle = select_roomtitle;
                        }
                    } else {
                        String select_sql = "SELECT chat_room FROM chattingroom where no= '"+arr[3]+"'";
                        ResultSet select_rs = select_stmt.executeQuery(select_sql);
                        while (select_rs.next()) {
                            select_roomtitle = select_rs.getString(1);
                            System.out.println("arr[3] != null select_roomtitle : " + select_roomtitle);
                            roomtitle = select_roomtitle;
                        }
                    }

                    // chattingcont db insert
                    String sql = "";
                    if( arr[3].equals("null") ) {
                        sql = "INSERT INTO chattingcont(room_no, chat_room, member_id, chat_date, chat_cont, send_img) "
                                + "VALUES ('"+roomNumber+"', '"+roomtitle+"', '"+id+"', '"+str+"', '"+say+"', '"+sendImgpath+"')";
                    } else {
                        sql = "INSERT INTO chattingcont(room_no, chat_room, member_id, chat_date, chat_cont, send_img) "
                                + "VALUES ('"+arr[3]+"', '"+roomtitle+"', '"+id+"', '"+str+"', '"+say+"', '"+sendImgpath+"')";
                    }
                    int cnt = insert_stmt.executeUpdate(sql);
                    System.out.println(cnt > 0 ? "내용 등록 성공" : "등록 실패");

                    // chattingroom db update
                    String update_sql;
                    if( !say.equals("null") ) {
                        update_sql = "UPDATE chattingroom SET last_send_date='"+str+"', last_send_cont='"+say+"' WHERE chat_room='"+roomtitle+"'";
                    } else {
                        update_sql = "UPDATE chattingroom SET last_send_date='"+str+"', last_send_cont='"+sendImgpath+"' WHERE chat_room='"+roomtitle+"'";
                    }
                    int update_cnt = insert_stmt.executeUpdate(update_sql);

                    System.out.println(update_cnt > 0 ? "방 내용 수정 성공" : "수정 실패-글 번호가 없어요");

                    server.broadcastRoom(roomtitle, line);

                } else if (line.startsWith("exitroom]")) {

                    System.out.println("----------------대화방 퇴장----------------");

                    for (int a = 0; a < arr.length; a++) {
                        System.out.println("arr[" + a + "] : " + arr[a]);
                    }

                    roomtitle = arr[0] + "." + arr[1];
                    re_roomtitle = arr[1] + "." + arr[0];
                    id = arr[0];

                    // 대화방 찾기 - chattingroom db select
                    if(arr[2].equals("null")) {
                        String select_sql = "SELECT chat_room FROM chattingroom where " + "chat_room='" + roomtitle
                                + "' OR chat_room='" + re_roomtitle + "'";
                        ResultSet select_rs = select_stmt.executeQuery(select_sql);
                        while (select_rs.next()) {
                            select_roomtitle = select_rs.getString(1);
                            System.out.println("select_roomtitle : " + select_roomtitle);
                            roomtitle = select_roomtitle;
                        }
                    } else {
                        String select_sql = "SELECT chat_room FROM chattingroom where no='" + arr[2] + "'";
                        ResultSet select_rs = select_stmt.executeQuery(select_sql);
                        while (select_rs.next()) {
                            select_roomtitle = select_rs.getString(1);
                            System.out.println("select_roomtitle : " + select_roomtitle);
                            roomtitle = select_roomtitle;
                        }
                    }

                    server.addGuest(this);
                    server.removeRoomGuest(this);
                    server.broadcastRoomList();
                    server.broadcastGuestList();

                } else if (line.startsWith("outroom]")) {

                    System.out.println("----------------대화방 나가기----------------");

                    for (int a = 0; a < arr.length; a++) {
                        System.out.println("arr[" + a + "] : " + arr[a]);
                    }

                    roomtitle = arr[0] + "." + arr[1];
                    id = arr[0];
                    roomNumber = arr[2];


                    // cp별 이름 뽑아내서 "ㅇㅇ님이 님이 나갔습니다.\n채팅방으로 초대하기" 라는 메시지 띄우기
                    String name = "";
                    String sql = "SELECT name FROM member where cp='" + arr[0] + "'";
                    ResultSet sql_rs = select_stmt.executeQuery(sql);
                    while (sql_rs.next()) {
                        name = sql_rs.getString(1);
                        name += "님이 나갔습니다.";
                    }
                    System.out.println("name : "+name);

                    String out_room = "outroom]" + name + "]with";
                    System.out.println("out_room : " + out_room);

                    // 초대하겠습니다. 등록
                    String room_no = "";
                    String select_sql = "SELECT no, chat_room FROM chattingroom where chat_room= '" + roomtitle + "'";
                    ResultSet select_rs = select_stmt.executeQuery(select_sql);
                    while (select_rs.next()) {
                        room_no = select_rs.getString(1);
                        System.out.println("room_no : " + room_no);
                    }
                    if( roomNumber.equals("") || roomNumber == null || roomNumber.equals("null") ) {
                        roomNumber = room_no;
                        System.out.println("roomNumber : " + roomNumber);
                    }
                    System.out.println("roomNumber_2 : " + roomNumber);

                    String insert_sql = "INSERT INTO chattingcont(room_no, chat_room, member_id, chat_date, chat_cont) "
                            + "VALUES ('"+roomNumber+"', '"+roomtitle+"', '"+arr[0]+"', '"+str+"', '"+name+"')";
                    int cnt = insert_stmt.executeUpdate(insert_sql);
                    System.out.println(cnt > 0 ? "내용 등록 성공" : "등록 실패");

                    server.broadcastRoom(roomtitle, out_room);
                    server.addGuest(this);
                    server.removeRoomGuest(this);

                } else if (line.startsWith("removeroom]")) {

                    System.out.println("----------------대화방 제거----------------");

                    server.broadcastRoomList();
                    server.broadcastGuestList();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        System.out.println("---------------- 메시지 보내기 ----------------");
        try {
            System.out.println("1. sendMsg msg : " + msg);

            if (msg.startsWith("say]")) {
                System.out.println("2. say] : " + msg);
                bw.write(msg + "\n");
                bw.flush();
            } else if (msg.startsWith("enter]")) {
                System.out.println("2. enter] : " + msg);
                bw.write(msg + "\n");
                bw.flush();
            } else if (msg.startsWith("outroom]")) {
                System.out.println("2. outroom] : " + msg);
                bw.write(msg + "\n");
                bw.flush();
            } else if (msg.startsWith("re_msg]")) {
                System.out.println("2. re_msg] : " + msg);
                bw.write(msg + "\n");
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("3. sendMsg msg : " + msg);
    }

    public String[] parseMsg(String msg) {
        System.out.println("---------------- 메시지 파싱 ----------------");

        StringTokenizer st = new StringTokenizer(msg, "]");
        String[] arr = new String[st.countTokens()];
        for (int i = 0; st.hasMoreTokens(); i++) {
            arr[i] = st.nextToken();
        }
        return arr;
    }

    public void terminate() {
        System.out.println("---------------- 자원 정리 ----------------");

        try {
            br.close();
            bw.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

