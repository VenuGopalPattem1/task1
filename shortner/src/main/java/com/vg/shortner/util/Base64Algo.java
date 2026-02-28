package com.vg.shortner.util;

import org.springframework.stereotype.Component;

@Component
public class Base64Algo {
    
    private static final String ALPHABET =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String encode(long urlLen){
        StringBuilder sb=new StringBuilder();
        while(urlLen>0){
            long x=(urlLen%62);
            sb.append(ALPHABET.charAt((int)(x)));
            urlLen=urlLen/62;
        }
        return sb.reverse().toString();
    }
}
