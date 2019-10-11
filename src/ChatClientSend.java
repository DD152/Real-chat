import com.google.gson.Gson;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ChatClientSend implements Runnable{
    private String clientName;
    private DatagramSocket msg;
    private int PORT =10000;
    private int localPORT ;
    private InetAddress ip;

    public ChatClientSend(int localPORT,String clientName) {

        this.localPORT = localPORT;
        this.clientName = clientName;

        try {
            msg =new DatagramSocket();
            try {
                ip=InetAddress.getByName("127.0.0.1");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage() {

        String text=null;
        boolean isBroadcast=true;
        String object=null;

        Scanner scan = new Scanner(System.in);

        System.out.println("发送对象(all/指定对象)：");

        if (scan.hasNext()) {
            object = scan.nextLine();
            if (!object.equals("all")){
                isBroadcast =false;
            }
        }

        System.out.print("Text：");
        if (scan.hasNext()) {
            text=scan.nextLine();
        }

        //封装消息与序列化
        Gson gson = new Gson();
        String s = gson.toJson(new Message(localPORT,clientName, text, isBroadcast, object));

        byte[] bytes = s.getBytes();
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, ip, PORT);
        try {
            msg.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run() {
        //第一次连接服务器，向服务器发送一个无效数据，以便服务端存储映射
        Gson gson = new Gson();
        String s = gson.toJson(new Message(localPORT,clientName, "上线", true, null));
        byte[] bytes = s.getBytes();
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, ip, PORT);

        try {
            msg.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
        sendMessage();
        }
    }
}
