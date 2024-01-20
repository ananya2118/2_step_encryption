

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESClient {

    public static String decryptAES(String encryptedText, String key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static void main(String args[]) throws Exception {

        Scanner sc=new Scanner(System.in);
        
        Socket socket = new Socket("127.0.01", 4000);
        // try (Socket socket = new Socket("127.0.01", 4000);)
            
        // DataInputStream dis = new DataInputStream(socket.getInputStream());
        
        try{
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
         BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String recke=br.readLine();
            System.out.println("encrypted key received is : "+recke);
            String reckey="";
            int length = recke.length();
int midpoint = length / 2;

String firstHalf = recke.substring(0, midpoint);
String secondHalf = recke.substring(midpoint);
for(int i=0;i<firstHalf.length();i++)
{
    reckey=reckey+firstHalf.charAt(i)+secondHalf.charAt(i);
}
        System.out.println("devrypted key is : "+reckey);
            bw.write("key-received");
            bw.newLine();
            bw.flush();

            String recmes=br.readLine();
            System.out.println("received encrypted text :"+recmes);
            String decmes=decryptAES(recmes, reckey);
            System.out.println("the otp received is : "+decmes);
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
