package message;

public class ReplyMsg extends Message{
    public ReplyMsg(int sendId, int rcvId, long rcvTime) {
        super(sendId, rcvId, rcvTime);
    }
    public boolean equals(Object obj) {
        if (obj instanceof ReplyMsg) {
            ReplyMsg msg = (ReplyMsg) obj;
            return (sndId == msg.sndId && rcvId == msg.rcvId && rcvtime == msg.rcvId);
        }
        return super.equals(obj);
    }

    public int hashCode() {
        String str = "" + sndId + rcvId + rcvtime;
        return str.hashCode();
    }

    public String toString() {
        return super.toString() + "发送者:"+sndId+";接收者:"+rcvId;
    }
}
