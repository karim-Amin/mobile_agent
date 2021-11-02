/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ds_mobileagent;

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
public class DS_MobileAgent_sensor_node {
    public static void main(String[] args) {
        // computation node impelementation
        try{
              //1.open server socket
            ServerSocket sv = new ServerSocket(3000);
            String client_message = null;
            while(true){
                //2.accept connection
                 Socket s = sv.accept();
                //3.create I/O streams
                 DataInputStream dis = new DataInputStream(s.getInputStream());
                 DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                 while(true){
                     client_message = dis.readUTF();
                     if(client_message.equals("Start Connection")){
                         // connection accepted by server
                         dos.writeUTF("connected");
                         dos.flush();
                     }
                     client_message = dis.readUTF();
                     if(client_message.equals("Get readings")){
                         // connection accepted by server
                         dos.writeUTF("nedded data");
                         dos.flush();
                     }
                     client_message = dis.readUTF();
                     if(client_message.equals("end connection")){
                         // connection accepted by server
                         dos.writeUTF("ended bye");
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
        }catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
}
}
