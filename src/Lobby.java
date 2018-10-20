import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Date;

public class Lobby implements Runnable {

    private ServerSocket servSock;
    private Group group1;
    private Thread gt;
    private final int port = 8000;
    private static Date now;

    {
        group1 = new Group("first");
        gt = new Thread(group1);
        try {
            servSock = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Couldn't open socket port");
        }
    }

    @Override
    public void run() {

        currentstring("Open port & Running server");
        Socket clntSock;
        try{
            gt.start();

            while (!Thread.interrupted()) {
                clntSock = servSock.accept();
                currentstring("came to : " + clntSock.getInetAddress().getHostAddress());

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
        catch (IOException e){ System.out.println("socket error"); }

        terminate();
    }

    public static void currentstring(String str){
        now = new Date();
        System.out.println(now.toString() + "] " + str);
    }

    private void terminate(){
        try {
            servSock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gt.interrupt();
        try {
            gt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        currentstring("lobby exit");
    }
}
