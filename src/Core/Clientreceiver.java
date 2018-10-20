package Core;

import java.io.IOException;
import java.io.InputStream;

import static Util.Printstr.print;

public class Clientreceiver implements Runnable{

    private InputStream client;
    private Group set;

    Clientreceiver(InputStream cl, Group setter){
        this.client = cl;
        set = setter;
    }

    @Override
    public void run() {
        // Core.Lobby.currentstring("Start client receiver....");
        // String getmessage;
        int recvMsgSize;
        byte byteBuffer[] = new byte[1024];

        try {

            while (true){
                recvMsgSize = client.read(byteBuffer);
                set.setMesg(byteBuffer,recvMsgSize);
                // System.out.printf("%s\n",new String(byteBuffer));
            }

        } catch (IOException e) {
            print("client socket out");
        }

    }
}
