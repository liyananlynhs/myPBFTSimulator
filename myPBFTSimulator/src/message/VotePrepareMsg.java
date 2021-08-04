package message;

public class VotePrepareMsg extends Message{
    public int vote;//提案号
    public String digest;//消息摘要
    public String message;
    public int client; //客户端的index，这样才知道把VoteReply消息发送给谁

    public VotePrepareMsg(int sndId, int rcvId, long rcvtime, int vote, String digest, String message, int client) {
        super(sndId, rcvId, rcvtime);
        this.type = VOTEPREPARE;
        this.vote = vote;
        this.digest = digest;
        this.message = message;
        this.client = client;
    }

    @Override
    public Message copy(int rcvId, long rcvtime) {
        return new VotePrepareMsg(sndId, rcvId, rcvtime, vote, digest, message, client);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof VotePrepareMsg) {
            VotePrepareMsg msg = (VotePrepareMsg) obj;
            return (vote == msg.vote && digest == msg.digest && message == msg.message && client == msg.client);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        String str = "" + vote + digest + message + client;
        return str.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + "投票给:" + vote + ";摘要:" + digest + ";消息:" + message + ";客户端:" + client;
    }
}
