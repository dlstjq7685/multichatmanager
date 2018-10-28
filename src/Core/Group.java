package Core;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import static Util.Printstr.print;

public class Group implements Runnable{

    private Socket members[];
    private byte mesg[];
    private int mesglen;
    private int _members;
    private String name;
    private Group[] groups;

    {
        _members = 0;
        members = new Socket[50];
        groups = new Group[10];

    }

    Group(String gname){
        name = gname;
    }

    public void setMembers(Socket clients){
        members[_members] = clients;
        _members++;
    }

    public void resetMembers(Socket member){
        
    }


    public void setMesg(byte[] mesg, int len) {
        this.mesg = mesg;
        this.mesglen = len;
    }

    public void setGroups(Group[] groups){
        this.groups = groups;
    }

    @Override
    public void run() {
        print("Start " + name + " Sender....");
        try{
            while (!Thread.interrupted()){
                if(mesglen > 0){
                    sendall();
                    mesg = null;
                    mesglen = 0;
                }
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            print(name +  " exit....");
        }
        terminate();
    }

    private void terminate(){
        for(int i =0; i < _members; i++){
            try {
                members[i].close();
            } catch (IOException e) {
            }
        }
        print("End "+ name + " Sender");
    }

    private void sendall(){
        for(int i = 0;i < _members; i++){
            try{
                OutputStream out = members[i].getOutputStream();
                out.write(mesg,0,mesglen);
            }catch (IOException e){
                //socket collection
                members[i] = new Socket();
                Socket dump[] = new Socket[50];
                int dumplen = 0;
                for(int j = 0; j < _members; j++){
                    if(members[j].isConnected()){
                        dump[dumplen] = members[j];
                        dumplen++;
                    }
                }
                members = dump;
                _members = dumplen;
                break;
            }
        }
    }
}