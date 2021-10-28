/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ds_mobileagent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author hp
 */
public class DS_mobileAgent_central_node {
    public static void main(String[] args)
    {
        try {
            //1.open server socket
            ServerSocket sv = new ServerSocket(1234);
            String client_message = null;
            while(true){
                //2.accept connection
                 Socket s = sv.accept();
                //3.create I/O streams
                 DataInputStream dis = new DataInputStream(s.getInputStream());
                 DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                 while(true){
                     client_message = dis.readUTF();
                     if(client_message.equals("Hello Start Connection")){
                         // connection accepted by server
                         dos.writeUTF("connected");
                         dos.flush();
                     }
                     client_message = dis.readUTF();
                     if(client_message.equals("get recommendation")){
                         // requst the data from computation node
                         dos.writeUTF("get nedded data");
                         dos.flush();
                     }
                     client_message = dis.readUTF();
                     if(client_message.equals("nedded data")){
                         // requst the data from computation node
                         dos.writeUTF("calculate recommended route");
                         dos.flush();
                     }
                     client_message = dis.readUTF();
                     if(client_message.equals("close connection")){
                         // requst the data from computation node
                         dos.writeUTF("closed");
                         dos.flush();
                         break;
                     }
                 }
                //5.close connection
                dis.close();
                dos.close();
                s.close();
            }
            //6.close server
            //sv.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
