package decisionmakertool.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.context.FacesContext;

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

    public static String readText(String path) throws IOException {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(path)));
        return data;
    }


    public static void pushChangesFile(String pathFile, String owlFile, String message){
        PathOntology pathOntology = new PathOntology();
        String repo = pathOntology.getPathRepo();
        Path repoPath = Paths.get(repo);
        String path = pathFile;
        String data= "";
        try {
            data = readText(path);
            Files.write(repoPath.resolve(owlFile),data.getBytes());
            Git git = Git.init().setDirectory(repoPath.toFile()).call();
            git.add().addFilepattern(".").call();
            git.commit()
                    .setAll(true)
                    .setMessage(message)
                    .call();

            git.push()
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider("gabyluna", "garomoJ91."))
                    .call();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteFile(String pathOntology){
        String path =   FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources");
        File file = new File( pathOntology);
        return file.delete();
    }

    public static boolean  renameFile(String oldFile, String newFile){
        String path =   FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources");
        File oldFileAux = new File(oldFile);
        File newFileAux = new File(newFile);
        return oldFileAux.renameTo(newFileAux);
    }

}
