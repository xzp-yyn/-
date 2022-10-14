package org.xzp.utils;

import java.util.Random;

/**
 * @Author xuezhanpeng
 * @Date 2022/10/13 9:56
 * @Version 1.0
 */
public  class  LoginCodeUtils {

    private static Random randObj = new Random();

    // 生成6位随机验证码
    private static String generateCode6() {
        return Integer.toString(100000 + randObj.nextInt(900000));
    }
    // 生成4位随机验证码
    private static String generateCode4() {
        return Integer.toString(1000 + randObj.nextInt(9000));
    }

    public static String generateCode(int length){
        if(length==4){
            return generateCode4();
        }else {
            return generateCode6();
        }
    }
}
