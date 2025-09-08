package world;

import lobby.Lobby;
import monster.Monster;
import myUtil.MyScanner;
import myUtil.MyTypeChecker;
import player.Character;

import java.util.Random;
import java.util.Scanner;

public class Ring {
    Character character;
    Monster monster;
    Scanner sc;
    Random rand;
    boolean finishFlag;
    int instanceExp;

    public Ring(Class<? extends Monster> monster) {
        character = Lobby.stUserProfile.getSelectedCharacter();
        try {
            this.monster = monster.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("Error creating monster");
        }
        sc = MyScanner.getInstance();
        instanceExp = 0;
        rand = new Random();
    }

    public void init(){
        while(!finishFlag){
            turn();
            if (!finishFlag) {
                mTurn();
            }
        }
    }

    public void turn(){
        System.out.printf("""
                ===================
                %s :
                   HP : %d
                  ATK : %d
                  DEF : %d
                ===================
                """,
                monster.getName(),monster.getHp(),monster.getAtk(),monster.getDef());
        System.out.println("""
                ===================
                행동을 선택해주세요.
                1 : 공격
                2 : 스킬
                3 : 도망치기
                4 : 내 스테이터스
                ===================
                """);
        String s = sc.nextLine();
        if (!s.isEmpty() && MyTypeChecker.checkInteger(s)) {
            switch (Integer.parseInt(s)) {
                case 1:
                    monster.setHp(monster.getHp() - (character.getAtk() - monster.getDef()));
                    if (monster.getHp() <= 0) {
                        System.out.println("몬스터가 죽었습니다.");
                        instanceExp = monster.getExp();
                        finishFlag = true;
                    }
                    break;
                case 2:
                    System.out.println("미구현");
                    init();
                    break;
                case 3:
                    if (rand.nextInt(100) < 50) {
                        System.out.println("도망치기 성공");
                        finishFlag = true;
                    } else {
                        System.out.println("도망치기 실패");
                    }
                    break;
                case 4:
                    System.out.printf("""
                                    ===================
                                    %s :
                                      HP : %d
                                      ATK : %d
                                      DEF : %d
                                    ===================
                                    """,
                            character.getCharacterName(), character.getHp(), character.getAtk(), character.getDef());
                    sc.nextLine();
                    init();
                    break;
                default:
                    init();
                    break;
            }
        } else {
            System.out.println("잘못된 입력입니다.");
            init();
        }
    }

    public void mTurn(){
        System.out.println("몬스터가 공격합니다.");
        character.setHp(character.getHp() - (monster.getAtk() - character.getDef()));
        if (character.getHp() <= 0){
            finishFlag=true;
            character.setAlive(false);
        }
        System.out.printf("""
                ===================
                %s :
                  HP : %d
                  ATK : %d
                  DEF : %d
                ===================
                """,
                character.getCharacterName(),character.getHp(),character.getAtk(),character.getDef());
    }
}
