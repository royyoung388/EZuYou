package com.you.ezuyou.Home;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/20.
 */

public class Item {

    private Bitmap image;
    private String id, tag, status, person, name, rent, sell, introduce;

    public List<Item> Data = new ArrayList<>();

    public Item(Bitmap image, String id, String tag, String status, String person, String name, String sell, String rent, String introduce) {
        this.id = id;
        this.tag = tag;
        this.status = status;
        this.person = person;
        this.image = image;
        this.name = name;
        this.sell = sell;
        this.rent = rent;
        this.introduce = introduce;
    }

    public Item(Bitmap[] image, String string) {
        Pattern pattern = Pattern.compile("\\{\\s*" +
                "id:(\\S*?);\\s*" +
                "tag:(\\S*?);\\s*" +
                "status:(\\S*?);\\s*" +
                "person:(\\S*?);\\s*" +
                "name:(\\S*?);\\s*" +
                "sell:(\\S*?);\\s*" +
                "rent:(\\S*?);\\s*" +
                "introduce:(\\S*?);\\s*" +
                "\\},\\s*");
        Matcher matcher = pattern.matcher(string);
        int i = 0;
        while (matcher.find()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
            System.out.println(matcher.group(4));
            System.out.println(matcher.group(5));
            System.out.println(matcher.group(6));
            System.out.println(matcher.group(7));
            System.out.println(matcher.group(8));

            Item item = new Item(
                    image[i],
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(3),
                    matcher.group(4),
                    matcher.group(5),
                    matcher.group(6),
                    matcher.group(7),
                    matcher.group(8)
            );
            System.out.println(item);
            Data.add(item);
            i++;
        }
        System.out.println(i);
    }

    public Bitmap getImage() {
        return image;
    }

    public String getID() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public String getStatus() {
        return status;
    }

    public String getPerson() {
        return person;
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

    public static int getSize(String string) {
        Pattern pattern = Pattern.compile("\\{\\s*" +
                "id:(\\S*?);\\s*" +
                "tag:(\\S*?);\\s*" +
                "status:(\\S*?);\\s*" +
                "person:(\\S*?);\\s*" +
                "name:(\\S*?);\\s*" +
                "sell:(\\S*?);\\s*" +
                "rent:(\\S*?);\\s*" +
                "introduce:(\\S*?);\\s*" +
                "\\},\\s*");
        Matcher matcher = pattern.matcher(string);
        int i = 0;
        while (matcher.find()) {
            i++;
        }
        return i;
    }
}
