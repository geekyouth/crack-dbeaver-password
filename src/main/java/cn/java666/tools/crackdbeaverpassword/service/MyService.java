package cn.java666.tools.crackdbeaverpassword.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Geek
 * @date 2021-03-25 19:48:10 
 */

@Service
public class MyService {
  
  @Value(value = "${LOCAL_KEY_CACHE}")
  private int[] LOCAL_KEY_CACHE;
  
  @Value("${CREDENTIALS_CONFIG}")
  private String CREDENTIALS_CONFIG;
  
  // https://github.com/dbeaver/dbeaver/blob/2028bc56cef2df1ac1a02c4d47bc86bbfc3dd4dc/plugins/org.jkiss.dbeaver.model/src/org/jkiss/dbeaver/model/impl/app/DefaultSecureStorage.java#L32
  // private final byte[] DEFAULT_LOCAL_KEY_CACHE = new byte[]{-70, -69, 74, -97, 119, 74, -72, 83, -55, 108, 45, 101, 61, -2, 84, 74};
  
  public String decrypt(MultipartFile file) {
    try {
      int[] DEFAULT_LOCAL_KEY_CACHE = new int[]{-70, -69, 74, -97, 119, 74, -72, 83, -55, 108, 45, 101, 61, -2, 84, 74};
      String DEFAULT_CREDENTIALS_CONFIG = "C:/Users/Geek/AppData/Roaming/DBeaverData/workspace6/General/.dbeaver/credentials-config.json";
      
      InputStream byteStream;
      if (file == null) {
        byte[] fileBinary = new FileInputStream(StrUtil.isNotEmpty(CREDENTIALS_CONFIG) ? CREDENTIALS_CONFIG : DEFAULT_CREDENTIALS_CONFIG).readAllBytes();
        byteStream = new ByteArrayInputStream(fileBinary);
      } else {
        byteStream = file.getInputStream();
      }
      
      byte[] fileIv = new byte[16];
      byteStream.read(fileIv);
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      SecretKey aes = new SecretKeySpec(int2Byte(ArrayUtil.isNotEmpty(LOCAL_KEY_CACHE) ? LOCAL_KEY_CACHE : DEFAULT_LOCAL_KEY_CACHE), "AES");
      cipher.init(Cipher.DECRYPT_MODE, aes, new IvParameterSpec(fileIv));
      CipherInputStream cipherIn = new CipherInputStream(byteStream, cipher);
      return inputStreamToString(cipherIn);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (InvalidAlgorithmParameterException e) {
      e.printStackTrace();
    }
    return "=== error ===";
  }
  
  private String inputStreamToString(InputStream is) {
    Scanner s = new Scanner(is).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }
  
  /**
   * int[] --> byte[]
   * https://stackoverflow.com/a/66796122/9633499
   */
  private byte[] int2Byte(int[] data) throws IOException {
    System.out.println(JSONUtil.toJsonStr(data));
    
    byte[] bytes = new byte[data.length];
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) data[i];
    }
    return bytes;
  }
}
