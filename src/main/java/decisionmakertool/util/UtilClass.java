/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UtilClass {
    private static final int GRADES = 360;
    private static final double CONSTANT_AREA = 2.0;
    private static final  String ALGORITHM = "AES";
    private static final  String ENCRYPTION_MODE = "AES/CBC/PKCS5Padding";

    private UtilClass(){}

    private static double polygonArea(double []coordenatesX, double []coordenatesY, int n) {
        double area = 0;
        // Calculate value of shoelace formula
        int j = n - 1;
        for (int i = 0; i < n; i++) {
            area += (coordenatesX[j] + coordenatesX[i]) * (coordenatesY[j] - coordenatesY[i]);
            // j is previous vertex to i
            j = i;
        }

        return Math.abs(area / CONSTANT_AREA);
    }

    public static double getPolygonArea(Integer []dimension, int n) {
        double []coordenatesX = new double[n];
        double []coordenatesY = new double[n];
        double interval =  (double)GRADES / n;
        double angle  = interval;

        for (int j = 0; j < n; j++) {
            coordenatesX[j] = dimension[j] * Math.cos(Math.toRadians(angle));
            coordenatesY[j] = format(dimension[j] * Math.sin(Math.toRadians(angle)));
            angle = angle + interval;
        }

        return polygonArea(coordenatesX, coordenatesY, n);
    }

    private static double format(double value) {
        return (double) Math.round(value * 1000000) / 1000000; //you can change this to round up the value(for two position use 100...)
    }

    public static String encrypt(String key, String iv, String cleartext) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_MODE);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(cleartext.getBytes());
        return DatatypeConverter.printBase64Binary(encrypted);
    }

    public static String decrypt(String key, String initializationVector, String encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_MODE);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector.getBytes());

        byte[] enc = DatatypeConverter.parseBase64Binary(encrypted);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
        byte[] decrypted = cipher.doFinal(enc);
        return new String(decrypted);
    }

    public static void removeRepeatClasses(List<String> listClassesRepeat){
        Set<String> hs = new HashSet<>();
        hs.addAll(listClassesRepeat);
        listClassesRepeat.clear();
        listClassesRepeat.addAll(hs);
    }

    public static String[] cutString(String value) {
        return value.split("#");
    }

}
