package restaurant.study.com.chatting_server_client;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

public class Server {
    ServerSocket ss;
    final static ArrayList<String> arrRoom = new ArrayList<String>();

    Vector<Guest> v_w_m = new Vector<Guest>();		// vector_wait_member : 전체 입장한 유저들의 벡터 값
    Vector<Guest> v_m_t = new Vector<Guest>();  	// vector_member_total : 전체 유저의 수
    Vector<Guest> v_r_m_t = new Vector<Guest>();	// vector_room_member_total : 방 사용자의 명단
    Vector<Guest> v_e_r_m;							// vector_enter_room_member : 방에 입장한 유저들의 벡터 값
    Vector<String> v_e_r_m_n;						// vector_enter_room_member_name : 방에 입장해 말한 유저들의 이름에 대한 벡터 값

    @SuppressWarnings("rawtypes")
    HashMap<String, Vector> map_e_r_m = new HashMap<String, Vector>();		// roomtitle, v_e_r_m 를 담는 맵
    @SuppressWarnings("rawtypes")
    HashMap<String, Vector> map_e_r_m_n = new HashMap<String, Vector>();		// roomtitle, v_e_r_m_n 를 담는 맵
    HashMap<String, String> map_t_m_n = new HashMap<String, String>();		// cp, name 담는 맵
    HashMap<String, Integer> map_r_t_m_c = new HashMap<String, Integer>();		// roomtitle, room_count 담는 맵(room_total_member_count)

    public Server() {
        System.out.println("잠시만 기다려주세요..");
        try {
            ss = new ServerSocket(8888);
            while (true) {
                Socket s = ss.accept();
                Guest g = new Guest(s, this);
                System.out.println("\n -----시작----- \ng : "+g);
                addGuest(g);
                g.setDaemon(true);
                g.start();
            }
        } catch (Exception e) {

        }
    }

    public void addGuest(Guest g) {
        System.out.println("\n----------------- 대기실 사용자 추가(addGuest) -----------------");

        System.out.println("g : "+g);
        v_w_m.add(g);
        v_m_t.add(g);
    }

    public void removeGuest(Guest g) {
        System.out.println("\n----------------- 대기실 사용자 제거(removeGuest) -----------------");

        System.out.println("g : "+g);
        v_w_m.remove(g);
    }

    public void addRoom(String roomtitle) {
        System.out.println("\n----------------- 방 추가(addRoom) -----------------");
        System.out.println("roomtitle : "+roomtitle);

        // roomtitle 나누기
        StringTokenizer st = new StringTokenizer(roomtitle, ".");
        String[] arr_Msg = new String[st.countTokens()];
        System.out.println("arr_Msg.length : "+arr_Msg.length);
        map_r_t_m_c.put(roomtitle, arr_Msg.length);
        System.out.println("map_r_t_m_c.get(roomtitle) : "+map_r_t_m_c.get(roomtitle));

        arrRoom.add(roomtitle);

        v_e_r_m = new Vector<Guest>();
        map_e_r_m.put(roomtitle, v_e_r_m);

        v_e_r_m_n = new Vector<String>();
        map_e_r_m_n.put(roomtitle, v_e_r_m_n);
    }

    public void removeRoom(String roomtitle) {
        System.out.println("\n----------------- 방 제거(removeRoom) -----------------");

        map_e_r_m.remove(roomtitle);
    }

    @SuppressWarnings("unchecked")
    public void addRoomGuest(Guest g) {
        System.out.println("\n----------------- 방 사용자 추가(addRoomGuest) -----------------");

        v_e_r_m = map_e_r_m.get(g.roomtitle);
        v_e_r_m.add(g);
    }

    @SuppressWarnings("unchecked")
    public void removeRoomGuest(Guest g) {
        System.out.println("\n----------------- 방 사용자 제거(removeRoomGuest) -----------------");

        v_e_r_m = map_e_r_m.get(g.roomtitle);
        v_e_r_m.remove(g);
    }

    public void broadcast(String msg) {
        System.out.println("\n----------------- 대기실 모두에게(broadcast) -----------------");
        System.out.println("msg : "+msg);

        for (Guest g : v_w_m) {
            if( !msg.equals("with") ) {
                g.sendMsg(msg);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void broadcastRoom(String roomtitle, String msg) {
        System.out.println("\n----------------- 방 사용자 모두에게(broadcastRoom) -----------------");

        if (map_e_r_m.get(roomtitle) != null) {
            v_e_r_m = map_e_r_m.get(roomtitle);

            String enter_cp="";

            if (msg.startsWith("outroom]")) {
                // 채팅방 나가기 위한 소스
                System.out.println("msg : "+msg);
                if( msg.startsWith("outroom]") ) {
                    System.out.println("msg_2 : "+msg);

                    for (Guest g : v_m_t) {
                        for(int k=0; k<v_e_r_m.size(); k++) {
                            if( !g.equals(v_e_r_m.get(k)) ) {
                                g.sendMsg(msg);
                                System.out.println("g : "+g);
                            }
                        }
                    }
                }
            } else {
                for (Guest g : v_e_r_m) {
                    System.out.println("g : "+g);
                    map_t_m_n.put(g.id, g.name);
                    enter_cp += g.id+".";
                    g.sendMsg(msg);
                }
            }
            System.out.println("enter_cp : "+enter_cp);
            System.out.println("map_t_m_n.toString() : "+map_t_m_n.toString());
            System.out.println("\n-----------------------------------------------------------\n");

            // msg 나누기
            StringTokenizer st = new StringTokenizer(msg, "]");
            String[] arr_Msg = new String[st.countTokens()];
            for (int i = 0; st.hasMoreTokens(); i++) {
                arr_Msg[i] = st.nextToken();
                System.out.println("arr_Msg["+i+"] : "+arr_Msg[i]);
            }


            System.out.println("\n-----------------------------------------------------------\n");

            // if( rv개수 != roomtitle.split(.).size() ) -> list로 msg 담고, 그 개수 g.send로 보내기
            System.out.println("v_e_r_m.size() : "+v_e_r_m.size());
            System.out.println("map_r_t_m_c.get(roomtitle) : "+map_r_t_m_c.get(roomtitle));
            if( msg.startsWith("say]") ) {

                // 대화방의 총 사용자 수 구하기
                System.out.println("roomtitle : "+roomtitle);
                StringTokenizer stCP = new StringTokenizer(roomtitle, ".");
                String[] arr_r_m_t = new String[stCP.countTokens()];
                for (int i = 0; stCP.hasMoreTokens(); i++) {
                    arr_r_m_t[i] = stCP.nextToken();
                    System.out.println("arr_r_m_t["+i+"] : "+arr_r_m_t[i]);
                }

                // "대화방 총 사용자 수" 가 "v_e_r_m(방에 들어온 사용자 수)"보다 크면 msg를 저장.
                if(arr_r_m_t.length > v_e_r_m.size()) {
                    v_e_r_m_n = map_e_r_m_n.get(roomtitle);
                    System.out.println("v_e_r_m_n : "+v_e_r_m_n);
                    System.out.println("map_e_r_m_n.toString() : "+map_e_r_m_n.toString());

                    // 메세지의 개수를 넣기 위해 보낸 사람의 name을 대신 넣어줌
                    v_e_r_m_n.add(map_t_m_n.get(arr_Msg[1]));
                    System.out.println("v_e_r_m_n 2 : "+v_e_r_m_n);
                    System.out.println("map_e_r_m_n.toString() 2 : "+map_e_r_m_n.toString());
                } else if(arr_r_m_t.length == v_e_r_m.size()) {		// 같으면 초기화(모두 입장 시)
                    v_e_r_m_n = new Vector<String>();
                    map_e_r_m_n.replace(roomtitle, v_e_r_m_n);
                    System.out.println("map_e_r_m_n.toString() : "+map_e_r_m_n.toString());
                }
                System.out.println("map_e_r_m_n : "+map_e_r_m_n);
                System.out.println("map_e_r_m_n.get(roomtitle).size() : "+map_e_r_m_n.get(roomtitle).size());


                // 메시지 변수에 값 할당 및 메시지 변수 보내기
                // re_msg]보낸사람이름]내용]개수]방번호
                String re_msg="";
                if(map_e_r_m_n.get(roomtitle).size() >= 1) {
                    String message = arr_Msg[3];
                    if( message.equals("null") ) {
                        message = "사진";
                    }
                    re_msg = "re_msg]"+map_t_m_n.get(arr_Msg[1])+"]"+message+"]"+map_e_r_m_n.get(roomtitle).size()+"]"+roomtitle+"]"+ enter_cp;
                    System.out.println("re_msg : "+re_msg);
                    for (Guest g : v_m_t) {
                        System.out.println("v_m_t.size() : "+v_m_t.size());
                        System.out.println("v_e_r_m.size() : "+v_e_r_m.size());

                        for(int k=0; k<v_e_r_m.size(); k++) {
                            System.out.println("v_e_r_m.get("+k+") : "+v_e_r_m.get(k));
                            System.out.println("-----------------------------------------------------");
                            System.out.println("g.roomNumber : "+g.roomNumber);
                            re_msg += "]"+g.roomNumber;
                            System.out.println("re_msg 1 : "+re_msg);
                        }

                        for(int k=0; k<v_e_r_m.size(); k++) {
                            if( !g.equals(v_e_r_m.get(k)) ) {
                                g.sendMsg(re_msg);
                                System.out.println("g : "+g);
                            }
                        }
                    }
                } else {

                }

                System.out.println("v_e_r_m_n : "+v_e_r_m_n);
                System.out.println("re_msg 2 : "+re_msg);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void broadcastRoomList() {
        System.out.println("\n----------------- 방 목록(broadcastRoomList) -----------------");

        String roomlist = "roomlist]";
        Set<String> set = map_e_r_m.keySet();

        for (String roomtitle : set) {
            roomlist += roomtitle + "]";
            for (int i = 0; i < map_e_r_m.size(); i++) {
                v_e_r_m = map_e_r_m.get(roomtitle);
                roomlist += v_e_r_m.size() + "]";
            }
        }

        System.out.println("roomlist Last : "+roomlist);
    }

    public void broadcastGuestList() {
        System.out.println("\n----------------- 대기실 사용자 목록(broadcastGuestList) -----------------");

        String guestlist = "guestlist]";
        for (Guest g : v_w_m) {
            guestlist += g.id + "]";
        }
        System.out.println("guestlist 2 : "+guestlist);
        broadcast(guestlist);
    }

    @SuppressWarnings("unchecked")
    public void broadcastRoomGuestList(String roomtitle) {
        System.out.println("\n----------------- 방 사용자 목록 (broadcastRoomGuestList) -----------------");

        String roomguestlist = "roomguestlist]";
        if (map_e_r_m.get(roomtitle) != null) {
            v_e_r_m = map_e_r_m.get(roomtitle);
            if (v_e_r_m.size() > 0) {
                for (Guest g : v_e_r_m) {
                    roomguestlist += g.id + "]";
                }

                System.out.println("roomguestlist 2 : "+roomguestlist);
                broadcastRoom(roomtitle, roomguestlist);
            }
        }
    }

    @SuppressWarnings("unused")
    public static void main(String args[]) {
        Server server = new Server();
    }
}


