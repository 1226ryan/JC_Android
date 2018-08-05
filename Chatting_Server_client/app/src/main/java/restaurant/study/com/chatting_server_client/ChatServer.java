package restaurant.study.com.chatting_server_client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    public static ArrayList<PrintWriter> m_OutputList;

    public static void main(String[] args) {
        m_OutputList = new ArrayList<PrintWriter>();
        ServerSocket s_socket;
        Socket c_socket;
        ClientManagerThread c_thread;
        PrintWriter out=null;

        try {
            System.out.println("Wait..");
            s_socket = new ServerSocket(8888);
            while(true) {
                c_socket = s_socket.accept();

                c_thread = new ClientManagerThread();
                c_thread.setSocket(c_socket);
                out = new PrintWriter(c_socket.getOutputStream(), true);
                m_OutputList.add(out);

                c_thread.start();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


