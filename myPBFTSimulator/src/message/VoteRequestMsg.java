package message;

public class VoteRequestMsg extends Message{
    public int operation;
    public long timestamp;
    public int client;

    // <<REQUEST, operation, timestamp, client>, Qvc>
    public VoteRequestMsg(int sndId, int rcvId, long rcvtime, int operation, long timestamp, int client) {
        super(sndId, rcvId, rcvtime);
        this.type = VOTEREQUEST;
        this.operation = operation;
        this.timestamp = timestamp;
        this.client = client;
    }

    @Override
    public Message copy(int rcvId, long rcvtime) {
        return new VoteRequestMsg(sndId, rcvId, rcvtime, operation, timestamp, client);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof VoteRequestMsg) {
            VoteRequestMsg msg = (VoteRequestMsg) obj;
            return (operation == msg.operation && timestamp == msg.timestamp && client == msg.client);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        String str = "" + operation + timestamp + client;
        return str.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + "操作:" + operation + ";时间戳:" + timestamp + ";客户端:" + client;
    }
}
