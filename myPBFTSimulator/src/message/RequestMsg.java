package message;

public class RequestMsg extends Message{
    public int c; // 客户端id
    public RequestMsg(int sendId, int recId, long rcvTime){
        super(sendId, recId, rcvTime);
        this.c = sendId;
    }

    public boolean equals(Object obj) {
        if (obj instanceof RequestMsg) {
            RequestMsg msg = (RequestMsg) obj;
            return (msg.sndId == this.sndId && msg.rcvId == this.rcvId && msg.rcvtime == this.rcvtime);
        }
        return super.equals(obj);
    }

    public int hashCode() {
        String str = "" + c;
        return str.hashCode();
    }

    public String toString() {
        return super.toString() + "客户端编号:" + c;
    }
}
