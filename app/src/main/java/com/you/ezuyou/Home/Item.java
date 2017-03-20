package com.you.ezuyou.Home;

/**
 * Created by Administrator on 2017/3/20.
 */

public class Item {

    private int image;
    private String name;
    private String rent;
    private String sell;
    private String introduce;

    public Item(int image, String name, String rent, String sell, String introduce) {
        this.image = image;
        this.name = name;
        this.rent = rent;
        this.sell = sell;
        this.introduce = introduce;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getRent() {
        return rent;
    }

    public String getSell() {
        return sell;
    }

    public String getIntroduce() {
        return introduce;
    }
}
