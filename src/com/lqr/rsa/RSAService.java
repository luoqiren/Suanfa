package com.lqr.rsa;

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
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author qi
 * @version 1.00 ����:���ӿ�ʵ����RSA��1024������Կ���ֽ�������мӽ��ܵĲ����� Ҳֻ��ʵ���˹�Կ���ܡ�˽Կ���ܵĹ���
 *          ��ϵ��ʽ:akqq261812410@163.com
 * */
public class RSAService {
	private static final String ALGORITHM_NAME = "RSA";
	private static final String CIPHER_NAME = "RSA";
	private static KeyPair keyPair = null;
	private static final int RSA_SIZE = 1024;// ��Կ����Ӱ����ܰ�ȫ�ԡ�Ч�ʵȣ���С��512
	private static final int RSA_ENCRYPTION_SIZE = 117;// ��ֵ�ܵ�RSA_SIZE��Ӱ�죬��С��RSA_SIZE������
	private static final int RSA_DECRYPTION_SIZE = 128;// ��ֵ�ܵ�RSA_SIZE��Ӱ�죬��С��RSA_SIZE������
	/**
	 * ��̬����������Կ��
	 * */
	static {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator
					.getInstance(ALGORITHM_NAME);
			keyPairGen.initialize(RSA_SIZE);
			keyPair = keyPairGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����:ֱ������Կ�����ɵ�RSA��Կ
	 * 
	 * @return RSAPublicKey
	 * */
	public static RSAPublicKey getRSAPublicKey() {
		PublicKey pubKey = keyPair.getPublic();
		return (RSAPublicKey) pubKey;
	}

	/**
	 * ����:ֱ������Կ�����ɵ�RSA˽Կ
	 * 
	 * @return RSAPrivateKey
	 * */
	public static RSAPrivateKey getRSAPrivateKey() {
		PrivateKey priKey = keyPair.getPrivate();
		return (RSAPrivateKey) priKey;
	}

	/**
	 * ����:�������ṩ�Ĺ�Կϵ���͹�Կָ��,���¹���RSA��Կ,����ָ����Ϣ���鿴RSAPublicKeySpec
	 * 
	 * @param modulus
	 *            ��Կϵ��
	 * @param publicExponent
	 *            ��Կָ��
	 * @return RSAPublicKey
	 * */
	public static RSAPublicKey rebuildRSAPublicKey(BigInteger modulus,
			BigInteger publicExponent) {
		RSAPublicKey rpk = null;
		KeyFactory kf = null;
		try {
			kf = KeyFactory.getInstance(ALGORITHM_NAME);
			rpk = (RSAPublicKey) kf.generatePublic(new RSAPublicKeySpec(
					modulus, publicExponent));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return rpk;
	}

	/**
	 * ����:�������ṩ��˽Կϵ���͹�Կָ��,���¹���RSA˽Կ,����ָ����Ϣ���鿴RSAPrivateKeySpec
	 * 
	 * @param modulus
	 *            ˽Կϵ��
	 * @param privateExponent
	 *            ˽Կָ��
	 * @return RSAPrivateKey
	 * */
	public static RSAPrivateKey rebuildRSAPrivateKey(BigInteger modulus,
			BigInteger privateExponent) {
		RSAPrivateKey rpk = null;
		KeyFactory kf = null;
		try {
			kf = KeyFactory.getInstance(ALGORITHM_NAME);
			rpk = (RSAPrivateKey) kf.generatePrivate(new RSAPrivateKeySpec(
					modulus, privateExponent));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return rpk;
	}

	/**
	 * ����:ֱ��ʹ�ù�Կ���ܷ�ʽ---��������˽Կ���ܷ�ʽ
	 * 
	 * @param plaintextFile
	 *            �����ļ�
	 * @param rsaPub
	 *            ��Կ
	 * @return ���ܹ���byte����
	 * */
	public static byte[] encryptionByPublicKey(File plaintextFile,
			RSAPublicKey rsaPub) {
		return encryptionByPublicKey(getFileBytes(plaintextFile), rsaPub);
	}

	/**
	 * ����:ֱ��ʹ�ù�Կ���ܷ�ʽ---��������˽Կ���ܷ�ʽ����GBK�ķ�ʽ���н���
	 * 
	 * @param plaintextStr
	 *            �����ַ���
	 * @param rsaPub
	 *            ��Կ
	 * @exception UnsupportedEncodingException
	 * @return ���ܹ���byte����
	 * */
	public static byte[] encryptionByPublicKey(String plaintextStr,
			RSAPublicKey rsaPub) {
		try {
			return encryptionByPublicKey(plaintextStr.getBytes("GBK"), rsaPub);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����:ֱ��ʹ�ù�Կ���ܷ�ʽ---��������˽Կ���ܷ�ʽ
	 * 
	 * @param plaintext
	 *            ��������
	 * @param rsaPub
	 *            ��Կ
	 * @return ���ܹ���byte����
	 * */
	public static byte[] encryptionByPublicKey(byte[] plaintext,
			RSAPublicKey rsaPub) {
		Cipher cipher = null;
		byte[] cipherBytes = null;
		int fileLength = plaintext.length;
		int step = fileLength / RSA_ENCRYPTION_SIZE;
		cipherBytes = new byte[RSA_DECRYPTION_SIZE * (step + 1)];// ��ʼ�����ܺ���ֽ�����
		try {
			cipher = Cipher.getInstance(CIPHER_NAME);
			cipher.init(Cipher.ENCRYPT_MODE, rsaPub);
			if (step > 0) {// ����Ҫ���зֲ�������ʱ��
				byte[] temp = null;
				for (int i = 0; i < step; i++) {// ����ֲ�����
					// ����RSA_ENCRYPTION_SIZE�Ĵ�С���м��ܣ������RSA_DECRYPTION_SIZE�Ĵ�С
					temp = cipher.doFinal(plaintext, i * RSA_ENCRYPTION_SIZE,
							RSA_ENCRYPTION_SIZE);
					System.arraycopy(temp, 0, cipherBytes, i
							* RSA_DECRYPTION_SIZE, RSA_DECRYPTION_SIZE);
				}
				// �ֲ�����֮��ʣ�µ��ֽڲ���RSA_ENCRYPTION_SIZE
				// ʣ��ļ��ܴ�С��: fileLength-(RSA_ENCRYPTION_SIZE*step)
				temp = cipher.doFinal(plaintext, step * RSA_ENCRYPTION_SIZE,
						fileLength - (RSA_ENCRYPTION_SIZE * step));
				System.arraycopy(temp, 0, cipherBytes, step
						* RSA_DECRYPTION_SIZE, RSA_DECRYPTION_SIZE);
			} else {// �����зֲ�����
				cipherBytes = cipher.doFinal(plaintext);
			}
			System.gc();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} finally {
			cipher = null;
			System.gc();
		}
		return cipherBytes;
	}// end

	/**
	 * ����:ֱ��ʹ��˽Կ���ܷ�ʽ---�������ӹ�Կ���ܷ�ʽ
	 * 
	 * @param File
	 *            cipherFile �����ļ�
	 * @param RSAPrivateKey
	 *            rsaPri ˽Կ
	 * @return ���ܺ�õ�������
	 * */
	public static byte[] decryptionByPrivateKey(File cipherFile,
			RSAPrivateKey rsaPri) {
		return decryptionByPrivateKey(getFileBytes(cipherFile), rsaPri);
	}

	/**
	 * ����:ֱ��ʹ��˽Կ���ܷ�ʽ---�������ӹ�Կ���ܷ�ʽ����GBK�ķ�ʽ���н���
	 * 
	 * @param String
	 *            cipherStr �����ַ���
	 * @param RSAPrivateKey
	 *            rsaPri ˽Կ
	 * @exception UnsupportedEncodingException
	 * @return ���ܺ�õ�������
	 * */
	public static byte[] decryptionByPrivateKey(String cipherStr,
			RSAPrivateKey rsaPri) {
		try {
			return decryptionByPrivateKey(cipherStr.getBytes("GBK"), rsaPri);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����:ֱ��ʹ��˽Կ���ܷ�ʽ---�������ӹ�Կ���ܷ�ʽ
	 * 
	 * @param byte [] cipherBytes ��������
	 * @param RSAPrivateKey
	 *            rsaPri ˽Կ
	 * @return ���ܺ�õ�������
	 * */
	public static byte[] decryptionByPrivateKey(byte[] cipherBytes,
			RSAPrivateKey rsaPri) {
		Cipher cipher = null;
		int fileLength = cipherBytes.length;// step�϶���fileLength��һ������
		int step = fileLength / RSA_DECRYPTION_SIZE;
		byte[] plaintextBytes = new byte[step * RSA_ENCRYPTION_SIZE];// �����޳�����ĳ���
		try {
			cipher = Cipher.getInstance(CIPHER_NAME);
			cipher.init(Cipher.DECRYPT_MODE, rsaPri);
			if (step > 0) {
				int notZeroLength = 0;// ����ͳ��plaintext�з���ĳ��ȣ�Ĭ�ϳ�ʼ��(new)��ֵ��0
				byte[] temp = null;
				for (int i = 0; i < step; i++) {// ����ֲ�����,����ܲ����е㲻һ��
					// ����RSA_DECRYPTION_SIZE�Ĵ�С���н��ܣ������temp.length�Ĵ�С
					temp = cipher.doFinal(cipherBytes, i * RSA_DECRYPTION_SIZE,
							RSA_DECRYPTION_SIZE);
					notZeroLength += temp.length;
					System.arraycopy(temp, 0, plaintextBytes, i
							* RSA_ENCRYPTION_SIZE, temp.length);
				}
				// �޳�byteֵΪ0�ĳ���
				temp = plaintextBytes;
				plaintextBytes = null;
				plaintextBytes = new byte[notZeroLength];
				System.arraycopy(temp, 0, plaintextBytes, 0, notZeroLength);
			} else {
				plaintextBytes = cipher.doFinal(cipherBytes);
			}
			System.gc();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} finally {
			cipher = null;
			System.gc();
		}
		return plaintextBytes;
	}// end

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
	/**
	 * ����:˽�й��������޷�ʹ��new
	 * */
	private RSAService(){
		
	}
}
