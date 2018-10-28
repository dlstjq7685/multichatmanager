package Core;
import static Util.Printstr.print;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Console implements Runnable{

    // lobby process l
    private Thread l;
    private Lobby lobby;
    private Scanner scanner;
    public static boolean flag;

    public Console(){
        flag = true;
        lobby = new Lobby();
        l = new Thread(lobby);
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        try{
            while (true){
                String in = scanner.next();
                if(in.equals("exit"))
                    break;
                switchloop(in);
            }
        } catch (Exception e){
        }

        print("console end....");
        terminate();
        print("System terminate");
    }

    private void terminate(){
        print("Waitting Lobby termination.....");
        l.interrupt();

        print("dump client running.....");
        try {
            flag = false;
            Socket dc = new Socket("localhost", 8000);
            dc.close();
        } catch (IOException e) {
        }
        print("dump client out...");

        try {
            l.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scanner.close();
        l = null;
        lobby = null;
        scanner = null;

    }

    private void switchloop(String in){
        switch (in){
            case "start":
                if(!l.isAlive())
                    this.lobby_run();
                break;
        }
    }

    private void lobby_run(){
        l.start();
    }
}
