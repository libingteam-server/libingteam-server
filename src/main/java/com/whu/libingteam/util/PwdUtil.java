package com.whu.libingteam.util;


import cc.eamon.open.security.CodeMethod;
import cc.eamon.open.security.SecurityFactory;

import java.util.UUID;

public class PwdUtil {


    public static String saltPassword(String password, String salt) {
        return SecurityFactory.getCodeMethod(CodeMethod.SUPPORT.CODE_MD5)
                .encode(salt + password);
    }

    public static String salt(){
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,7);
    }

    public static boolean checkPassword(String origin, String salt, String post){
        return origin.equals(SecurityFactory.getCodeMethod(CodeMethod.SUPPORT.CODE_MD5)
                .encode(salt + post));
    }

    public static String token(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }


}
