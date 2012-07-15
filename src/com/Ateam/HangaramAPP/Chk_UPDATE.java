package com.Ateam.HangaramAPP;

import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Chk_UPDATE extends Activity {
	
	// BLUR ȿ��
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);


		new Handler();
		
			
			Toast t = Toast.makeText(this, "��ø� ��ٷ� �ֽʽÿ�...",
					Toast.LENGTH_SHORT);
			t.show();
			Log.i("Net", "���Ÿ�ÿ���");
		

		Try();
		// �ֽ� ������ �ִ°� �˷���

	}

	public void Try(){
	try {
		URL url = new URL("http://bluepeal.raonnet.com/inf.xml");
		Log.i("Net", "�Ľ� ����");
		XmlPullParserFactory parserCreator = XmlPullParserFactory
				.newInstance();
		XmlPullParser parser = parserCreator.newPullParser();

		parser.setInput(url.openStream(), null);

		int parserEvent = parser.getEventType();
		while (parserEvent != XmlPullParser.END_DOCUMENT) {

			if (parserEvent == XmlPullParser.START_TAG) {
		
				if (parser.getName().equals("inf")) {
					String pver = parser.getAttributeValue(null, "version");
					String pmessage = parser.getAttributeValue(null,
							"message");
					Log.i("Net", "ã����");

					if (pver.equals(getString(R.string.app_ver)) == true) {
						Toast toast = Toast.makeText(this, "�ֽ� ������ ���� �Դϴ�",
								Toast.LENGTH_SHORT);
						toast.show();
						Log.i("Net", "�ֽŹ�����");
					} else {
						Toast toast = Toast.makeText(this, pmessage,
								Toast.LENGTH_SHORT);
						toast.show();
						Log.i("Net", "�ֽŹ����ƴԤ�");
					}
				}
			} else if(parser.getEventType() == XmlPullParser.TEXT) {
				Toast toast = Toast.makeText(this, "������ �������� �ʽ��ϴ�",
						Toast.LENGTH_SHORT);
				toast.show();
				Log.i("Net", "������������");
			}
			
			parserEvent = parser.next();
		}
		
	} catch (Exception e) {
		Toast toast = Toast.makeText(this, "��Ʈ��ũ ���¸� Ȯ���Ͻñ� �ٶ��ϴ�.",
				Toast.LENGTH_SHORT);
		toast.show();
		Log.i("Net", "��Ʈ��ũ�� �����Ͻÿ� ��");
		}
		
		// �ȵʤ�
	
	finish();
	}
}