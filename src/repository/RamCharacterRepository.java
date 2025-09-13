package repository;

import java.util.HashMap;
import java.util.Map;

import lobby.Lobby;
import player.Character;

public class RamCharacterRepository {
    private static final RamCharacterRepository INSTANCE = new RamCharacterRepository();

    private RamCharacterRepository(){}

    public static RamCharacterRepository getInstance() {
        return INSTANCE;
    }

    Map<String, Character[]> characterMap = new HashMap<>();

    public boolean createCharacter(Character character, String userId) {
        Character[] characters = characterMap.get(userId);
        if (characters == null) {
            characterMap.put(userId, new Character[]{character});
        }else if (characters.length < 3) {
            Character[] newCharacters = new Character[characters.length + 1];
            for (int i = 0; i < characters.length+1; i++) {
                if (i == characters.length) {
                    newCharacters[i] = character;
                    break;
                }
                newCharacters[i] = characters[i];
                characterMap.put(userId, newCharacters);
            }
            return true;
        } else if (characterMap.get(userId) != null && characterMap.get(userId).length > 3) {
            return false;
        }
        return false;
    }

    public Character[] getCharacters(String userId) {
        return characterMap.get(userId);
    }

    public void saveCharacter(String userId) {
        Character[] characters = characterMap.get(userId);
        if (characters != null) {
            for (int i = 0; i < characters.length; i++) {
                Character selectedCharacter = Lobby.stUserProfile.getSelectedCharacter();
                if (characters[i].getCharacterName().equals(selectedCharacter.getCharacterName())) {
                    characters[i] = selectedCharacter;
                    break;
                }
            }
        }
    }

}
