package lobby;

import monster.shoreline.Spider;
import monster.shoreline.Zombie;
import monster.woods.Goblin;
import monster.woods.Slime;
import myUtil.MyProcessBuilder;
import myUtil.MyScanner;
import myUtil.MyTypeChecker;
import typePackage.MapType;
import world.World;

import java.util.Scanner;

public class Map {
    Scanner sc;

    public void init(MapType mt){
        sc = MyScanner.getInstance();
        switch (mt) {
            case WOODS:
                worldLoading(new World(100, 100, 15, new Class[]{Slime.class, Goblin.class}));
                break;
            case SHORELINE:
                worldLoading(new World(125, 125, 20, new Class[]{Spider.class, Zombie.class}));
                break;
            default:
                Lobby lobby = new Lobby();
                lobby.init();
                sc = null;
        }
    }

    public void worldLoading(World world){
        MyProcessBuilder.clearLine();
        int[] dropPoint = world.getDropPoint();
        System.out.printf("현재 드랍 포인트는 x : %d, y : %d 입니다.\n", dropPoint[0], dropPoint[1]);
        while (true) {
            String s = sc.nextLine();
            if (MyTypeChecker.checkString(s)) {
                switch (s) {
                    case "w":
                        world.workN();
                        break;
                    case "a":
                        world.workW();
                        break;
                    case "s":
                        world.workS();
                        break;
                    case "d":
                        world.workE();
                        break;
                    }
            }
            if (world.isExitFlag()) {
                System.out.println("맵을 탈출하여 로비로 돌아갑니다.");
                break;
            }
            if (!Lobby.stUserProfile.getSelectedCharacter().isAlive()) {
                System.out.println("캐릭터가 사망하여 로비로 돌아갑니다.");
                break;
            }
        }
    }
}
