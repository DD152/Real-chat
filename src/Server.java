public class Server {
    public static void main(String[] args) {
        ChatServer r = new ChatServer();
        new Thread(r).start();
    }
}
