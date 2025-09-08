package myUtil;

public class MyUtils {
    public static void notice(String s){
        MyProcessBuilder.clearLine();
        System.out.println(s);
    }

    public static void sleepNotice(String s, int time) {
        MyProcessBuilder.clearLine();
        System.out.println(s);
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}
