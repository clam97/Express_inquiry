package org.Util;

import java.util.UUID;

public class MakeUUid {
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
//去掉“-”符号
        System.out.println(uuid);
        return uuid.replaceAll("-", "");
    }

}
