package main;

import client.Client;
import message.Message;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import replica.Replica;

public class Simulator {
    public static final int RN = 7;
    public static final int CN = 1;

    //消息优先队列（按消息接收时间排序）
    //LinkedList就是普通的队列，按照消息添加进来的顺序排列
    //PirorityQueue才能自定义排列方式
    public static Queue<Message> msgQue = new LinkedList<>();

    public static void main(String[] args) {
        Replica[] reps = new Replica[RN];
        Client[] clis = new Client[CN];
        for(int i = 0; i < RN; i++) {
            reps[i] = new Replica(i);//初始化
        }
        for(int i = 0; i < CN; i++) {
            clis[i] = new Client(i);
        }

        clis[0].sendVoteRequest(0);

        while(!msgQue.isEmpty()) {
            Message msg = msgQue.poll();
            switch (msg.type) {
                case Message.VOTEREPLY:		//发给客户端
                    clis[msg.rcvId].msgProcess(msg);
                    break;
                default:
                    reps[msg.rcvId].msgProcess(msg);
            }
        }
        System.out.println(clis[0].voteTable);
        //看一下投票信任度列表
        System.out.println("-------投票信任度列表-------");
        for(Double d : reps[0].crVotes)
            System.out.println(d);
    }

    public static void sendMsg(Message msg) {
        msgQue.add(msg);
    }

    public static void sendMsgToOthers(Message msg) { //发给除主节点之外的副本节点
        for(int i = 0; i < RN; i++){
            if(i != msg.sndId) {
                Message m = msg.copy(i, msg.rcvtime);
                sendMsg(m);
            }
        }
    }

    public static void sendMsgToAllReplicas(Message msg) { //广播给所有节点
        for(int i = 0; i < RN; i++) {
            Message m = msg.copy(i, msg.rcvtime);
            sendMsg(m);
        }
    }
}
