public class User3 {
    public static void main(String[] args) {
        ChatClientRevecive r = new ChatClientRevecive(7777);
        ChatClientSend s = new ChatClientSend(7777, "3");
        new Thread(r).start();
        new Thread(s).start();
    }


}
