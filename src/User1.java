public class User1 {
    public static void main(String[] args) {

        ChatClientRevecive r = new ChatClientRevecive(9999);
        ChatClientSend s = new ChatClientSend(9999, "1");
        new Thread(r).start();
        new Thread(s).start();
    }
}
