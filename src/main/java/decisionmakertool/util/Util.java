package decisionmakertool.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Util {
    private static final  String ALGORITHM = "AES";

    private Util(){}

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

    public static void loadFile(String path, InputStream in) throws IOException {
        int read;
        byte[] bytes = new byte[1024];

        try(OutputStream out = new FileOutputStream(new File(path))){
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
        }
    }

    public static String getWord(String word){
        String []arrayAux = word.split("#");
        String []result = arrayAux[arrayAux.length-1].split("_");
        return result[0];
    }
}
