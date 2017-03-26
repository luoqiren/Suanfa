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
 * @version 1.00 描述:本接口实现了RSA的1024长度密钥对字节数组进行加解密的操作， 也只是实现了公钥加密、私钥解密的功能
 *          联系方式:akqq261812410@163.com
 * */
public class RSAService {
	private static final String ALGORITHM_NAME = "RSA";
	private static final String CIPHER_NAME = "RSA";
	private static KeyPair keyPair = null;
	private static final int RSA_SIZE = 1024;// 密钥长度影响加密安全性、效率等，最小是512
	private static final int RSA_ENCRYPTION_SIZE = 117;// 此值受到RSA_SIZE的影响，大小与RSA_SIZE成正比
	private static final int RSA_DECRYPTION_SIZE = 128;// 此值受到RSA_SIZE的影响，大小与RSA_SIZE成正比
	/**
	 * 静态工厂生成密钥对
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
	 * 描述:直接由密钥对生成的RSA公钥
	 * 
	 * @return RSAPublicKey
	 * */
	public static RSAPublicKey getRSAPublicKey() {
		PublicKey pubKey = keyPair.getPublic();
		return (RSAPublicKey) pubKey;
	}

	/**
	 * 描述:直接由密钥对生成的RSA私钥
	 * 
	 * @return RSAPrivateKey
	 * */
	public static RSAPrivateKey getRSAPrivateKey() {
		PrivateKey priKey = keyPair.getPrivate();
		return (RSAPrivateKey) priKey;
	}

	/**
	 * 描述:根据所提供的公钥系数和公钥指数,重新构建RSA公钥,关于指数信息，查看RSAPublicKeySpec
	 * 
	 * @param modulus
	 *            公钥系数
	 * @param publicExponent
	 *            公钥指数
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
	 * 描述:根据所提供的私钥系数和公钥指数,重新构建RSA私钥,关于指数信息，查看RSAPrivateKeySpec
	 * 
	 * @param modulus
	 *            私钥系数
	 * @param privateExponent
	 *            私钥指数
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
	 * 描述:直接使用公钥加密方式---必须连接私钥解密方式
	 * 
	 * @param plaintextFile
	 *            明文文件
	 * @param rsaPub
	 *            公钥
	 * @return 加密过的byte数组
	 * */
	public static byte[] encryptionByPublicKey(File plaintextFile,
			RSAPublicKey rsaPub) {
		return encryptionByPublicKey(getFileBytes(plaintextFile), rsaPub);
	}

	/**
	 * 描述:直接使用公钥加密方式---必须连接私钥解密方式，以GBK的方式进行解码
	 * 
	 * @param plaintextStr
	 *            明文字符串
	 * @param rsaPub
	 *            公钥
	 * @exception UnsupportedEncodingException
	 * @return 加密过的byte数组
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
	 * 描述:直接使用公钥加密方式---必须连接私钥解密方式
	 * 
	 * @param plaintext
	 *            明文数组
	 * @param rsaPub
	 *            公钥
	 * @return 加密过的byte数组
	 * */
	public static byte[] encryptionByPublicKey(byte[] plaintext,
			RSAPublicKey rsaPub) {
		Cipher cipher = null;
		byte[] cipherBytes = null;
		int fileLength = plaintext.length;
		int step = fileLength / RSA_ENCRYPTION_SIZE;
		cipherBytes = new byte[RSA_DECRYPTION_SIZE * (step + 1)];// 初始化加密后的字节数组
		try {
			cipher = Cipher.getInstance(CIPHER_NAME);
			cipher.init(Cipher.ENCRYPT_MODE, rsaPub);
			if (step > 0) {// 当需要进行分步操作的时候
				byte[] temp = null;
				for (int i = 0; i < step; i++) {// 处理分步加密
					// 按照RSA_ENCRYPTION_SIZE的大小进行加密，输出是RSA_DECRYPTION_SIZE的大小
					temp = cipher.doFinal(plaintext, i * RSA_ENCRYPTION_SIZE,
							RSA_ENCRYPTION_SIZE);
					System.arraycopy(temp, 0, cipherBytes, i
							* RSA_DECRYPTION_SIZE, RSA_DECRYPTION_SIZE);
				}
				// 分部加密之后剩下的字节不足RSA_ENCRYPTION_SIZE
				// 剩余的加密大小是: fileLength-(RSA_ENCRYPTION_SIZE*step)
				temp = cipher.doFinal(plaintext, step * RSA_ENCRYPTION_SIZE,
						fileLength - (RSA_ENCRYPTION_SIZE * step));
				System.arraycopy(temp, 0, cipherBytes, step
						* RSA_DECRYPTION_SIZE, RSA_DECRYPTION_SIZE);
			} else {// 不进行分步操作
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
	 * 描述:直接使用私钥解密方式---必须连接公钥加密方式
	 * 
	 * @param File
	 *            cipherFile 密文文件
	 * @param RSAPrivateKey
	 *            rsaPri 私钥
	 * @return 解密后得到的明文
	 * */
	public static byte[] decryptionByPrivateKey(File cipherFile,
			RSAPrivateKey rsaPri) {
		return decryptionByPrivateKey(getFileBytes(cipherFile), rsaPri);
	}

	/**
	 * 描述:直接使用私钥解密方式---必须连接公钥加密方式，以GBK的方式进行解码
	 * 
	 * @param String
	 *            cipherStr 密文字符串
	 * @param RSAPrivateKey
	 *            rsaPri 私钥
	 * @exception UnsupportedEncodingException
	 * @return 解密后得到的明文
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
	 * 描述:直接使用私钥解密方式---必须连接公钥加密方式
	 * 
	 * @param byte [] cipherBytes 密文数组
	 * @param RSAPrivateKey
	 *            rsaPri 私钥
	 * @return 解密后得到的明文
	 * */
	public static byte[] decryptionByPrivateKey(byte[] cipherBytes,
			RSAPrivateKey rsaPri) {
		Cipher cipher = null;
		int fileLength = cipherBytes.length;// step肯定是fileLength的一个因数
		int step = fileLength / RSA_DECRYPTION_SIZE;
		byte[] plaintextBytes = new byte[step * RSA_ENCRYPTION_SIZE];// 必须剔除多余的长度
		try {
			cipher = Cipher.getInstance(CIPHER_NAME);
			cipher.init(Cipher.DECRYPT_MODE, rsaPri);
			if (step > 0) {
				int notZeroLength = 0;// 用来统计plaintext中非零的长度，默认初始化(new)的值是0
				byte[] temp = null;
				for (int i = 0; i < step; i++) {// 处理分步解密,与加密步骤有点不一样
					// 按照RSA_DECRYPTION_SIZE的大小进行解密，输出是temp.length的大小
					temp = cipher.doFinal(cipherBytes, i * RSA_DECRYPTION_SIZE,
							RSA_DECRYPTION_SIZE);
					notZeroLength += temp.length;
					System.arraycopy(temp, 0, plaintextBytes, i
							* RSA_ENCRYPTION_SIZE, temp.length);
				}
				// 剔除byte值为0的长度
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
	/**
	 * 描述:私有构造器，无法使用new
	 * */
	private RSAService(){
		
	}
}
