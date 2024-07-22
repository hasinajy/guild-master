package models;

import jakarta.servlet.http.HttpServletRequest;

public class InventorySearchCriteria {
    private Integer itemId;
    private String characterName;
    private Integer minDurability;
    private Integer maxDurability;
    private Integer typeId;
    private Integer rarityId;

    /* ------------------------------ Constructors ------------------------------ */
    public InventorySearchCriteria() {
        this.setItemId((Integer) null);
        this.setCharacterName(null);
        this.setMinDurability((Integer) null);
        this.setMaxDurability((Integer) null);
        this.setTypeId((Integer) null);
        this.setRarityId((Integer) null);
    }

    /* --------------------------- Getters and setters -------------------------- */
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public void setItemId(String itemId) {
        try {
            this.setItemId(Integer.parseInt(itemId));
        } catch (NumberFormatException e) {
            this.setItemId((Integer) null);
        }
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public Integer getMinDurability() {
        return minDurability;
    }

    public void setMinDurability(Integer minDurability) {
        this.minDurability = minDurability;
    }

    public void setMinDurability(String minDurability) {
        try {
            this.setMinDurability(Integer.parseInt(minDurability));
        } catch (NumberFormatException e) {
            this.setMinDurability((Integer) null);
        }
    }

    public Integer getMaxDurability() {
        return maxDurability;
    }

    public void setMaxDurability(Integer maxDurability) {
        this.maxDurability = maxDurability;
    }

    public void setMaxDurability(String maxDurability) {
        try {
            this.setMinDurability(Integer.parseInt(maxDurability));
        } catch (NumberFormatException e) {
            this.setMinDurability((Integer) null);
        }
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public void setTypeId(String typeId) {
        try {
            this.setTypeId(Integer.parseInt(typeId));
        } catch (NumberFormatException e) {
            this.setTypeId((Integer) null);
        }
    }

    public Integer getRarityId() {
        return rarityId;
    }

    public void setRarityId(Integer rarityId) {
        this.rarityId = rarityId;
    }

    public void setRarityId(String rarityId) {
        try {
            this.setRarityId(Integer.parseInt(rarityId));
        } catch (NumberFormatException e) {
            this.setRarityId((Integer) null);
        }
    }

    /* ------------------------------ Class methods ----------------------------- */
    public static InventorySearchCriteria getCriteriaFromRequest(HttpServletRequest req) {
        InventorySearchCriteria criteria = new InventorySearchCriteria();

        String itemIdParam = req.getParameter("item-id");
        String characterNameParam = req.getParameter("character-name");
        String minDurabilityParam = req.getParameter("min-durability");
        String maxDurabilityParam = req.getParameter("max-durability");
        String typeIdParam = req.getParameter("type-id");
        String rarityIdParam = req.getParameter("rarity-id");

        if (isValidParameter(itemIdParam)) {
            criteria.setItemId(itemIdParam);
        }

        if (isValidParameter(characterNameParam)) {
            criteria.setCharacterName(characterNameParam);
        }

        if (isValidParameter(minDurabilityParam)) {
            criteria.setMinDurability(minDurabilityParam);
        }

        if (isValidParameter(maxDurabilityParam)) {
            criteria.setMaxDurability(maxDurabilityParam);
        }

        if (isValidParameter(typeIdParam)) {
            criteria.setTypeId(typeIdParam);
        }

        if (isValidParameter(rarityIdParam)) {
            criteria.setRarityId(rarityIdParam);
        }

        return criteria;
    }

    private static boolean isValidParameter(String param) {
        return (param != null && !param.trim().isEmpty());
    }
}
