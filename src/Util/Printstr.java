package Util;

import java.util.Date;

public class Printstr {

    static private Date now;

    public  static void print(String str){
        now = new Date();
        System.out.println(now.toString() + "] " + str);
    }
}
