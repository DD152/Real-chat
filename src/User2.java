public class User2 {
    public static void main(String[] args) {
        ChatClientRevecive r = new ChatClientRevecive(8888);
        ChatClientSend s = new ChatClientSend(8888, "2");
        new Thread(r).start();
        new Thread(s).start();
    }
}
