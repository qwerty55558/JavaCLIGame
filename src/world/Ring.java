package world;

import lobby.Lobby;
import monster.Monster;
import myUtil.MyScanner;
import myUtil.MyTypeChecker;
import player.Character;
import skill.Skill;
import skill.attack.PowerStrike;
import skill.buff.Bless;

import java.util.*;

public class Ring {
    Character character;
    Monster monster;
    Scanner sc;
    Random rand;
    boolean finishFlag;
    int instanceExp;
    int turn;
    Map<Integer, Skill> buffmap;

    public Ring(Class<? extends Monster> monster) {
        character = Lobby.stUserProfile.getSelectedCharacter();
        try {
            this.monster = monster.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("Error creating monster");
        }
        sc = MyScanner.getInstance();
        instanceExp = 0;
        turn = 0;
        rand = new Random();
        buffmap = new HashMap<>();
    }

    public void init() {
        while (!finishFlag) {
            pTurn();
            if (!finishFlag) {
                mTurn();
            }
            if (buffmap.get(turn) != null) {
                Skill skill = buffmap.get(turn);
                if (skill.getSkillName().contains("축복")) {
                    Bless bless = (Bless) skill;
                    character.setStr(bless.getDebuff(character.getStr()));
                    character.setAtk();
                    System.out.println("축복 효과가 사라졌습니다.");
                    buffmap.remove(turn);
                }
            }
            turn++;
        }
        fightEndAction();
    }

    public void pTurn() {
        printMStatus();
        printAction();
        String s = sc.nextLine();
        if (!s.isEmpty() && MyTypeChecker.checkInteger(s)) {
            switch (Integer.parseInt(s)) {
                case 1:
                    monster.setHp(monster.getHp() - (character.getAtk() - monster.getDef()));
                    break;
                case 2:
                    useSkill();
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
                    printPStatus();
                    sc.nextLine();
                    pTurn();
                    return;
                case 5:
                    System.out.println("턴을 넘깁니다.");
                    break;
                default:
                    pTurn();
                    return;
            }
            if (monster.getHp() <= 0) {
                System.out.println("몬스터가 죽었습니다.");
                instanceExp = monster.getExp();
                finishFlag = true;
            }
        } else {
            System.out.println("잘못된 입력입니다.");
            pTurn();
            return;
        }
    }

    public Skill selectSkill() {
        Skill[] haveSkills = character.getHaveSkills();
        System.out.println("""
                ===================
                사용할 스킬을 선택해주세요.
                """);
        for (int i = 0; i < haveSkills.length; i++) {
            System.out.println((i + 1) + " : " + haveSkills[i].getSkillName() + " (MP : " + haveSkills[i].getMpCost() + ")");
        }
        System.out.println("""
                0 : 뒤로가기
                ===================
                """);
        String s = sc.nextLine();
        if (!s.isEmpty() && MyTypeChecker.checkInteger(s)) {
            int i = Integer.parseInt(s);
            if (i == 0) {
                pTurn();
                return null;
            }
            if (1 <= i && i <= haveSkills.length) {
                return haveSkills[i - 1];
            }
        }
        return null;
    }

    public void useSkill() {
        Skill skill = selectSkill();
        if (skill != null) {
            if (character.getMp() >= skill.getMpCost()) {
                character.setMp(character.getMp() - skill.getMpCost());
                if (skill.isBuff()) {
                    if (skill.getSkillName().contains("축복")) {

                        Bless bless = (Bless) skill;
                        if (buffmap.containsValue(bless)) {
                            System.out.println("이미 사용중인 버프입니다.");
                            return;
                        }else{
                            buffmap.put(turn + bless.getTurn(), bless);
                            character.setStr(bless.getBuffStatus(character.getStr()));
                            character.setAtk();
                            System.out.println("축복을 사용합니다.");
                        }
                    }
                }else {
                    int dmg = 0;
                    if (skill.getSkillName().contains("강타")) {
                        PowerStrike powerStrike = (PowerStrike) skill;
                        powerStrike.calcDmg(character.getStr());
                        dmg = powerStrike.getDmg();
                        System.out.println("강타를 사용합니다.");
                    }
                    monster.setHp(monster.getHp() - (dmg - monster.getDef()));
                }
            }else {
                System.out.println("MP가 부족합니다.");
                pTurn();
                return;
            }
        }else {
            System.out.println("잘못된 입력입니다.");
            pTurn();
            return;
        }
    }

    public void mTurn() {
        System.out.println("몬스터가 공격합니다.");
        if (isDodged()) {
            System.out.println("회피 성공!");
            return;
        }
        character.setHp(character.getHp() - (monster.getAtk() - character.getDef()));
        if (character.getHp() <= 0) {
            finishFlag = true;
            character.setAlive(false);
        }
        printPStatus();
    }

    public boolean isDodged() {
        return rand.nextDouble(1.0) < character.getDodgeChance();
    }

    public void printPStatus() {
        System.out.printf("""
                        ===================
                        %s :
                          HP : %d
                          MP : %d
                          ATK : %d
                          DEF : %d
                        ===================
                        """,
                character.getCharacterName(), character.getHp(), character.getMp(), character.getAtk(), character.getDef());
    }

    public void fightEndAction(){
        for (Integer i : buffmap.keySet()) {
            Skill skill = buffmap.get(i);
            if (skill.getSkillName().contains("축복")) {
                Bless bless = (Bless) skill;
                character.setStr(bless.getDebuff(character.getStr()));
                character.setAtk();
                System.out.println("축복 효과가 사라졌습니다.");
            }
        }
    }

    public void printMStatus() {
        System.out.printf("""
                        ===================
                        %s :
                           HP : %d
                          ATK : %d
                          DEF : %d
                        ===================
                        """,
                monster.getName(), monster.getHp(), monster.getAtk(), monster.getDef());
    }

    public void printAction() {
        System.out.println("""
                ===================
                행동을 선택해주세요.
                1 : 공격
                2 : 스킬
                3 : 도망치기
                4 : 내 스테이터스
                5 : 턴 넘기기
                ===================
                """);
    }
}
