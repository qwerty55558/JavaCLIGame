package world;

import lobby.Lobby;
import monster.Monster;
import monster.woods.Goblin;
import monster.woods.Slime;

import java.util.Random;

public class World {
    private int[][] map;
    private int mobSpawnChance;
    private int[] exitPoint;
    private int[] dropPoint;
    private int[] characterPosition;
    private final Class<? extends Monster>[] spawnTable;
    private Random rnd;
    private int stackExp;
    private boolean exitFlag = false;

    public int[] getCharacterPosition() {
        return characterPosition;
    }

    public int[] getExitPoint() {
        return exitPoint;
    }

    public int[] getDropPoint() {
        return dropPoint;
    }

    public World(int x, int y, int mobSpawnChance, Class<? extends Monster>[] spawnTable) {
        this.spawnTable = spawnTable;
        map = new int[x][y];
        this.mobSpawnChance = mobSpawnChance;
        this.exitPoint = new int[2];
        this.dropPoint = new int[2];
        this.characterPosition = new int[2];
        rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
        init();
    }

    public void init(){
        dropCharacter(rnd);
        setExitPoint(rnd);
    }

    public void dropCharacter(Random rnd) {
        int x = rnd.nextInt(map.length);
        int y = rnd.nextInt(map[0].length);
        dropPoint[0] = x;
        dropPoint[1] = y;
        characterPosition[0] = x;
        characterPosition[1] = y;
    }

    public void setExitPoint(Random rnd) {
        int x = rnd.nextInt(map.length);
        int y = rnd.nextInt(map[0].length);
        exitPoint[0] = x;
        exitPoint[1] = y;
        System.out.printf("탈출구는 %d, %d\n", x, y);
    }

    public boolean workN() {
        if (characterPosition[1] < map[0].length) {
            characterPosition[1]++;
            workLogics();
            return true;
        }else{
            System.out.println("맵의 경계선입니다.");
            return false;
        }
    }

    public boolean workS(){
        if (characterPosition[1] >  0) {
            characterPosition[1]--;
            workLogics();
            return true;
        }else{
            System.out.println("맵의 경계선입니다.");
            return false;
        }
    }

    public boolean workE(){
        if (characterPosition[0] < map.length) {
            characterPosition[0]++;
            workLogics();
            return true;
        }else{
            System.out.println("맵의 경계선입니다.");
            return false;
        }
    }

    public boolean workW(){
        if (characterPosition[0] > 0) {
            characterPosition[0]--;
            workLogics();
            return true;
        }else{
            System.out.println("맵의 경계선입니다.");
            return false;
        }
    }

    public boolean isExitFlag() {
        return exitFlag;
    }

    public void workLogics(){
        rnd.setSeed(System.currentTimeMillis());
        if (rnd.nextInt(100) <  mobSpawnChance) {
            System.out.println("mobSpawn");
            Ring ring = new Ring(spawnTable[rnd.nextInt(spawnTable.length)]);
            ring.init();
            Lobby.stUserProfile.getSelectedCharacter().setExp(Lobby.stUserProfile.getSelectedCharacter().getExp() + ring.instanceExp);
        }
        if (characterPosition[0] == exitPoint[0] && characterPosition[1] == exitPoint[1]) {
            System.out.println("exit");
            exitFlag = true;
        }
        System.out.printf("현재 좌표는 %d , %d\n", characterPosition[0], characterPosition[1]);

    }


}
