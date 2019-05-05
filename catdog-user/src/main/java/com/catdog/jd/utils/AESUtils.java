package com.catdog.jd.utils;

import com.catdog.jd.test.AESTest;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESUtils {

	private static String seed = "catdog-jd";

	public static Key getkey() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(seed.getBytes());
		keyGenerator.init(128,secureRandom);
		//使用上面这种初始化方法可以特定种子来生成密钥，这样加密后的密文是唯一固定的。
		SecretKey secretkey = keyGenerator.generateKey();
		byte[] encodedBytes = secretkey.getEncoded();
		return new SecretKeySpec(encodedBytes,"AES");
	}

	/**
	 * AES加密
	 * @param s 要加密的字符串
	 * @return 加密后转换为base64的字符串
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static String aesEncode(String s) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, getkey());
		byte[] encodeResult = cipher.doFinal(s.getBytes());
		return new BASE64Encoder().encode(encodeResult);
	}

	/**
	 *
	 * @param s 解密字符串
	 * @return 解密后的字符串
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws IOException
	 */
	public static String aesDecode(String s) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, getkey());
		byte[] encodeResult = cipher.doFinal(new BASE64Decoder().decodeBuffer(s));
		return new String(encodeResult,"utf-8");
	}

	public static void main(String[] args) {
		try {
			String root = aesEncode("root");
			String pwd = aesEncode("123456");
			String dRoot = aesDecode(root);
			String dPwd = aesDecode(pwd);
			System.out.println(root + ":" + dRoot + " -- " + pwd  + ":" + dPwd);
			//Jc+3UAKjJOz30gDoHN0TVg==:root -- 5175rzbxjTcfu9zMvLsjXA==:123456
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
