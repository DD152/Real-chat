import com.google.gson.Gson;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;


public class ChatServer implements Runnable{
    private static final int port = 10000;
    private static DatagramSocket server = null;
    private static HashMap<Integer,String> Clients =new HashMap<Integer,String>();

    public ChatServer() {
        try {
            server =new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    //存储客户端映射，但是此处不够严谨
    public static void setID(Integer port,String name){
        if(Clients.get(port)==null) {
            Clients.put(port, name);
        }
    }

    public void run() {
        while(true){
            byte[] bytes = new byte[1024];
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
            DatagramPacket sendp = null;


            while(true){
                try {
                    server.receive(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String datagram = new String(packet.getData(), 0, packet.getLength());
                System.out.println(datagram);
                Gson json = new Gson();
                Message message = null;

                message = json.fromJson(datagram, Message.class);

                setID(message.getPort(),message.getUserName());

                //广播与非广播
                if (message.isBroadcast()){

                    for (Integer port: Clients.keySet()) {


                            try {
                                sendp =new DatagramPacket(packet.getData(), packet.getData().length, InetAddress.getByName("127.0.0.1"),port);
                                server.send(sendp);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                    }
                }
                else {
                    for (Integer port: Clients.keySet()) {
                        String name = Clients.get(port);
                        if (message.getObject().equals(name)){



                                try {
                                    sendp =new DatagramPacket(packet.getData(), packet.getData().length, InetAddress.getByName("127.0.0.1"),port);
                                    server.send(sendp);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                        }
                    }
                }

            }
        }

    }
}
