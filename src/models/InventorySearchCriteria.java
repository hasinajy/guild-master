package models;

public class InventorySearchCriteria {
    private Integer itemId;
    private String characterName;
    private Float minDurability;
    private Float maxDurability;
    private Integer typeId;
    private Integer rarityId;

    // Getters and setters
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

    public Float getMinDurability() {
        return minDurability;
    }

    public void setMinDurability(Float minDurability) {
        this.minDurability = minDurability;
    }

    public Float getMaxDurability() {
        return maxDurability;
    }

    public void setMaxDurability(Float maxDurability) {
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
}
