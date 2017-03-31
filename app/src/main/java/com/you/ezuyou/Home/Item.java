package com.you.ezuyou.Home;

import com.you.ezuyou.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/20.
 */

public class Item {

    private int image;
    private String name, rent, sell, introduce;

    public List<Item> Data = new ArrayList<>();

    public Item(int image, String name, String sell, String rent, String introduce) {
        this.image = image;
        this.name = name;
        this.sell = sell;
        this.rent = rent;
        this.introduce = introduce;
    }

    public Item(int[] image, String string) {
        Pattern pattern = Pattern.compile("\\{\\s*name:(\\S*?);\\s*" +
                                            "sell:(\\S*?);\\s*" +
                                            "rent:(\\S*?);\\s*" +
                                            "introduce:(\\S*?);\\s*\\},");
        Matcher matcher = pattern.matcher(string);
        int i = 0;
        while (matcher.find()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
            System.out.println(matcher.group(4));
            Item item = new Item(
                    image[i],
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(3),
                    matcher.group(4)
            );
            System.out.println(item);
            Data.add(item);
            i++;
        }
        System.out.println(i);
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
    private String getName(String string) {
        Pattern pattern = Pattern.compile("name:(\\S);");
        Matcher matcher = pattern.matcher(string);
        String name = "";
        while (matcher.find()) {
            name += matcher.group();
        }

        return name;
    }

    public String getRent() {
        return rent;
    }
    private String getRent(String string) {
        Pattern pattern = Pattern.compile("rent:(\\S);");
        Matcher matcher = pattern.matcher(string);

        String rent = "";
        while (matcher.find()) {
            rent += matcher.group();
        }
        return rent;
    }

    public String getSell() {
        return sell;
    }
    private String getSell(String string) {
        Pattern pattern = Pattern.compile("sell:(\\S);");
        Matcher matcher = pattern.matcher(string);

        String sell = "";
        while (matcher.find()) {
            sell += matcher.group();
        }
        return sell;
    }

    public String getIntroduce() {
        return introduce;
    }
    private String getIntroduce(String string) {
        Pattern pattern = Pattern.compile("introduce:(\\S);");
        Matcher matcher = pattern.matcher(string);

        String introduce = "";
        while (matcher.find()) {
            introduce += matcher.group();
        }
        return introduce;
    }
}
