import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Date;


public class Main {
    private static final int BUFSIZE = 1024; // 受信バッファの大きさ
    private static int port = 8000;

    public static void main(String[] args) throws IOException {

        ServerSocket servSock = new ServerSocket(port);

        int recvMsgSize;
        byte byteBuffer[] = new byte[BUFSIZE];

        while (true) {
            Socket clntSock = servSock.accept();
            Date now = new Date();
            System.out.println(now.toString() + "  came to ： " + clntSock.getInetAddress().getHostAddress());

            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();

            try{
                while (true) {
                    recvMsgSize = in.read(byteBuffer);
                    out.write(byteBuffer, 0, recvMsgSize);
                }
            } catch (IOException e){
                now = new Date();
                System.out.println(now.toString() +  "  close client : " + clntSock.getInetAddress().getHostAddress());
                clntSock.close();
                break;
            }

        }
        System.out.println("Terminate this program");

    }
}
