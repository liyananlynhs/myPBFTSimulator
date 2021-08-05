package message;

public class CommitMsg extends Message{
    public CommitMsg(int sendId, int rcvId, long rcvTime){
        super(sendId, rcvId, rcvTime);
    }
    public Message copy(int rcvId, long rcvtime) {
        return new CommitMsg(sndId, rcvId, rcvtime);
    }

    public boolean equals(Object obj) {
        if (obj instanceof CommitMsg) {
            CommitMsg msg = (CommitMsg) obj;
            return (sndId == msg.sndId && rcvId == msg.rcvId && rcvtime == msg.rcvtime);
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
