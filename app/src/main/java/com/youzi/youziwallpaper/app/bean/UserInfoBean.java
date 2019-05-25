package com.youzi.youziwallpaper.app.bean;

import java.io.Serializable;

/**
 * Created by zzw on 2019/4/27.
 * 描述:
 */
public class UserInfoBean implements Serializable {
    public String nikeName;
    public String userId;
    public String headerUrl;
    public String token;
    public LoginType loginType;

    public enum LoginType {

        QQ(0),
        WX(1),
        WB(2),
        UNKNOW(-1);

        int type;

        LoginType(int type) {
            this.type = type;
        }

        public static LoginType  parserType(int type) {
            switch (type) {
                case 0:
                    return QQ;
                case 1:
                    return WX;
                case 2:
                    return WB;
                default:
                    return UNKNOW;
            }
        }

    }
}
