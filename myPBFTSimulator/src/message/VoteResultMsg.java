package message;

import java.util.Map;

public class VoteResultMsg extends Message{
    public int vote;
    public String digest;
    public Map<Integer, Integer> voteTable;

    public VoteResultMsg(int sndId, int rcvId, long rcvtime, int vote, String digest, Map<Integer, Integer> voteTable) {
        super(sndId, rcvId, rcvtime);
        this.type = VOTERESULT;
        this.vote = vote;
        this.digest = digest;
        this.voteTable = voteTable;
    }

    @Override
    public Message copy(int rcvId, long rcvtime) {
        return new VoteResultMsg(sndId, rcvId, rcvtime, vote, digest, voteTable);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VoteResultMsg) {
            VoteResultMsg ms = (VoteResultMsg) obj;
            return (vote == ms.vote && digest.equals(ms.digest));
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        String str = "" + vote + digest + voteTable;
        return str.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + "提案号:"+ vote +";摘要:"+ digest;
    }
}
