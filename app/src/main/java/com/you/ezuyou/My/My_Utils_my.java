package com.you.ezuyou.My;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/4/15.
 */

public class My_Utils_my {

    private String id, username, userschool, userschool_class, usernumber, usersex;

    public My_Utils_my(String my) {
        Pattern pattern = Pattern.compile("\\{\\s*" +
                "id:(\\S*?);\\s*" +
                "username:(\\S*?);\\s*" +
                "userschool:(\\S*?);\\s*" +
                "userschool_class:(\\S*?);\\s*" +
                "usernumber:(\\S*?);\\s*" +
                "usersex:(\\S*?);\\s*" +
                "\\},\\s*");
        Matcher matcher = pattern.matcher(my);
        int i = 0;
        while (matcher.find()) {
            id = matcher.group(1);
            username = matcher.group(2);
            userschool = matcher.group(3);
            userschool_class = matcher.group(4);
            usernumber = matcher.group(5);
            usersex = matcher.group(6);
        }
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUserschool() {
        return userschool;
    }

    public String getUserschool_class() {
        return userschool_class;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public String getUsersex() {
        return usersex;
    }
}