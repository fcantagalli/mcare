package criptografia;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import android.util.Log;

public class Criptorgrafia {

	private static final String METODO_ENCRIPTACAO = "RSA";
	// PUBLIC KETY
	private static final BigInteger PUBLIC_KEY_MODULE = new BigInteger("114644347728844391516152512925372656689549946126463004197697148883762088572308364390682178652665759834997204763616629618844502439328504141703631913778048732379068725393484862270975952890219332855708361323006002715573528758609149605844028927117596657512282595164292203101545073769247959373764570783537138484633");
	private static final BigInteger PUBLIC_KEY_EXPONENT = new BigInteger("65537");
		
	//PRIVATE KEY
	private static final BigInteger PRIVATE_KEY_MODULE = new BigInteger("114644347728844391516152512925372656689549946126463004197697148883762088572308364390682178652665759834997204763616629618844502439328504141703631913778048732379068725393484862270975952890219332855708361323006002715573528758609149605844028927117596657512282595164292203101545073769247959373764570783537138484633");
	private static final BigInteger PRIVATE_KEY_EXPONENT = new BigInteger("112165579388337921845914508274331976546591124794067592797295286424871829947303238242985508876038398471398153248441312406126455230018824260586186098104101245637474599813007067082045874559965667873527179026302402509282946839158222989192871091478010197527918102771195831082979320214075666280912949606813035059073");

	// AES stuffs
	private static String algorithm = "AES";
	//private static byte[] keyValue=new byte[] 
	//{ 'A', 'S', 'e', 'c', 'u', 'r', 'e', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };
	
	private static PublicKey getPublicKey(){
		PublicKey pubKey = null;
		try {
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(PUBLIC_KEY_MODULE, PUBLIC_KEY_EXPONENT);
			KeyFactory fact;
			fact = KeyFactory.getInstance(METODO_ENCRIPTACAO);
			pubKey = fact.generatePublic(keySpec);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		  
		return pubKey;
	}
	
	public static ArrayList<String> encriptar(String value) {
		
			Key key = null;
			try {
				key = generateKey();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String dataCripted = AESencrypt(value, key);
			
			Log.i("cript","tipo de cripto: "+ key.getFormat());
		
            byte[] encrypted = {};
            Cipher cipher;
            
			try {
				cipher = Cipher.getInstance(METODO_ENCRIPTACAO+"/ECB/PKCS1Padding");
				cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
				encrypted = cipher.doFinal(key.getEncoded());
				
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String keyEncripted = bytesToHex(encrypted);
			ArrayList<String> result = new ArrayList<String>(2);
			result.add(keyEncripted);
			result.add(dataCripted);
            return result;
    }
	
	private static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	private static String bytesToHex(byte[] bytes) {
		
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}


	private static String AESencrypt(String strToEncrypt, Key key)
	{
	    try
	    {
	    	
            Cipher cipher = Cipher.getInstance(algorithm);
	        cipher.init(Cipher.ENCRYPT_MODE, key);
	        byte[] encriptBytes = cipher.doFinal(strToEncrypt.getBytes());
	        String encryptedString = bytesToHex(encriptBytes);
	        return encryptedString;
	    }
	    catch (Exception e)
	    {
	        System.err.println(e.getMessage());
	    }
	    return null;
	
	}
	
	private static SecretKey generateKey() throws Exception 
    {
		KeyGenerator kg = KeyGenerator.getInstance(algorithm);
	    kg.init(128); // 128 is the keysize. Fixed for AES
	    SecretKey key = kg.generateKey();
        return key;
    }
	
	/*private static void gerarChave() {
        try {
        	KeyPairGenerator kpg = KeyPairGenerator.getInstance(METODO_ENCRIPTACAO);
        	kpg.initialize(1024);
        	KeyPair kp = kpg.genKeyPair();
 
        	KeyFactory fact = KeyFactory.getInstance(METODO_ENCRIPTACAO);
        	
        	RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(),RSAPublicKeySpec.class);
        	RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(),RSAPrivateKeySpec.class);
        	
            System.out.println("Chave RSA:");
            
            System.out.println("public key : \n"+"Modulus : "+pub.getModulus()+"\n Exponent : "+pub.getPublicExponent() );
            System.out.println("\nprivate key : \n"+"Modulus : "+priv.getModulus()+"\n Exponent : "+ priv.getPrivateExponent());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
