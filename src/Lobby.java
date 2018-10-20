import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Date;

public class Lobby implements Runnable {

    private int recvMsgSize;
    private byte byteBuffer[];
    private ServerSocket servSock;
    private final int BUFSIZE = 1024;
    private final int port = 8000;

    {
        byteBuffer = new byte[BUFSIZE];
    }

    @Override
    public void run() {
        try {
            servSock = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Couldn't open socket port");
        }

        System.out.println("Open port & Running server");

        Socket clntSock;

        try{
            while (!Thread.interrupted()) {

                clntSock = servSock.accept();
                // Socket clntSock = Dump.servSock.accept();
                Date now = new Date();
                System.out.println(now.toString() + "  came to ： " + clntSock.getInetAddress().getHostAddress());

                InputStream in = clntSock.getInputStream();
                OutputStream out = clntSock.getOutputStream();

                try{
                    while (true) {
                        recvMsgSize = in.read(byteBuffer);
                        out.write(byteBuffer, 0, recvMsgSize);
                        Thread.sleep(10);
                    }
                } catch (IOException e){
                    now = new Date();
                    System.out.println(now.toString() +  "  closed client : " + clntSock.getInetAddress().getHostAddress());
                    clntSock.close();
                    servSock.close();
                    break;
                }
            }
        }
        catch (InterruptedException e){ System.out.println("lobby exit"); }
        catch (IOException e){ System.out.println("socket error"); }

        servSock = null;
        clntSock = null;

        System.out.println("lobby exit");

    }
}
