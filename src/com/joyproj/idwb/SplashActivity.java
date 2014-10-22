package com.joyproj.idwb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.joyproj.idwb.data.UrlData;
import com.joyproj.idwb.data.UserData;
import com.joyproj.idwb.helper.HttpHelper;

public class SplashActivity extends AbstractActivity {

	SharedPreferences sharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_splash);
		// ������
		sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
		// ��ȡ��������
		getConfig();
		// 
		next();
	}

	/**
	 * 
	 */
	private void getConfig(){
		// id������
		UserData.id = sharedPreferences.getString("id", "");
		UserData.password = sharedPreferences.getString("password", "");
		// ����ÿҳ��ʾ��Ŀ��
		UserData.weiboListrows = sharedPreferences.getInt("weiboListrows", UserData.DEFAULT_LISTROWS);
		UserData.perListrows = sharedPreferences.getInt("perListrows", UserData.DEFAULT_LISTROWS);		
	}
	
	/**
	 * 
	 */
	private void next() {
		new Thread(){
			@Override
			public void run() {
				// ��˯һ��
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// û�е�¼��Ϣ ��ת����¼ҳ��
				if("".equals(UserData.id) || "".equals(UserData.password)){
					Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
					startActivity(intent);
				}
				else{
					// ���������¼��Ϣ
					String args = "id=" + UserData.id + "&password=" + UserData.password;
					HttpHelper httpUtil = new HttpHelper(UrlData.SPLASH, SplashActivity.this);
					String res = httpUtil.post(args);
					// ��¼�ɹ�, ��ת��������
					if("1".equals(res)){
						Intent intent = new Intent(SplashActivity.this, MainActivity.class);
						startActivity(intent);
					}					
				}
				//
				finish();
			};
		}.start();
	}
	

}
