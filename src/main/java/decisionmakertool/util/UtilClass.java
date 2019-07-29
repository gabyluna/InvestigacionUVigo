package decisionmakertool.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UtilClass {
    private static final int GRADES = 360;
    private static final double CONSTANT_AREA = 2.0;
    private static final  String ALGORITHM = "AES";

    private UtilClass(){}

    private static double calculatePolygonArea(Coordinate []coordinates) {
        double area = 0;
        // Calculate value of shoelace formula
        int j = coordinates.length - 1;
        for (int i = 0; i < coordinates.length; i++) {
            area += (coordinates[j].getCoordinatX() + coordinates[i].getCoordinatX()) *
                    (coordinates[j].getCoordinateY() - coordinates[i].getCoordinateY());
            // j is previous vertex to i
            j = i;
        }

        return Math.abs(area / CONSTANT_AREA);
    }

    public static double getPolygonArea(Integer []listPoints) {
        Coordinate []coordinates = new Coordinate[listPoints.length];
        double interval =  (double)GRADES / listPoints.length;
        double angle  = interval;

        for (int j = 0; j < listPoints.length; j++) {
            double x = listPoints[j] * Math.cos(Math.toRadians(angle));
            double y = format(listPoints[j] * Math.sin(Math.toRadians(angle)));
            Coordinate coordinate = new Coordinate(x,y);
            coordinates[j] = coordinate;
            angle = angle + interval;
        }

        return calculatePolygonArea(coordinates);
    }

    private static double format(double value) {
        return (double) Math.round(value * 1000000) / 1000000; //you can change this to round up the value(for two position use 100...)
    }

    public static String encrypt(String key, String iv, String cleartext) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(cleartext.getBytes());
        return DatatypeConverter.printBase64Binary(encrypted);
    }

    public static String decrypt(String key, String initializationVector, String encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
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

    public static String arrayToJsonString(Object[] object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(object);
        return  mapper.writeValueAsString(object);
    }

}
