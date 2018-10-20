import java.io.IOException;
import java.util.Scanner;

public class Consol implements Runnable{

    // lobby process l
    private Thread l;
    private Lobby lobby;
    private Scanner scanner;

    Consol(){
        lobby = new Lobby();
        l = new Thread(lobby);
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        try{
            while (true){
                System.out.print(">");
                String in = scanner.next();
                if(in.equals("exit"))
                    break;
                switchloop(in);
            }
        } catch (Exception e){
        }

        terminate();
        System.out.println("console end");

    }

    private void terminate(){
        l.interrupt();
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
