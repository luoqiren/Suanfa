package com.lqr.aes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESServiceECB {
	private static String ALGORITHM_NAME = "AES";
	private static String CIPHER_NAME = "AES/ECB/PKCS5Padding";
	private static int [] KEY_SIZE = { 128,192,256 };

	/**
	 * 描述:直接由密钥对生成的AES密钥,默认大小是128
	 * 
	 * @exception NoSuchAlgorithmException
	 * @return Key
	 * */
	public static Key getAESKey() {
		return getAESKey(false);
	}//
	/**
	 * 描述:直接由密钥对生成的AES密钥,默认大小是128
	 * @param is192Size 输入true则选择使用192长度的密钥
	 * @exception NoSuchAlgorithmException
	 * @return Key
	 * */
	public static Key getAESKey(boolean is192Size) {
		return getAESKey(false, is192Size);
	}//
	/**
	 * 描述:直接由密钥对生成的AES密钥,默认大小是128
	 * @param is256SIZE 输入true同时192为false则选择使用256长度的密钥
	 * @param is192Size 输入true同时256为false则选择使用192长度的密钥
	 * @exception NoSuchAlgorithmException
	 * @return Key
	 * */
	public static Key getAESKey(boolean is256Size , boolean is192Size) {
		SecretKey sk = null;
		try {
			KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME);
			if((is256Size!=false) &&(is192Size!=false)&&  (is256Size == is192Size)){
				throw new Exception("256长度和192长度的参数不能同时为true!");
			}//强制判断is256Size和is192Size不能同时为true
			if(is256Size){
				kg.init(KEY_SIZE[2]);
			}else if(is192Size){
				kg.init(KEY_SIZE[1]);
			}else{
				kg.init(KEY_SIZE[0]);
			}
			sk = kg.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sk;
	}//
	/**
	 * 描述:根据所提供的指数,重新构建AES密钥
	 * 
	 * @exception InvalidKeyException
	 *                ,NoSuchAlgorithmException,InvalidKeySpecException
	 * @return Key (不需要强制转换成AESKey,同时JDK没有AESKey这个类)
	 * 建议:建议密钥的保存形式是BigInteger转化的成的字符串，具体内容请参考API中的BigInteger类
	 * */
	public static Key rebuildAESKey(BigInteger key) {
		byte[] keyByte = key.toByteArray();
		return new SecretKeySpec(keyByte, ALGORITHM_NAME);
	}//

	/**
	 * 描述:文件加密
	 * 
	 * @param plaintextFile
	 *            明文文件
	 * @param aesKey
	 *            AES的加密密钥
	 * @return 密文
	 * */
	public static byte[] encryption(File plaintextFile, Key aesKey) {
		return encryption(getFileBytes(plaintextFile), aesKey);
	}

	/**
	 * 描述:字符串加密
	 * 
	 * @param plaintextStr
	 *            明文字符串
	 * @param aesKey
	 *            AES的加密密钥
	 * @exception UnsupportedEncodingException
	 * @return  cipherStr 经过处理的加密字符串 建议:得到的密文字符串希望能经过类似Base64编码的处理，
	 *         否则直接生成字符串的话会发生字符丢失的情况
	 * */
	public static String encryption(String plaintextStr, Key aesKey) {
		String cipherStr = null;
		try {
			byte[] cipherByte = encryption(plaintextStr.getBytes("GBK"), aesKey);
			BigInteger bi = new BigInteger(cipherByte);
			cipherStr = bi.toString(32);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return cipherStr;
	}

	/**
	 * 描述:加密
	 * 
	 * @param plaintext
	 *            明文字节数组
	 * @param aesKey
	 *            AES的加密密钥
	 * @exception NoSuchAlgorithmException
	 *                , NoSuchPaddingException,InvalidKeyException,
	 *                IllegalBlockSizeException,BadPaddingException
	 * @return cipherBytes 密文数组
	 * **/
	public static byte[] encryption(byte[] plaintext, Key aesKey) {
		byte[] cipherByte = null;
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(CIPHER_NAME);
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			cipherByte = cipher.doFinal(plaintext);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return cipherByte;
	}//
	/**
	 * 描述:文件加密
	 * 
	 * @param cipherFile
	 *            密文文件
	 * @param aesKey
	 *            AES的解密密钥
	 * @return 明文
	 * */
	public static byte[] decryption(File cipherFile, Key aesKey) {
		return decryption(getFileBytes(cipherFile), aesKey);
	}
	/**
	 * 描述:字符串解密
	 * 
	 * @param cipherStr
	 *            明文字符串
	 * @param aesKey
	 *            AES的解密密钥
	 * @exception UnsupportedEncodingException
	 * @return  plaintextStr 经过处理的解密字符串
	 * */
	public static String decryption(String cipherStr, Key aesKey) {
		String plaintextStr = null;
		try {
			byte[] cipherByte = new BigInteger(cipherStr,32).toByteArray();
			cipherByte = decryption(cipherByte, aesKey);
			plaintextStr = new String(cipherByte,"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return plaintextStr;
	}

	/**
	 * 描述:解密
	 * 
	 * @param cipherBytes
	 *            密文字节数组
	 * @param aesKey
	 *            AES的解密密钥
	 * @exception NoSuchAlgorithmException
	 *                ,NoSuchPaddingException,InvalidKeyException,
	 *                IllegalBlockSizeException,BadPaddingException
	 * @return plaintextByte 明文字节数组
	 * **/
	public static byte[] decryption(byte[] cipherBytes, Key aesKey) {
		byte[] plaintextByte = null;
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(CIPHER_NAME);
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			plaintextByte = cipher.doFinal(cipherBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return plaintextByte;
	}//


	/**
	 * 描述:取得路径文件的字节数组
	 * 
	 * @param f
	 *            文件
	 * @exception FileNotFoundException
	 *                ,IOException
	 * @return fileBytes 文件映射的字节数组
	 * **/
	private static byte[] getFileBytes(File f) {
		FileInputStream fis = null;
		FileChannel fc = null;
		MappedByteBuffer mbb = null;
		byte[] fileBytes = null;
		long start = 0L;// 文件从头开始
		long fileLength = f.length();// 文件大小
		long step = fileLength / 200000000L;// 允许加载的最大文件为190.734M
		try {
			fis = new FileInputStream(f);
			fc = fis.getChannel();
			if (step == 0) {
				mbb = fc.map(MapMode.READ_ONLY, start, fileLength);
				fileBytes = new byte[mbb.capacity()];
				mbb.get(fileBytes);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fis!=null){
					fis.close();
				}
				fc.close();
				fc = null;
				System.gc();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}// end try-catch-finally
		return fileBytes;

	}// end
}
