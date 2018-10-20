import Core.Console;
import static Util.Printstr.print;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Console main = new Console();
        Thread t =  new Thread(main,"main consol");
        t.start();
        print("Start main");

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
