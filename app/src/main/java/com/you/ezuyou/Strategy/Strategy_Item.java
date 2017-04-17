package com.you.ezuyou.Strategy;

import android.graphics.Bitmap;

import com.you.ezuyou.Home.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/4/14.
 */

public class Strategy_Item {

    private Bitmap image;
    private String id, tag, person, program, money, detil;

    public List<Strategy_Item> Data = new ArrayList<>();

    public Strategy_Item(Bitmap image, String id, String tag, String person, String program, String money, String detil) {
        this.image = image;
        this.id = id;
        this.tag = tag;
        this.person = person;
        this.program = program;
        this.money = money;
        this.detil = detil;
    }

    public Strategy_Item(Bitmap[] image, String string) {
        Pattern pattern = Pattern.compile("\\{\\s*" +
                "id:(\\S*?);\\s*" +
                "tag:(\\S*?);\\s*" +
                "person:(\\S*?);\\s*" +
                "program:(\\S*?);\\s*" +
                "money:(\\S*?);\\s*" +
                "detil:(\\S*?);\\s*" +
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

            Strategy_Item strategy_item = new Strategy_Item(
                    image[i],
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(3),
                    matcher.group(4),
                    matcher.group(5),
                    matcher.group(6)
            );
            System.out.println(strategy_item);
            Data.add(strategy_item);
            i++;
        }
        System.out.println(i);
    }

    public Bitmap getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public String getPerson() {
        return person;
    }

    public String getProgram() {
        return program;
    }

    public String getMoney() {
        return money;
    }

    public String getDetil() {
        return detil;
    }

    public static int getSize(String string) {
        Pattern pattern = Pattern.compile("\\{\\s*" +
                "id:(\\S*?);\\s*" +
                "tag:(\\S*?);\\s*" +
                "person:(\\S*?);\\s*" +
                "program:(\\S*?);\\s*" +
                "money:(\\S*?);\\s*" +
                "detil:(\\S*?);\\s*" +
                "\\},\\s*");
        Matcher matcher = pattern.matcher(string);
        int i = 0;
        while (matcher.find()) {
            i++;
        }
        return i;
    }
}
