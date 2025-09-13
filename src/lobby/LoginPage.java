package lobby;

import myUtil.MyProcessBuilder;
import myUtil.MyScanner;
import myUtil.MyTypeChecker;
import myUtil.MyUtils;
import player.Character;
import profile.UserProfile;
import repository.RamCharacterRepository;
import repository.RamUserRepository;
import typePackage.MapType;

import java.util.Scanner;

public class LoginPage {

    Scanner sc;
    RamCharacterRepository ramCharacterRepository;
    UserProfile userProfile;


    public void init() {
        sc = MyScanner.getInstance();
        userProfile = Lobby.stUserProfile;
        System.out.println("""
                ===================
                메뉴를 선택해주세요.
                1 : 캐릭터 수 확인 & 생성 (최대 : 3)
                2 : 맵 입장
                3 : 캐릭터 선택
                4 : 캐릭터 스테이터스
                5 : 캐릭터 저장
                6 : 로그아웃
                ===================
                """
        );
        ramCharacterRepository = RamCharacterRepository.getInstance();

        String s = sc.nextLine();
        if (MyTypeChecker.checkInteger(s)) {
            int i = Integer.parseInt(s);
            switch (i) {
                case 1:
                    checkCharacter();
                    break;
                case 2:
                    choiceMap();
                    break;
                case 3:
                    selectCharacter();
                    break;
                case 4:
                    characterStatus();
                    break;
                case 5:
                    saveCharacter();
                    break;
                case 6:
                    logout();
                    break;
                default :
                    wrongChoice();
                    init();
            }
        } else {
            wrongChoice();
            init();
        }
    }

    public void saveCharacter(){
        ramCharacterRepository.saveCharacter(userProfile.getId());
        MyUtils.notice("선택된 캐릭터가 저장되었습니다.");
        init();
    }

    public void choiceMap(){
        if (Lobby.stUserProfile.getSelectedCharacter() != null) {
            System.out.println("""
            1. 산림
            2. 해안가
            3. 되돌아가기
            """);
            String s = sc.nextLine();
            if (MyTypeChecker.checkInteger(s)) {
                int i = Integer.parseInt(s);
                switch (i) {
                    case 1:
                        Map map = new Map();
                        map.init(MapType.WOODS);
                        init(); // 맵에서 나온 후 메인 메뉴로 복귀
                        break;
                    case 2:
                        Map map2 = new Map();
                        map2.init(MapType.SHORELINE);
                        init();
                        break;
                    default:
                        MyUtils.sleepNotice("이전 화면으로 되돌아갑니다.", 1500);
                        init();
                }
            } else {
                // 추가: 잘못된 입력 처리
                wrongChoice();
                choiceMap(); // 또는 init();
            }
        }else{
            System.out.println("캐릭터를 먼저 선택해주세요.");
            init();
        }

    }

    public void logout(){
        Lobby lobby = new Lobby();
        lobby.init();
    }

    public void characterStatus(){

        Character selectedCharacter = userProfile.getSelectedCharacter();
        if (selectedCharacter != null) {
            System.out.printf("""
                            소유자 : %s
                            HP : %d
                            MP : %d
                            공격력 : %d
                            방어력 : %d
                            회피확률 : %.0f
                            힘 : %d
                            민첩 : %d
                            """,
                    selectedCharacter.getUserId(),
                    selectedCharacter.getHp(),
                    selectedCharacter.getMp(),
                    selectedCharacter.getAtk(),
                    selectedCharacter.getDef(),
                    selectedCharacter.getDodgeChance() * 100,
                    selectedCharacter.getStr(),
                    selectedCharacter.getDex()
            );
        }else{
            System.out.println("캐릭터를 먼저 선택해주세요.");
        }

        sc.nextLine();
        init();
    }

    public void checkCharacter() {
        MyProcessBuilder.clearLine();
        Character[] characters = ramCharacterRepository.getCharacters(userProfile.getId());
        if (characters == null) {
            System.out.println("현재 존재하는 캐릭터가 없습니다.");
            System.out.println("캐릭터를 생성합니다.");
            createCharacter();
        }else {
            System.out.println("""
                ===================
                메뉴를 선택해주세요.
                1 : 캐릭터 확인
                2 : 캐릭터 생성
                ===================
                """);
            String s = sc.nextLine();
            if (MyTypeChecker.checkInteger(s)){
                int i = Integer.parseInt(s);
                switch (i) {
                    case 1:
                        characterListing(characters);
                        break;
                    case 2:
                        createCharacter();
                        break;
                    default:
                        wrongChoice();
                }
            }
        }
    }

    public void characterListing(Character[] characters) {
        System.out.println("캐릭터 ↓");
        for (Character character : characters) {
            System.out.println(character.getCharacterName());
        }
        sc.nextLine();
        init();
    }

    public void createCharacter(){
        try{
            Thread.sleep(1500);
        }catch (Exception e){
            Thread.currentThread().interrupt();
        }
        MyProcessBuilder.clearLine();
        System.out.print("사용하실 6자 이하의 캐릭터 이름을 정해주세요 :");
        String s = sc.nextLine();
        if (s.length() <= 6 && MyTypeChecker.checkString(s)) {
            Character character = new Character(userProfile.getId(), s);
            MyProcessBuilder.clearLine();
            if (ramCharacterRepository.createCharacter(character, userProfile.getId())){
                System.out.println("성공적으로 캐릭터를 생성했습니다.");
            }else {
                System.out.println("캐릭터 생성에 실패했습니다.");
            }
            init();
        }else {
            wrongChoice();
            init();
        }
    }

    public void selectCharacter(){
        MyProcessBuilder.clearLine();
        Character[] characters = ramCharacterRepository.getCharacters(userProfile.getId());
        if (characters != null) {
            for (int i = 0; i < characters.length; i++) {
                System.out.println("캐릭터 "+(i+1)+"번 :"+characters[i].getCharacterName());
            }
            System.out.print("몇 번 캐릭터를 선택하시겠습니까? :");
            String s = sc.nextLine();
            if (MyTypeChecker.checkInteger(s)) {
                try {
                    userProfile.setCharacter(characters[Integer.parseInt(s) - 1]);
                    RamUserRepository.getInstance().updateUser(userProfile);
                    init();
                } catch (Exception e) {
                    wrongChoice();
                    selectCharacter();
                }
            }else {
                wrongChoice();
                selectCharacter();
            }
        }else{
            System.out.println("현재 존재하는 캐릭터가 없습니다.");
            System.out.println("캐릭터를 생성합니다.");
            createCharacter();
        }
    }

    public void wrongChoice() {
        System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}
