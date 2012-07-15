package com.Ateam.HangaramAPP;

import java.net.URL;
import java.util.Calendar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.Ateam.HangaramAPP.R.string;

import android.app.Activity;
import android.app.ProgressDialog;
//import android.content.Intent;
//import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;

public class Parsing extends Activity {

	private ProgressDialog pd;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parseview);
		
		TT();
	}

	public void TT() {


		Y = calendar.get(Calendar.YEAR);
		M = calendar.get(Calendar.MONTH);
		D = calendar.get(Calendar.DAY_OF_MONTH);

		Log.i("Net", "�Ͼ��! " + Y + (M + 1) + D);
		dd = Integer.toString(Y * 10000 + (M + 1) * 100 + D);
		TextView noticedate = (TextView) findViewById(R.id.dateview);
		noticedate.setText(M + 1 + "�� " + D + "�� " + YI[getYI(Y, M + 1, D)]
				+ "����");

		Button bmod = (Button) findViewById(R.id.mod);
		bmod.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (D == 1) {
					if ((M + 1) == 1) {
						M = 11;
						D = 31;
					} else {
						M--;
						if ((M + 1) == 2 || (M + 1) == 4 || (M + 1) == 6
								|| (M + 1) == 9 || (M + 1) == 11)
							D = 30;
						else
							D = 31;
					}
				} else
					D--;
				TextView noticedate = (TextView) findViewById(R.id.dateview);
				noticedate.setText(M + 1 + "�� " + D + "�� "
						+ YI[getYI(Y, M + 1, D)] + "����");
				dd = Integer.toString(Y * 10000 + (M + 1) * 100 + D);
				Log.i("Net", "-1�� ��ư�� ����");
				update(dd);
			}
		});

		Button bpod = (Button) findViewById(R.id.pod);
		bpod.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if ((M + 1) == 2 || (M + 1) == 4 || (M + 1) == 6
						|| (M + 1) == 9 || (M + 1) == 11) {
					if (D == 30) {
						D = 1;
						if ((M + 1) == 12)
							M = 1;
						else
							M++;
					} else
						D++;
				} else {
					if (D == 31) {
						D = 1;
						if ((M + 1) == 12)
							M = 1;
						else
							M++;
					} else
						D++;
				}
				TextView noticedate = (TextView) findViewById(R.id.dateview);
				noticedate.setText(M + 1 + "�� " + D + "�� "
						+ YI[getYI(Y, M + 1, D)] + "����");
				dd = Integer.toString(Y * 10000 + (M + 1) * 100 + D);
				Log.i("Net", "+1�� ��ư�� ����");
				update(dd);
			}

		});

		update(dd);

	}

	public int getYI(int Y, int M, int D) {

		int a, b, c, d;
		if (M < 3) {
			a = ((Y - 1) / 100);
			b = (Y - 1) % (a * 100);
			c = 12 + M;
			d = D;
		} else {
			a = ((Y) / 100);
			b = (Y) % (a * 100);
			c = M;
			d = D;
		}
		int e = (((21 * a) / 4) + (5 * b / 4) + (26 * (c + 1) / 10) + d - 1) % 7;
		return e;

	}

	public void update(String dd2) {
		Log.i("Net", "update �� ����Ǿ����ϴ�.");
		Mlunch = (TextView) findViewById(R.id.TV1);
		Mdinner = (TextView) findViewById(R.id.TV2);
  //
		Log.i("Net", dd2);
		
		
		Mlunch.setText("���� ������ �ش� ��¥ �޽������� ���ε���� �ʾҽ��ϴ�.");
		Mdinner.setText("���� ���ѳ��� ���ε� �ϵ��� �ϰڽ��ϴ�.");
		Log.i("Net", dd + "���� �޽������� �����ϴ�");

		if (getYI(Y, M + 1, D) == 0 || getYI(Y, M + 1, D) == 6) {
			Mlunch.setText("���� �Դϴ�");
			Mdinner.setText("���� �Դϴ�");
		} else {
			try {
				URL url = new URL("http://bluepeal.raonnet.com/sch.xml");

				XmlPullParserFactory parserCreator = XmlPullParserFactory
						.newInstance();
				XmlPullParser parser = parserCreator.newPullParser();

				parser.setInput(url.openStream(), null);

				int parserEvent = parser.getEventType();
				while (parserEvent != XmlPullParser.END_DOCUMENT) {
					if (parserEvent == XmlPullParser.START_TAG) {
						if (parser.getName().equals("inf")) {
							String pdate = parser.getAttributeValue(null,
									"date");
							String plunch = parser.getAttributeValue(null,
									"lunch");
							String pdinner = parser.getAttributeValue(null,
									"dinner");
							if (dd2.equals(pdate) == true) {
								if (plunch.equals("NULL") != true) {
									Mlunch.setText(plunch);
									Mdinner.setText(pdinner);
									Log.i("Net", dd + "���� �޽������� �����մϴ�");
									Log.i("Net", plunch + pdinner);
								} else if (plunch.equals("NULL") != false) {
									Mlunch.setText("���� ������ �ش� ��¥ �޽������� ���ε���� �ʾҽ��ϴ�.");
									Mdinner.setText("���� ���ѳ��� ���ε� �ϵ��� �ϰڽ��ϴ�.");
									Log.i("Net", dd + "���� �޽������� �����ϴ�");
								}
							}
						}
					} else if (parser.getEventType() == XmlPullParser.TEXT) {

					}
					parserEvent = parser.next();
				}
			} catch (Exception e) {
				Mlunch.setText("������ �ҷ��� �� �����ϴ�.");
				Mdinner.setText("���� �����̰ų� ����� ��Ʈ��ũ ���� ���¸� Ȯ���Ͻʽÿ�.");
			}
		}
		TTcheck = true;

	}

	private int Y, D, M;
	private TextView Mlunch, Mdinner;
	private String dd;
	private String[] YI = { "��", "��", "ȭ", "��", "��", "��", "��" };
	private boolean TTcheck;
	Calendar calendar = Calendar.getInstance();

}

// http://kkoseul.tistory.com/30
// http://devsw.tistory.com/60