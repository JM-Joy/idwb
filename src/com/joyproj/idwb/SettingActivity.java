package com.joyproj.idwb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.joyproj.idwb.data.UserData;
import com.joyproj.idwb.helper.PoolHelper;

public class SettingActivity extends AbstractPowerfulActivity {

	Spinner spinnerWeibo;
	Spinner spinnerUser;
	
	Activity actMain;
	
	private ArrayAdapter<String> adapter;
	
	@Override
	protected CharSequence returnTitleText() {
		return "����";
	}

	@Override
	protected CharSequence returnRightText() {
		return "ȷ�� > ";
	}

	@Override
	protected void onRightClick(View v) {
		int weiboListrows = spinnerWeibo.getSelectedItemPosition() * 5 + 10;
		int userListrows = spinnerUser.getSelectedItemPosition() * 5 + 10;
		// ���µ�sharedPreferences
		SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putInt("weiboListrows", weiboListrows);
		editor.putInt("perListrows", userListrows);
		// �ɹ���ʾ
		if(editor.commit()){
			Toast.makeText(this, "���óɹ�", Toast.LENGTH_SHORT).show();
			UserData.weiboListrows = weiboListrows;
			UserData.perListrows = userListrows;
			finish();
		}
		// ʧ����ʾ
		else{
			Toast.makeText(this, "����ʧ��", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void doSthOnCreateView() {
		// ��ȡ MainActivity ����
		actMain = (Activity) getIntent().getSerializableExtra("main");
		// ��ʼ��
		spinnerWeibo = (Spinner) findViewById(R.id.spinnerWeibo);
		spinnerUser = (Spinner) findViewById(R.id.spinnerUser);
		
		String[] items = {"10", "15", "20", "25"}; 
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerWeibo.setAdapter(adapter);	
		spinnerUser.setAdapter(adapter);
		
		spinnerWeibo.setSelection((UserData.weiboListrows - 10) / 5);
		spinnerUser.setSelection((UserData.perListrows - 10) / 5);
	}

	@Override
	protected int returnChildResource() {
		return R.layout.act_child_setting;
	}

	@Override
	protected void uiWait() {
	}

	@Override
	protected void uiReset() {
	}

	/**
	 * ע��
	 * @param v
	 */
	public void logout(View v){
		// �����¼��Ϣ
		SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("id", null);
		editor.putString("password", null);
		editor.commit();
		UserData.id = "";
		UserData.password = "";
		// ��������ɾ��
		PoolHelper poolHelper = PoolHelper.getInstance();
		poolHelper.activity.finish();
		// ���ص�¼ҳ��
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void beginOnCreateView() {
	}

	@Override
	protected void uiOther() {
		// TODO Auto-generated method stub
		
	}
}
