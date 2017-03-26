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
	 * ����:ֱ������Կ�����ɵ�AES��Կ,Ĭ�ϴ�С��128
	 * 
	 * @exception NoSuchAlgorithmException
	 * @return Key
	 * */
	public static Key getAESKey() {
		return getAESKey(false);
	}//
	/**
	 * ����:ֱ������Կ�����ɵ�AES��Կ,Ĭ�ϴ�С��128
	 * @param is192Size ����true��ѡ��ʹ��192���ȵ���Կ
	 * @exception NoSuchAlgorithmException
	 * @return Key
	 * */
	public static Key getAESKey(boolean is192Size) {
		return getAESKey(false, is192Size);
	}//
	/**
	 * ����:ֱ������Կ�����ɵ�AES��Կ,Ĭ�ϴ�С��128
	 * @param is256SIZE ����trueͬʱ192Ϊfalse��ѡ��ʹ��256���ȵ���Կ
	 * @param is192Size ����trueͬʱ256Ϊfalse��ѡ��ʹ��192���ȵ���Կ
	 * @exception NoSuchAlgorithmException
	 * @return Key
	 * */
	public static Key getAESKey(boolean is256Size , boolean is192Size) {
		SecretKey sk = null;
		try {
			KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME);
			if((is256Size!=false) &&(is192Size!=false)&&  (is256Size == is192Size)){
				throw new Exception("256���Ⱥ�192���ȵĲ�������ͬʱΪtrue!");
			}//ǿ���ж�is256Size��is192Size����ͬʱΪtrue
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
	 * ����:�������ṩ��ָ��,���¹���AES��Կ
	 * 
	 * @exception InvalidKeyException
	 *                ,NoSuchAlgorithmException,InvalidKeySpecException
	 * @return Key (����Ҫǿ��ת����AESKey,ͬʱJDKû��AESKey�����)
	 * ����:������Կ�ı�����ʽ��BigIntegerת���ĳɵ��ַ���������������ο�API�е�BigInteger��
	 * */
	public static Key rebuildAESKey(BigInteger key) {
		byte[] keyByte = key.toByteArray();
		return new SecretKeySpec(keyByte, ALGORITHM_NAME);
	}//

	/**
	 * ����:�ļ�����
	 * 
	 * @param plaintextFile
	 *            �����ļ�
	 * @param aesKey
	 *            AES�ļ�����Կ
	 * @return ����
	 * */
	public static byte[] encryption(File plaintextFile, Key aesKey) {
		return encryption(getFileBytes(plaintextFile), aesKey);
	}

	/**
	 * ����:�ַ�������
	 * 
	 * @param plaintextStr
	 *            �����ַ���
	 * @param aesKey
	 *            AES�ļ�����Կ
	 * @exception UnsupportedEncodingException
	 * @return  cipherStr ��������ļ����ַ��� ����:�õ��������ַ���ϣ���ܾ�������Base64����Ĵ���
	 *         ����ֱ�������ַ����Ļ��ᷢ���ַ���ʧ�����
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
	 * ����:����
	 * 
	 * @param plaintext
	 *            �����ֽ�����
	 * @param aesKey
	 *            AES�ļ�����Կ
	 * @exception NoSuchAlgorithmException
	 *                , NoSuchPaddingException,InvalidKeyException,
	 *                IllegalBlockSizeException,BadPaddingException
	 * @return cipherBytes ��������
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
	 * ����:�ļ�����
	 * 
	 * @param cipherFile
	 *            �����ļ�
	 * @param aesKey
	 *            AES�Ľ�����Կ
	 * @return ����
	 * */
	public static byte[] decryption(File cipherFile, Key aesKey) {
		return decryption(getFileBytes(cipherFile), aesKey);
	}
	/**
	 * ����:�ַ�������
	 * 
	 * @param cipherStr
	 *            �����ַ���
	 * @param aesKey
	 *            AES�Ľ�����Կ
	 * @exception UnsupportedEncodingException
	 * @return  plaintextStr ��������Ľ����ַ���
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
	 * ����:����
	 * 
	 * @param cipherBytes
	 *            �����ֽ�����
	 * @param aesKey
	 *            AES�Ľ�����Կ
	 * @exception NoSuchAlgorithmException
	 *                ,NoSuchPaddingException,InvalidKeyException,
	 *                IllegalBlockSizeException,BadPaddingException
	 * @return plaintextByte �����ֽ�����
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
	 * ����:ȡ��·���ļ����ֽ�����
	 * 
	 * @param f
	 *            �ļ�
	 * @exception FileNotFoundException
	 *                ,IOException
	 * @return fileBytes �ļ�ӳ����ֽ�����
	 * **/
	private static byte[] getFileBytes(File f) {
		FileInputStream fis = null;
		FileChannel fc = null;
		MappedByteBuffer mbb = null;
		byte[] fileBytes = null;
		long start = 0L;// �ļ���ͷ��ʼ
		long fileLength = f.length();// �ļ���С
		long step = fileLength / 200000000L;// ������ص�����ļ�Ϊ190.734M
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
