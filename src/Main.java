import java.io.IOException;

// error
// static values memory leak
public class Main {

    public static void main(String[] args) throws IOException {

        Consol main = new Consol();
        Thread t =  new Thread(main,"main consol");
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
