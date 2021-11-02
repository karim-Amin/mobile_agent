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
            //1.create socket and connect to the sensor server
            //with IP:127.0.0.1(localhost)
            //and with portnumber: 3000
            Socket s_to_sensor_node = new Socket("127.0.0.1",3000);
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
                         dos.writeUTF("get location and destination");
                         dos.flush();
                     }
                     client_message = dis.readUTF();
                     if(client_message.equals("nedded location and destination")){
                         /*after getting the location form the computer
                           central node has to connect to the sensor server to get the 
                           needed readings from the sensors*/
                          /*Open I/O stream connection to central server*/
                            DataInputStream dis_for_sensor_server = new DataInputStream(s_to_sensor_node.getInputStream());
                            DataOutputStream dos_for_sensor_server = new DataOutputStream(s_to_sensor_node.getOutputStream());
                            String sensor_server_response;
                            /*perform some O/I operations between the central node and sensor node*/
                            while(true){
                                // send this message to server
                                dos_for_sensor_server.writeUTF("Hello Start Connection");
                                dos_for_sensor_server.flush();
                                //read the message sent by the server
                                sensor_server_response = dis_for_sensor_server.readUTF();
                                if("connected".equals(sensor_server_response)){
                                    // send this message to server
                                    dos_for_sensor_server.writeUTF("Get readings");
                                    dos_for_sensor_server.flush();
                                }
                                sensor_server_response = dis_for_sensor_server.readUTF();
                                if("needed data".equals(sensor_server_response)){
                                /*connect to sensor node and get the readings from it */
                                dos_for_sensor_server.writeUTF("end connection");
                                dos_for_sensor_server.flush();
                                }
                                sensor_server_response = dis_for_sensor_server.readUTF();
                                 if("ended bye".equals(sensor_server_response)){
                                  break;
                                }   
                            }
                            //5.close connection between the central node and the sensor node
                             dis_for_sensor_server.close();
                             dos_for_sensor_server.close();
                             s_to_sensor_node.close();
                            // send the recommended route
                            dos.writeUTF("send recommendation");
                            dos.flush();
                     }
                     client_message = dis.readUTF();
                     if(client_message.equals("end connection")){
                         // requst the data from computation node
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
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
