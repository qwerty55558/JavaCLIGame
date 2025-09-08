package profile;

import player.Character;

public class UserProfile {
    private String id;
    private String pw;
    private Character selectedCharacter;


    public UserProfile(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public void setCharacter(Character character){
        this.selectedCharacter = character;
    }

    public Character getSelectedCharacter(){
        return selectedCharacter;
    }

    public String getId(){
        return id;
    }

    public String getPw(){
        return pw;
    }
}
