package com.hiramine.encryptdecrypttrial;

import android.util.Base64;
import android.util.Log;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

// 暗号化装置
// （シングルトンクラス）
public class Crypter
{
	private static final String SECRET_KEY     = "MySecretKey12345";    // 鍵(128bit = 16byte = 16文字)
	private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";    // アルゴリズム/ブロックモード/パディング方式
	private static final String ALGORITHM      = "AES";    // アルゴリズム

	private static final String LOGTAG = "Crypter";

	private static Crypter m_instance;
	private        Cipher  m_encrypter;
	private        Cipher  m_decrypter;

	// インスタンスの湯徳
	public static Crypter getInstance()
	{
		if( null == m_instance )
		{
			m_instance = new Crypter();
		}
		return m_instance;
	}

	// コンストラクタ
	private Crypter()
	{
		// Cipherオブジェクトのインスタンス取得
		try
		{
			m_encrypter = Cipher.getInstance( TRANSFORMATION );
			m_decrypter = Cipher.getInstance( TRANSFORMATION );
		}
		catch( NoSuchAlgorithmException e )
		{
			Log.e( LOGTAG, "No such algorithm." );
		}
		catch( NoSuchPaddingException e )
		{
			Log.e( LOGTAG, "No such padding." );
		}

		// Cipherインスタンスの初期化
		try
		{
			SecretKeySpec secretkeyspec = new SecretKeySpec( SECRET_KEY.getBytes(), ALGORITHM );
			m_encrypter.init( Cipher.ENCRYPT_MODE, secretkeyspec );
			m_decrypter.init( Cipher.DECRYPT_MODE, secretkeyspec );
		}
		catch( InvalidKeyException e )
		{
			Log.e( LOGTAG, "Invalid key." );
		}
	}

	// 暗号化
	public String encrypt( String strTargetText )
	{
		try
		{
			// 文字列をバイト列化
			byte[] abtTargetText = strTargetText.getBytes();
			// 暗号化
			byte[] abtEncrypted = m_encrypter.doFinal( abtTargetText );
			// バイト列をBase64で文字列化
			return Base64.encodeToString( abtEncrypted, Base64.DEFAULT );
		}
		catch( BadPaddingException e )
		{
			Log.e( LOGTAG, "Bad padding." );
		}
		catch( IllegalBlockSizeException e )
		{
			Log.e( LOGTAG, "Illegal block size." );
		}
		return "";
	}

	// 復号化
	public String decrypt( String strCipherText )
	{
		try
		{
			// Base64で文字列化された文字列をバイト列に戻す
			byte[] abtCipherText = Base64.decode( strCipherText, Base64.DEFAULT );
			// 復号化
			byte[] abtDecrypted = m_decrypter.doFinal( abtCipherText );
			// 復号化したバイト列を文字列に戻す
			return new String( abtDecrypted );
		}
		catch( BadPaddingException e )
		{
			Log.e( LOGTAG, "Bad padding." );
		}
		catch( IllegalBlockSizeException e )
		{
			Log.e( LOGTAG, "Illegal block size." );
		}
		return "";
	}
}
