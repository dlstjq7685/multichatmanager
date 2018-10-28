package Core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.ServerSocket;

import static Util.Printstr.print;
/*
    Lobby Group: group[0];
    Another Groups: cheat group
*/
public class Lobby implements Runnable {

    private ServerSocket servSock;
    private Group[] groups;
    private Thread[] gt;
    private final int port = 8000;

    {
        gt = new Thread[10];
        groups = new Group[10];

        for(int i = 0; i < 10; i ++){
            groups[i] = new Group(  "Group " + (i + 1));
            gt[i] = new Thread(groups[i]);
        }

        for(int i = 0; i < 10; i++)
            groups[i].setGroups(groups);

        try {
            servSock = new ServerSocket(port);
        } catch (IOException e) {
            print("Couldn't open socket port");
        }
    }

    @Override
    public void run() {

        print("Open port & Running server");
        Socket clientSock;
        try{
            for(int i = 0; i < 10; i++)
                gt[i].start();

            while (!Thread.interrupted()) {
                clientSock = servSock.accept();
                print("Came to : " + clientSock.getInetAddress().getHostAddress());

                if(Console.flag){
                    InputStream in = clientSock.getInputStream();
                    Clientreceiver rec = new Clientreceiver(in, groups,0);
                    groups[0].setMembers(clientSock);
                    Thread t = new Thread(rec);
                    t.start();
                }
                Thread.sleep(10);
            }
        }
        catch (InterruptedException e){ }
        catch (IOException e){ print("socket error"); }

        terminate();
    }

    private void terminate(){
        try {
            servSock.close();
        } catch (IOException e) {
        }

        for(int i = 0; i < 10; i++){
            gt[i].interrupt();
        }
        try {
            for(int i = 0; i < 10; i++)
                gt[i].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print("Lobby exit");
    }
}