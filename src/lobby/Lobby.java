package lobby;

import myUtil.MyProcessBuilder;
import myUtil.MyScanner;
import myUtil.MyTypeChecker;
import myUtil.MyUtils;
import profile.UserProfile;
import repository.RamUserRepository;

import java.util.Scanner;


public class Lobby {

    public static UserProfile stUserProfile;

    Scanner sc;
    RamUserRepository ramUserRepository;

    public Lobby() {
        System.out.println("Lobby construct");
    }

    public void init() {
        System.out.println("""
                ===================
                메뉴를 선택해주세요.
                1 : 회원가입
                2 : 로그인
                ===================
                """
        );

        ramUserRepository = RamUserRepository.getInstance();

        sc = MyScanner.getInstance();
        String s = sc.nextLine();
        if (MyTypeChecker.checkInteger(s)) {
            int i = Integer.parseInt(s);
            switch (i) {
                case 1:
                    System.out.flush();
                    signUp();
                    break;
                case 2:
                    System.out.flush();
                    login();
                    break;
            }
        }
    }

    public void signUp(){
        MyProcessBuilder.clearLine();
        System.out.print("사용하실 아이디를 6자 이하로 입력해주세요 :");
        String s = sc.nextLine();
        if (!s.isEmpty() && s.length() <= 6) {
            if (!ramUserRepository.hasInUserId(s)) {
                System.out.print("사용하실 비밀번호를 12자 이하로 입력해주세요 :");
                String pw = sc.nextLine();
                if (!pw.isEmpty() && pw.length() <= 12) {
                    UserProfile userProfile = new UserProfile(s, pw);
                    ramUserRepository.createUser(userProfile);
                    MyUtils.notice("성공적으로 계정을 생성하였습니다. 초기 화면으로 되돌아갑니다.");
                    init();
                }
            }else {
                MyUtils.notice("이미 존재하는 아이디입니다.");
            }
        }else {
            MyUtils.notice("사용할 수 없는 아이디입니다.");
        }
    }


    public void login(){
        MyProcessBuilder.clearLine();
        System.out.print("아이디를 입력해주세요 :");
        String id = sc.nextLine();
        if (!id.isEmpty() && id.length() <= 6) {
            if (ramUserRepository.hasInUserId(id)){
                System.out.print("12자 이하의 비밀번호를 입력해주세요 :");
                String pw = sc.nextLine();
                if (!pw.isEmpty() && pw.length() <= 12) {
                    UserProfile userProfile = ramUserRepository.loginUser(id, pw);
                    if (userProfile != null) {
                        LoginPage loginPage = new LoginPage();
                        stUserProfile = userProfile;
                        loginPage.init();
                    }
                }
            }else {
                MyUtils.notice("존재하지 않는 아이디입니다.");
                init();
            }
        }else{
            MyUtils.notice("6자리 이하의 아이디를 입력해주세요");
            init();
        }
    }






}
