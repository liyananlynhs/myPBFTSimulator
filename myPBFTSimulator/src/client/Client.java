package client;

import main.Simulator;
import message.Message;
import message.VoteReplyMsg;
import message.VoteRequestMsg;
import message.VoteResultMsg;
import message.RequestMsg;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Client {
    public int id;
    public int v;
    public  Map<Integer, Integer> voteTable; // 投票列表

    public Client(int id) {
        this.id = id;
        voteTable = new HashMap<>();
    }

    public void sendVoteRequest(long time) {
        Message voteRequestMsg = new VoteRequestMsg(id, 0, time, 1, time, id);
        //调用Simulator.sendMsg()发送投票请求消息
        Simulator.sendMsg(voteRequestMsg);
    }

    public void msgProcess(Message msg) {
        switch(msg.type) {
            case Message.VOTEREPLY:
                receiveVoteReply(msg);
                break;
            case Message.REPLY:
                receiveReply(msg);
                break;
            default:
                System.out.println("【Error】消息类型错误！");
        }
    }

    public void receiveVoteReply(Message msg) {
        VoteReplyMsg vrmsg = (VoteReplyMsg)msg;
        int voteTo = vrmsg.voteTo;
        // 修改投票表
        if(!voteTable.containsKey(voteTo)){
            voteTable.put(voteTo, 1);
        }
        else {
            int v = voteTable.get(voteTo)+1;
            voteTable.put(voteTo, v);
        }
        
        // 广播投票表
        Message voteResultMsg = new VoteResultMsg(id, id, vrmsg.rcvtime, vrmsg.vote, "VoteResult摘要", voteTable);
        //调用Simulator.sendToAllReplica广播这个投票结果
        Simulator.sendMsgToAllReplicas(voteResultMsg);
       // System.out.println(voteTable);
        //System.out.println("------投票结果排序---------");
        voteTable = voteTable.entrySet().stream()
              .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                      (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        System.out.println("------投票结果---------");
        System.out.println(voteTable);
        System.out.println("----------------------");
       
    }
    
    //发送Request消息
    public void sendRequest(int primaryIndex) {
        RequestMsg requestMsg = new RequestMsg(id, primaryIndex, 0);
        Simulator.sendMsg(requestMsg);
    }

    public void receiveReply(Message msg) {
        System.out.println("客户端:" + id + "收到Reply，来自:" + msg.sndId);
    }
}
