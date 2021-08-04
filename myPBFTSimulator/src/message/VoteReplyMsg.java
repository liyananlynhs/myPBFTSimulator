package message;

public class VoteReplyMsg extends Message{
    public int vote;
    public String digest;
    public String message;
    public int voteTo;//投票给谁

    public VoteReplyMsg(int sndId, int rcvId, long rcvtime, int vote, String digest, String message, int voteTo) {
        super(sndId, rcvId, rcvtime);
        this.type = VOTEREPLY;
        this.vote = vote;
        this.digest = digest;
        this.message = message;
        this.voteTo = voteTo;
    }

    @Override
    public Message copy(int rcvId, long rcvtime) {
        return new VoteReplyMsg(sndId, rcvId, rcvtime, vote, digest, message, voteTo);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VoteReplyMsg) {
            VoteReplyMsg ms = (VoteReplyMsg) obj;
            return (vote == ms.vote && digest.equals(ms.digest) && message.equals(ms.message));
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        String str = "" + vote + digest + message;
        return str.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + "投票给:"+ vote +";摘要:"+ digest + ";消息:" + message;
    }
}
