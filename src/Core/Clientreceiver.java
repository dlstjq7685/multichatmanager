package Core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static Util.Printstr.print;

public class Clientreceiver implements Runnable{

    private InputStream client;
    private Group[] set;
    private int idx;
    private Socket cli;

    {
        set = new Group[10];
        idx = 0;
    }


    Clientreceiver(Group[] setter,int idx, Socket cli) {
        this.set = setter;
        this.idx = idx;
        this.cli = cli;
        try {
            client = this.cli.getInputStream();
        } catch (IOException e) {
            try {
                this.cli.close();
            } catch (IOException e1) {
            }
        }
    }

    private void chgroup(int g){
        Socket member = set[idx].getMembers(cli);
        set[g].setMembers(member);
        idx = g;
    }

    @Override
    public void run() {

        int recvMsgSize;
        byte byteBuffer[] = new byte[1024];

        try {
            while (true){
                recvMsgSize = client.read(byteBuffer);
                String meg = new String(byteBuffer);
                if(meg.contains("change")){
                    chgroup(Integer.parseInt(meg.substring(recvMsgSize -1, recvMsgSize)));
                }
                else{
                    set[idx].setMesg(byteBuffer,recvMsgSize);
                }
            }
        } catch (IOException e) {
            print("client socket out");
        }

    }
}
