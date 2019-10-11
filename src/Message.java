public class Message {
    private int port;
    private String userName;
    private String text;
    private boolean isBroadcast;//是否广播
    private String object;//发送对象

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Message(int port, String userName, String text, boolean isBroadcast, String object) {
        this.port = port;
        this.userName = userName;
        this.text = text;
        this.isBroadcast = isBroadcast;
        this.object = object;
    }

    public boolean isBroadcast() {
        return isBroadcast;
    }

    public void setBroadcast(boolean broadcast) {
        isBroadcast = broadcast;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
