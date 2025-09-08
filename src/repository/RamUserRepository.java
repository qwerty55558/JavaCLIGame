package repository;

import profile.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class RamUserRepository {
    private final static RamUserRepository INSTANCE = new RamUserRepository();

    private RamUserRepository(){}

    public static RamUserRepository getInstance(){
        return INSTANCE;
    }

    Map<String, UserProfile> userData = new HashMap<>();

    public boolean createUser(UserProfile user){
        if (userData.containsKey(user.getId())) {
            return false;
        }
        userData.put(user.getId(), user);
        System.out.println("create User : " + user.getId());
        return true;
    }

    public void updateUser(UserProfile... user) {
        for (UserProfile userProfile : user) {
            userData.put(userProfile.getId(), userProfile);
        }
    }

    public UserProfile loginUser(String id, String pw){
        try {
            UserProfile userProfile = userData.get(id);
            if (userProfile.getPw().equals(pw)) {
                return userProfile;
            }else {
                System.out.println("loginFail!!!!!!!!!!!");
                return null;
            }
        } catch (Exception e) {
            System.out.println("loginUserError!!!!!!!!!!");
            return null;
        }
    }

    public boolean hasInUserId(String id) {
        return userData.containsKey(id);
    }



}
