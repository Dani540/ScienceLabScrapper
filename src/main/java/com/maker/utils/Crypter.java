package com.maker.utils;

import java.util.stream.Collectors;

public class Crypter {
    public static String crypt(String text) {
        return text.chars()
                .mapToObj(i -> (char) (i*i))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    public static String decrypt(String text){
        return text.chars()
                .mapToObj(i -> (char) Math.sqrt(i))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
