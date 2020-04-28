package com.ipedg.minecraft.replaceitem.Entity;

public class MapItemEntity {
    private String keyItem;
    private String valueItem;


    public MapItemEntity() {
        this.keyItem="";
        this.valueItem="";
    }
    public MapItemEntity(String keyItem, String valueItem) {
        this.keyItem = keyItem;
        this.valueItem = valueItem;
    }

    @Override
    public String toString() {
        return "MapItemEntity{" +
                "keyItem='" + keyItem + '\'' +
                ", valueItem='" + valueItem + '\'' +
                '}';
    }

    public String getKeyItem() {
        return keyItem;
    }

    public void setKeyItem(String keyItem) {
        this.keyItem = keyItem;
    }

    public String getValueItem() {
        return valueItem;
    }

    public void setValueItem(String valueItem) {
        this.valueItem = valueItem;
    }
}
