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
        this.setItemId(null);
        this.setCharacterName(null);
        this.setMinDurability(null);
        this.setMaxDurability(null);
        this.setTypeId(null);
        this.setRarityId(null);
    }

    /* --------------------------- Getters and setters -------------------------- */
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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

    public Integer getMaxDurability() {
        return maxDurability;
    }

    public void setMaxDurability(Integer maxDurability) {
        this.maxDurability = maxDurability;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getRarityId() {
        return rarityId;
    }

    public void setRarityId(Integer rarityId) {
        this.rarityId = rarityId;
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

        if (itemIdParam != null && !itemIdParam.isEmpty()) {
            Integer itemId;

            try {
                itemId = Integer.parseInt(itemIdParam);
            } catch (NumberFormatException e) {
                itemId = null;
            }

            criteria.setItemId(itemId);
        }

        if (characterNameParam != null && !characterNameParam.isEmpty()) {
            criteria.setCharacterName(characterNameParam);
        }

        if (minDurabilityParam != null && !minDurabilityParam.isEmpty()) {
            Integer minDurability;

            try {
                minDurability = Integer.parseInt(minDurabilityParam);
            } catch (NumberFormatException e) {
                minDurability = null;
            }

            criteria.setMinDurability(minDurability);
        }

        if (maxDurabilityParam != null && !maxDurabilityParam.isEmpty()) {
            Integer maxDurability;

            try {
                maxDurability = Integer.parseInt(maxDurabilityParam);
            } catch (NumberFormatException e) {
                maxDurability = null;
            }

            criteria.setMaxDurability(maxDurability);
        }

        if (typeIdParam != null && !typeIdParam.isEmpty()) {
            Integer typeId;

            try {
                typeId = Integer.parseInt(typeIdParam);
            } catch (NumberFormatException e) {
                typeId = null;
            }

            criteria.setItemId(typeId);
        }

        if (rarityIdParam != null && !rarityIdParam.isEmpty()) {
            Integer rarityId;

            try {
                rarityId = Integer.parseInt(rarityIdParam);
            } catch (NumberFormatException e) {
                rarityId = null;
            }

            criteria.setItemId(rarityId);
        }

        return criteria;
    }
}
