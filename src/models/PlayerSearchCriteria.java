package models;

import jakarta.servlet.http.HttpServletRequest;

public class PlayerSearchCriteria {
    private String username;
    private String characterName;
    private Integer minLevel;
    private Integer maxLevel;
    private Integer factionId;

    /* ------------------------------ Constructors ------------------------------ */
    public PlayerSearchCriteria() {
        this.setUsername(null);
        this.setCharacterName(null);
        this.setMinLevel(null);
        this.setMaxLevel(null);
        this.setFactionId(null);
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

    public Integer getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(Integer minLevel) {
        this.minLevel = minLevel;
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
    }

    public Integer getFactionId() {
        return factionId;
    }

    public void setFactionId(Integer factionId) {
        this.factionId = factionId;
    }

    /* ------------------------------ Class methods ----------------------------- */
    public static PlayerSearchCriteria getCriteriaFromRequest(HttpServletRequest req) {
        PlayerSearchCriteria criteria = new PlayerSearchCriteria();

        // Retrieve username
        String username = req.getParameter("username");
        if (username != null && !username.trim().isEmpty()) {
            criteria.setUsername(username.trim());
        }

        // Retrieve characterName
        String characterName = req.getParameter("character-name");
        if (characterName != null && !characterName.trim().isEmpty()) {
            criteria.setCharacterName(characterName.trim());
        }

        // Retrieve minLevel
        String minLevelStr = req.getParameter("min-level");
        if (minLevelStr != null && !minLevelStr.trim().isEmpty()) {
            Integer minLevel;

            try {
                minLevel = Integer.parseInt(minLevelStr.trim());
            } catch (NumberFormatException e) {
                minLevel = null;
            }

            criteria.setMinLevel(minLevel);
        }

        // Retrieve maxLevel
        String maxLevelStr = req.getParameter("max-level");
        if (maxLevelStr != null && !maxLevelStr.trim().isEmpty()) {
            Integer maxLevel;

            try {
                maxLevel = Integer.parseInt(maxLevelStr.trim());
            } catch (NumberFormatException e) {
                maxLevel = null;
            }

            criteria.setMaxLevel(maxLevel);
        }

        // Retrieve factionId
        String factionIdStr = req.getParameter("faction-id");
        if (factionIdStr != null && !factionIdStr.trim().isEmpty()) {
            Integer factionId;

            try {
                factionId = Integer.parseInt(factionIdStr.trim());
            } catch (NumberFormatException e) {
                factionId = null;
            }

            criteria.setFactionId(factionId);
        }

        return criteria;
    }
}
