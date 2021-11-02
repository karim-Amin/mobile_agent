
package ds_mobileagent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
/**
 *
 * @author hp
 */
public class DS_MobileAgent_computer_node {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // computation node impelementation
        try{
           
            //1.create socket and connect to the server
            //with IP:127.0.0.1(localhost)
            //and with portnumber: 1234
            Socket s = new Socket("127.0.0.1", 1234);
            /*Open I/O stream connection to central server*/
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            String server_response;
            //3.perform IO with server 
            while(true){
                // send this message to server
                dos.writeUTF("Hello Start Connection");
                dos.flush();
                //read the message sent by the server
                server_response = dis.readUTF();
                if("connected".equals(server_response)){
                // send this message to server
                dos.writeUTF("get recommendation");
                dos.flush();
                }
                server_response = dis.readUTF();
                if("get location and destination".equals(server_response)){
                 /*connect to sensor node and get the readings from it */
                dos.writeUTF("nedded location and destination");
                dos.flush();
                }
                server_response = dis.readUTF();
                if("send recommendation".equals(server_response)){
                 // perfore some calculation to know the best route
                dos.writeUTF("end connection");
                dos.flush();
                }
                server_response = dis.readUTF();
                if("ended bye".equals(server_response)){
                    break;
                }
            }
            //5.close connection
                dis.close();
                dos.close();
                s.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
