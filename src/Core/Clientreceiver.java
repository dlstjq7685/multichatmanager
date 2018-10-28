package Core;

import java.io.IOException;
import java.io.InputStream;

import static Util.Printstr.print;

public class Clientreceiver implements Runnable{

    private InputStream client;
    private Group[] set;
    private int idx;

    {
        set = new Group[10];
        idx = 0;
    }


    Clientreceiver(InputStream cl, Group[] setter,int idx){
        this.client = cl;
        this.set = setter;
        this.idx = idx;
    }

    @Override
    public void run() {

        int recvMsgSize;
        byte byteBuffer[] = new byte[1024];

        try {
            while (true){
                recvMsgSize = client.read(byteBuffer);
                set[idx].setMesg(byteBuffer,recvMsgSize);
            }
        } catch (IOException e) {
            print("client socket out");
        }

    }
}
