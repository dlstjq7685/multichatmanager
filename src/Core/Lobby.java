package Core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.ServerSocket;

import static Util.Printstr.print;

public class Lobby implements Runnable {

    private ServerSocket servSock;
    private Group group1;
    private Thread gt;
    private final int port = 8000;

    {
        group1 = new Group("first");
        gt = new Thread(group1);
        try {
            servSock = new ServerSocket(port);
        } catch (IOException e) {
            print("Couldn't open socket port");
        }
    }

    @Override
    public void run() {

        print("Open port & Running server");
        Socket clntSock;
        try{
            gt.start();

            while (!Thread.interrupted()) {
                clntSock = servSock.accept();
                print("came to : " + clntSock.getInetAddress().getHostAddress());

                if(Console.flag){
                    InputStream in = clntSock.getInputStream();
                    Clientreceiver rec = new Clientreceiver(in, group1);
                    group1.setMembers(clntSock);
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

        gt.interrupt();
        try {
            gt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print("lobby exit");
    }
}
