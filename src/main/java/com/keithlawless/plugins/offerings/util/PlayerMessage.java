package com.keithlawless.plugins.offerings.util;

public class PlayerMessage {
    public static String format(String message) {
        return "\u00A74Offerings: \u00A7e" + message + "\u00A7r";
    }

    public static String unformat(String message) {
        int i;
        StringBuffer sb = new StringBuffer();
        while((i = message.indexOf("\u00A7")) > -1) {
            if(i > 0) {
                sb.append(message.substring(0,i));
            }
            if( message.length() > i + 2 ) {
                sb.append(message.substring(i+2));
            }
            message = new String(sb);
            sb = new StringBuffer();
        }

        if(message.startsWith("Offerings:")) {
            if(message.length() > 10) {
                message = message.substring(10);
            }
            else {
                message = new String("");
            }
        }
        return message.trim();
    }
}
