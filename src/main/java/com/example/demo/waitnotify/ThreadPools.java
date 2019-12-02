package com.example.demo.waitnotify;

/**
 * @author zhaoyu
 * @date 2019-07-16
 */
public class ThreadPools {


    public static void main(String[] args) {

        byte b = (byte) 38;

        byte andByte = getAndByte(b, 4, 7);

        System.out.println(andByte);
    }


    private static byte getAndByte(byte b,int startPos,int endPos) {
        int op = b&0xff;
        int left = ((byte)(op << startPos))&0xff;
        return (byte)(left >>> (7+startPos-endPos));
    }



}
