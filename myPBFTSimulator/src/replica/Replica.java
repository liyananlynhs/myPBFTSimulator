package replica;

import message.*;
import main.Simulator;


import java.util.*;

public class Replica {
    public String receiveTag = "Receive";
    public String sendTag = "Send";
    public int id;
    public int v;
    public int n;
    public int H;
    public Double[] crVotes = new Double[Simulator.RN];
    public Double[] crBeh = new Double[Simulator.RN];
    public Double[] crAcc = new Double[Simulator.RN];
    public Double[] crPro = new Double[Simulator.RN];
    int rcvCommitNum = 0;

    //消息缓存
    public Map<Integer, Set<Message>> msgCache;

    //投票列表
    public Map<Integer, Integer> voteTable;

    public Replica(int id) {
        this.id = id;
        msgCache = new HashMap<>();
        voteTable = new HashMap<>();
    }

    public void msgProcess(Message msg) {
        switch(msg.type) {
            case Message.VOTEREQUEST:
                receiveVoteRequest(msg);
                break;
            case Message.VOTEPREPARE:
                receiveVotePrepare(msg);
                break;
            case Message.VOTERESULT:
                receiveVoteResult(msg);
                break;
            case Message.REQUEST:
                receiveRequest(msg);
                break;
            case Message.PREPARE:
                receivePrepare(msg);
                break;
            case Message.COMMIT:
                receiveCommit(msg);
                break;
            default:
                System.out.println("消息类型错误!");
                return;
        }
    }

    public void receiveVoteRequest(Message msg) {
        if(msg == null)
            return;
        VoteRequestMsg voteRequestMsg = (VoteRequestMsg) msg;
        int c = voteRequestMsg.client;
        long t = voteRequestMsg.rcvtime;
        //新建VotePrepareMessage广播出去
        Message votePrepareMsg = new VotePrepareMsg(id, id, t, 1, "摘要", "投票请求消息", c);
        //调用Simulator.sendMsgToOthers把准备投票消息广播出去
        Simulator.sendMsgToOthers(votePrepareMsg);
        //第一个收到VoteRequest的副本节点也要投票
        Random r = new Random();
        int voteTo = r.nextInt(Simulator.RN);
        Message voteReplyMsg = new VoteReplyMsg(id, c, t, 1, "摘要", "投票响应消息", voteTo);
        //调用Simulator.sendMsg把投票结果返回给客户端
        Simulator.sendMsg(voteReplyMsg);

    }

    public void receiveVotePrepare(Message msg) {
        if(msg == null)
            return;
        VotePrepareMsg votePrepareMsg = (VotePrepareMsg) msg;
        int c = votePrepareMsg.client;
        long t = votePrepareMsg.rcvtime;
        Random r = new Random();
        int voteTo = r.nextInt(Simulator.RN);	//投票给谁
        Message voteReplyMsg = new VoteReplyMsg(id, c, t, 1, "摘要", "投票响应消息", voteTo);
        //调用Simulator.sendMsg把投票结果返回给客户端
        Simulator.sendMsg(voteReplyMsg);
    }

    public void receiveVoteResult(Message msg) {
        if(msg == null)
            return;
        VoteResultMsg voteResultMsg = (VoteResultMsg) msg;
        this.voteTable.putAll(voteResultMsg.voteTable);
        // 更新本节点的投票信任度列表
        for(Map.Entry entry : voteTable.entrySet()) {
            int k = (Integer)entry.getKey();
            int v = (Integer)entry.getValue();
            crVotes[k] = v / (double)Simulator.RN;
        }
    }

    public void receiveRequest(Message msg) {
        if(msg == null)
            return;
        RequestMsg requestMsg = (RequestMsg) msg;
        PrepareMsg prepareMsg = new PrepareMsg(id, id, 0);
        prepareMsg.c = requestMsg.c;
        Simulator.sendMsgToOthers(prepareMsg);
    }

    public void receivePrepare(Message msg) {
        if(msg == null)
            return;
        PrepareMsg prepareMsg = (PrepareMsg) msg;
        CommitMsg commitMsg = new CommitMsg(id, id, 0);
        commitMsg.c = prepareMsg.c;
        Simulator.sendMsgToOthers(commitMsg);
    }

    public void receiveCommit(Message msg) {
        if(msg == null)
            return;
        CommitMsg commitMsg = (CommitMsg) msg;
        rcvCommitNum++;
        if(rcvCommitNum >= (Simulator.RN + 1)/2) {
            ReplyMsg replyMsg = new ReplyMsg(id, commitMsg.c, 0);
            replyMsg.c = commitMsg.c;
            Simulator.sendMsg(replyMsg);
        }
    }
}
