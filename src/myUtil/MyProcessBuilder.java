package myUtil;

public class MyProcessBuilder {

    static ProcessBuilder pb;

    public static void clearLine(){
        try {
            String lowerCase = System.getProperty("os.name").toLowerCase();
            if (lowerCase.contains("windows")) {
                pb = new ProcessBuilder("cmd", "/c", "cls");
            } else {
                pb = new ProcessBuilder("clear");
            }

            pb.inheritIO()
                    .start()
                    .waitFor();
        } catch (Exception e) {
            System.out.println("Clear Line Error!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }

    }
}
