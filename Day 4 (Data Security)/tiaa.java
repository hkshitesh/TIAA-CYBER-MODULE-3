package pkg;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

class tiaa {
	
	public static void encryptEcb(String filenamePlain,	String filenameEnc,	byte[] key)	throws IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException,
			BadPaddingException
	{
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		try (
			FileInputStream fis = new FileInputStream(filenamePlain);
			BufferedInputStream inputstream = new BufferedInputStream(fis);
			FileOutputStream outputstream = new FileOutputStream(filenameEnc);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputstream)) {
			byte[] ibufffer = new byte[1024];
			int length;
			while ((length = inputstream.read(ibufffer))!= -1) {
				byte[] obuffer 	= cipher.update(ibufffer, 0, length);
				if (obuffer != null)
					bufferedOutputStream.write(obuffer);
			}
			byte[] obuffer = cipher.doFinal();
			if (obuffer != null)
				bufferedOutputStream.write(obuffer);
		}
	}

	public static void decryptEcb(String filenameEnc,String filenameDec, byte[] key)throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException,
			BadPaddingException	{

		try (FileInputStream inputStream = new FileInputStream(filenameEnc);
			FileOutputStream outputStream = new FileOutputStream(filenameDec)) {
			byte[] ibuffer = new byte[1024];
			int length;
			Cipher cipher = Cipher.getInstance( "AES/ECB/PKCS5PADDING");
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			while ((length = inputStream.read(ibuffer))	!= -1) {
				byte[] obuffer 	= cipher.update(ibuffer, 0, length);
				if (obuffer != null)
					outputStream.write(obuffer);
			}

			byte[] obuffer = cipher.doFinal();
			if (obuffer != null)
				outputStream.write(obuffer);
		}
	}
	public static void main(String[] args)
		throws IOException, NoSuchPaddingException,
			NoSuchAlgorithmException, BadPaddingException,IllegalBlockSizeException,
			InvalidKeyException
	{
		System.out.println("/****AES Encryption*******/");
		String pFileName = "C:\\Users\\hksharma\\eclipse-workspace\\AES_Encryption\\src\\pkg\\lor.pdf";
		String cFileName = "LOR-ENC.enc";
		String decFileName = "LOR-DEC.pdf";
		byte[] cipher_key = "12345678901234561234567890123456".getBytes("UTF-8");
		//encryptEcb(pFileName, cFileName, cipher_key);
		decryptEcb(cFileName, decFileName, cipher_key);
		System.out.println( "file of encryption: " + pFileName + "\n"
			+ "created encrypted file : " + cFileName+ "\n"
			+ "created decrypted file : " + decFileName);
	}
}
