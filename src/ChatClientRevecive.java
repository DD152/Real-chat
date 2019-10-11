import com.google.gson.Gson;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ChatClientRevecive implements Runnable {
    Message me;
    private DatagramSocket socket;
    byte[] bytes = new byte[1024];
    DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
    private int localPORT;

    public ChatClientRevecive(int localPORT) {
        this.localPORT = localPORT;
    }


    //控制台输出
    public void pushMessage(Message message) {
        String isBroadcast="";
        if (message.isBroadcast()==false){
            isBroadcast="(私人消息)";
        }
        System.out.println(isBroadcast+message.getUserName()+":"+message.getText());
    }


    @Override
    public void run() {
        try {
            socket= new DatagramSocket(localPORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (true){

            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String datagram = new String(packet.getData(), 0, packet.getLength());

            //检测json最后一位
            int i;
            for(i=0;i<datagram.length();i++){
                char ch = datagram.charAt(i);
                if (ch=='}'){
                    break;
                }
            }
            datagram=new StringBuilder(datagram).substring(0,i+1);

            //反序列化
            Gson json = new Gson();
            me = json.fromJson(datagram, Message.class);
            pushMessage(me);
        }

    }
}
