package lobby;

import myUtil.MyProcessBuilder;
import myUtil.MyScanner;
import myUtil.MyTypeChecker;
import typePackage.MapType;
import world.Woods;

import java.util.Scanner;

public class Map {
    Scanner sc;

    public void init(MapType mt){
        sc = MyScanner.getInstance();
        switch (mt) {
            case WOODS:
                woodsLoading();
                break;
            default:
                Lobby lobby = new Lobby();
                lobby.init();
                sc = null;
        }
    }

    public void woodsLoading(){
        MyProcessBuilder.clearLine();
        Woods woods = new Woods(100, 100, 15);
        int[] dropPoint = woods.getDropPoint();
        System.out.printf("현재 드랍 포인트는 x : %d, y : %d 입니다.\n", dropPoint[0], dropPoint[1]);
        while (true) {
            String s = sc.nextLine();
            if (MyTypeChecker.checkString(s)) {
                switch (s) {
                    case "w":
                        woods.workN();
                        break;
                    case "a":
                        woods.workW();
                        break;
                    case "s":
                        woods.workS();
                        break;
                    case "d":
                        woods.workE();
                        break;
                    }
            }
            if (woods.isExitFlag()) {
                break;
            }
        }
    }
}
