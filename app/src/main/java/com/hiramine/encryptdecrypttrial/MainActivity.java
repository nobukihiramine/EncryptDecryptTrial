package com.hiramine.encryptdecrypttrial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		Button buttonEncryptDecrypt = findViewById( R.id.button_encryptdecrypt );
		buttonEncryptDecrypt.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick( View view )
			{
				EditText edittext = findViewById( R.id.edittext_targettext );
				String strTargetText = edittext.getText().toString();

				// 暗号化装置の取得
				Crypter crypter = Crypter.getInstance();

				// 暗号化
				String strEncrypted = crypter.encrypt( strTargetText );
				TextView textviewEncrypted = findViewById( R.id.textview_encrypted );
				textviewEncrypted.setText( strEncrypted );

				// 復号化
				String strDecrypted = crypter.decrypt( strEncrypted );
				TextView textviewDecrypted = findViewById( R.id.textview_decrypted );
				textviewDecrypted.setText( strDecrypted );
			}
		} );
	}
}