package org.Util;


import org.apache.log4j.Logger;

public class Codecase {
    private static Logger logger = Logger.getLogger(Codecase.class);
    String codes;
    public String code(String code){
        switch (code){
            case "中通":
                codes="ZTO";
                break;
            case "圆通":
                codes="YTO";
                break;
            case "邮政":
                codes="YZPY";
                break;
            case "韵达":
                codes="YD";
                break;
            case "EMS":
                codes="EMS";
                break;
            case "京东":
                codes="JD";
                break;
            case "德邦":
                codes="DBL";
                break;
            case "宅急送":
                codes="ZJS";
                break;
        }

    logger.info("快递编号："+codes);
        return codes;
    }
}
