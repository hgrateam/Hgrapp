package com.Ateam.HangaramAPP;

import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;

public class Main<CustomDialogActivity> extends Activity {

	public ViewPager mPager;
	public String ptitle, ptext;
	String items[] = {"한가람고 15기 그룹", "한가람고 16기 그룹"};

	private OnClickListener mBTN_viewnotice = new OnClickListener() {
		public void onClick(View v) {
			// 노티스 보여주기

			AlertDialog.Builder ab = new AlertDialog.Builder(Main.this);
			ab.setMessage(Html.fromHtml(ptext));
			ab.setPositiveButton("확인", null);
			ab.show();

		}
	};

	private OnClickListener mBTN_toas = new OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("http://afterschool.sen.go.kr/student/stuIndex.do?sch_code=2100H135")));
		}
	};
	private OnClickListener mBTN_toCalc = new OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(Main.this, Calc.class));
		}
	};

	private OnClickListener mBTN_toLib = new OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("http://reading.ssem.or.kr/r/dls/school_code_setting.jsp?school_code=9080&school_name=%ED%95%9C%EA%B0%80%EB%9E%8C%EA%B3%A0%EB%93%B1%ED%95%99%EA%B5%90")));
		}
	};

	private OnClickListener mBTN_toPrincipleWall = new OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://www.facebook.com/sanjinii")));
		}
	};
	private OnClickListener mBTN_toHGR_15th = new OnClickListener() {
		public void onClick(View v) {
			
			AlertDialog.Builder ab = new AlertDialog.Builder(Main.this);
			ab.setIcon(R.drawable.communication_s);
			ab.setTitle("한가람고 커뮤니티");
			ab.setView(createCustomView());
			ab.setNegativeButton("뒤로", null);
			ab.show();

		}
	};
    // AlertDialog에 사용할 ListView로 구성된 커스텀 view 생
    private View createCustomView() {
        LinearLayout linearLayoutView = new LinearLayout(this);
        ListView listview = new ListView(this);
        ArrayAdapter<String> aa = new ArrayAdapter<String> (this,
                                    android.R.layout.simple_list_item_1,
                                    items);
         
        listview.setAdapter(aa);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                	startActivity(new Intent(Intent.ACTION_VIEW,
        					Uri.parse("http://www.facebook.com/groups/hangaram15th/")));
                    
                }
                else if(position == 1){
                	startActivity(new Intent(Intent.ACTION_VIEW,
        					Uri.parse("http://www.facebook.com/groups/222927487789059/")));
                    
                }
            }          
        });
         
        linearLayoutView.setOrientation(LinearLayout.VERTICAL);
        linearLayoutView.addView(listview);
        return linearLayoutView;
    }

	
	
	private OnClickListener mBTN_toHGR = new OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://www.hangaram.hs.kr/")));
		}
	};
	private OnClickListener mBTN_toHGRency = new OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://www.facebook.com/hangarampedia")));
		}
	};
	private OnClickListener mBTN_todayMeal = new OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(Main.this, Loading_Parsing.class));
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager_main);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(new mainPagerAdapter(getApplicationContext()));
		
		//Bind the circle indicator to the adapter
		CirclePageIndicator CPIndicator = (CirclePageIndicator)findViewById(R.id.CPIndicator);
		CPIndicator.setViewPager(mPager);
	}

	private class mainPagerAdapter extends PagerAdapter {

		private LayoutInflater mInflater;

		public mainPagerAdapter(Context con) {
			super();
			mInflater = LayoutInflater.from(con);

		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public Object instantiateItem(View pager, int position) {
			View v = null;
			if (position == 0) {

				v = mInflater.inflate(R.layout.main, null);
				// 공지사항을 파싱해 온다
				v.findViewById(R.id.noticebutton).setOnClickListener(
						mBTN_viewnotice);

				try {
					URL url = new URL("http://bluepeal.raonnet.com/inf.xml");
					Log.i("Net", "파싱 시작");
					XmlPullParserFactory parserCreator = XmlPullParserFactory
							.newInstance();
					XmlPullParser parser = parserCreator.newPullParser();

					parser.setInput(url.openStream(), null);

					int parserEvent = parser.getEventType();
					while (parserEvent != XmlPullParser.END_DOCUMENT) {
						if (parserEvent == XmlPullParser.START_TAG) {
							if (parser.getName().equals("notice")) {
								ptitle = parser
										.getAttributeValue(null, "title");
								ptext = parser.getAttributeValue(null, "text");
								Log.i("Net", "공지사항을 찾음ㅋ" + ptitle);
								TextView nv = (TextView) v
										.findViewById(R.id.noticeview);
								nv.setText(ptitle);

							}
						} else if (parser.getEventType() == XmlPullParser.TEXT) {
							Log.i("Net", "서버가삐짐ㅋ");
						}
						parserEvent = parser.next();
					}
				} catch (Exception e) {
					// 안됨ㅋ
				}

			} else if (position == 1){
				v = mInflater.inflate(R.layout.metro_view_menu, null);
				v.findViewById(R.id.toPrincipleWall).setOnClickListener(
						mBTN_toPrincipleWall);
				v.findViewById(R.id.toHGR).setOnClickListener(mBTN_toHGR);
				v.findViewById(R.id.todayMeal).setOnClickListener(
						mBTN_todayMeal);
				v.findViewById(R.id.toHGRlib).setOnClickListener(mBTN_toLib);
				
				v.findViewById(R.id.toas).setOnClickListener(mBTN_toas);
			}
			
			else if (position == 2){
				v = mInflater.inflate(R.layout.metro_view_menu2, null);
				v.findViewById(R.id.toCalc).setOnClickListener(mBTN_toCalc);
				v.findViewById(R.id.HGR_15th).setOnClickListener(
						mBTN_toHGR_15th);
				v.findViewById(R.id.toHGRency).setOnClickListener(
						mBTN_toHGRency);
			}
			((ViewPager) pager).addView(v, 0);

			return v;
		}

		@Override
		public void destroyItem(View pager, int position, Object view) {
			((ViewPager) pager).removeView((View) view);
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			// TODO Auto-generated method stub
			return view == obj;
		}

	}

	// 여기서부터 옵션메뉴 관련 내용
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		createMenu(menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return applyMenuChoice(item) || super.onOptionsItemSelected(item);
	}

	private void createMenu(Menu menu) {
		menu.add(0, 1, 0, "개발팀 정보");
		menu.add(0, 2, 0, "설정");
		menu.add(0, 3, 0, "도움말");
		menu.add(0, 4, 0, "업데이트 확인");
	}

	private boolean applyMenuChoice(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			startActivity(new Intent(this, Devteam_info.class));
			finish();
			overridePendingTransition(R.anim.bottom_to_top_in,
					R.anim.bottom_to_top_out);
			return true;
			
		case 2: 
			 
		     startActivity(new Intent(this, DBupdate.class)); finish();
			  overridePendingTransition(R.anim.bottom_to_top_in,
			  R.anim.bottom_to_top_out); 
			  return true;
			  
			
		case 3:
			
			Toast toast2 = Toast.makeText(this, "현재 구현중에 있습니다.",
					Toast.LENGTH_SHORT);
			toast2.show();
			return true;
			
		case 4:
			startActivity(new Intent(this, Chk_UPDATE.class));
			return true;
		}
		return false;
	}
}