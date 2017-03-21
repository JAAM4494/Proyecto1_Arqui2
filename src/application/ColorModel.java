/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

/**
 *
 * @author jaam
 */
public class ColorModel {

    private int red;
    private int green;
    private int blue;
    private int alpha;

    /*
    int i = 234;
    byte b = (byte) i;
    System.out.println (b); // -22
    int i2 = b & 0xFF;
    System.out.println (i2); // 234
    */

    public byte getRedByte() {
        return (byte) red;
    }

    public byte getGreenByte() {
        return (byte) green;
    }

    public byte getBlueByte() {
        return (byte) blue;
    }

    public byte getAlphaByte() {
        return (byte) alpha;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

}
