package message;

import java.util.Comparator;

public class Message {
    public static final int REQUEST = 0;
    public static final int PREPREPARE = 1;
    public static final int PREPARE = 2;
    public static final int COMMIT = 3;
    public static final int REPLY = 4;
    public static final int CHECKPOINT = 5;
    public static final int VIEWCHANGE = 6;
    public static final int NEWVIEW = 7;
    public static final int TIMEOUT = 8;
    public static final int CLITIMEOUT = 9;
    public static final int VOTEREQUEST = 10;
    public static final int VOTEPREPARE = 11;
    public static final int VOTEREPLY = 12;
    public static final int VOTERESULT = 13;

    public int type;
    public int sndId;
    public int rcvId;
    public long rcvtime;
    public long len;

    //按收到消息的时间排序
    public static Comparator<Message> cmp = new Comparator<Message>() {
        @Override
        public int compare(Message o1, Message o2) {
            return (int) (o1.rcvtime - o2.rcvtime);
        }
    };

    public Message(int sndId, int rcvId, long rcvtime) {
        this.sndId = sndId;
        this.rcvId = rcvId;
        this.rcvtime = rcvtime;
    }

    public Message copy(int rcvId, long rcvtime) {
        return new Message(sndId, rcvId, rcvtime);
    }

    public boolean equals(Object obj) {
        if(obj instanceof Message) {
            Message msg = (Message) obj;
            return (type == msg.type && sndId == msg.sndId && rcvId == rcvId && rcvtime == rcvtime);
        }
        return super.equals(obj);
    }

    public int hashCode() {
        String str = "" + type + sndId + rcvId + rcvtime;
        return str.hashCode();
    }

    public String toString() {
        String[] typeName = {"Request","PrePrepare","Prepare","Commit","Reply"
                ,"CheckPoint","ViewChange","NewView","TimeOut","CliTimeOut"};
        return "消息类型:"+typeName[type]+";发送者id:"
                +sndId+";接收者id:"+rcvId+";消息接收时间戳:"+rcvtime+";";
    }
}
