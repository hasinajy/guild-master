package models;

public class Name {
    private String username;
    private String characterName;

    /* ------------------------------ Constructors ------------------------------ */
    public Name() {
    }

    public Name(String username, String characterName) {
        this.setUsername(username);
        this.setCharacterName(characterName);
    }

    /* --------------------------- Getters and setters -------------------------- */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
