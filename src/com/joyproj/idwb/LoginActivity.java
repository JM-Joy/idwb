package com.joyproj.idwb;

import com.joyproj.idwb.data.UrlData;
import com.joyproj.idwb.data.UserData;
import com.joyproj.idwb.helper.HttpHelper;
import com.joyproj.idwb.util.MD5Util;
import com.joyproj.idwb.util.UrlCodeUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * �����Ϊʲô���ǲ��ܹ���ȫ�˳�������???
 * 
 * @author JM_Joy
 *
 */
public class LoginActivity extends AbstractProgressbarActivity implements View.OnClickListener{

	private static final int VAL_FAILED = -100;
	
	EditText inputName;
	EditText inputPassword;
	TextView textgotoRegister;
	Button btnLogin;
	
	Handler handler;
	
	@Override
	protected int setResource() {
		return R.layout.act_login;
	}

	@Override
	protected void doSthOnCreateView() {
		// ��ʼ�����
		textgotoRegister = (TextView) findViewById(R.id.textgotoRegister);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		inputName = (EditText) findViewById(R.id.inputName);
		inputPassword = (EditText) findViewById(R.id.inputPassword);
		// ���ü�����
		textgotoRegister.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
		// ����Handler
		handler = returnHandler();
	}
	
	@Override
	public void onClick(View v) {
		// ���ȥע���
		if(v == textgotoRegister){
			// ��ת��ע�����
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
		}
		// �����¼��ť
		else if(v == btnLogin){
			login();
		}
	}	
	
	/**
	 * ��¼����
	 */
	private void login(){
		final String name = inputName.getText().toString().trim();
		final String password = inputPassword.getText().toString().trim();
		if("".equals(name) || "".equals(password)){
			return;
		}
		// UI : �ȴ�״̬
		handler.sendEmptyMessage(UI_WAIT);
		// �����ύ��¼��Ϣ
		new Thread(){
			@Override
			public void run() {
				// ��������
				String md5psd = MD5Util.md5(password);
				String args = "name=" + UrlCodeUtil.encode(name) + "&password=" + md5psd;
				// �����ύ����
				HttpHelper httpUtil = new HttpHelper(UrlData.LOGIN, LoginActivity.this);
				String res = httpUtil.post(args);
				// �����¼�Ƿ�ɹ�
				handleLoginRes(res, name, md5psd);
				// UI : �ָ�
				handler.sendEmptyMessage(UI_RESET);
			};
		}.start();
	}
	
	/**
	 * �����ؽ��
	 * 1.��¼�ɹ�  0.�û������������
	 * @param res
	 * @param md5psd 
	 * @param name 
	 */
	private void handleLoginRes(String res, String name, String md5psd) {
		if(res == null){
			return;
		}
		else if("0".equals(res)){
			handler.sendEmptyMessage(VAL_FAILED);
			return;
		}
		else{
			//ʵ����SharedPreferences���󣨵�һ���� 
			SharedPreferences mySharedPreferences= getSharedPreferences("user", Context.MODE_PRIVATE); 
			//ʵ����SharedPreferences.Editor���󣨵ڶ����� 
			SharedPreferences.Editor editor = mySharedPreferences.edit(); 
			//��putString�ķ����������� 
			editor.putString("id", res);
			editor.putString("password", md5psd);
			//�ύ��ǰ���� 
			editor.commit();
			// ���浽��̬����
			UserData.id = res;
			UserData.password = md5psd;
			// ת��������
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
	/**
	 * ����һ������UI��Handler
	 * @return
	 */
	private Handler returnHandler() {
		return new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case UI_WAIT:
						viewBase.setAlpha(0.5f);
						progressbar.setVisibility(View.VISIBLE);
						inputName.setEnabled(false);
						inputPassword.setEnabled(false);
						break;
						
					case UI_RESET:
						viewBase.setAlpha(1f);
						progressbar.setVisibility(View.GONE);
						inputName.setEnabled(true);
						inputPassword.setEnabled(true);
						break;
					case VAL_FAILED:
						Toast.makeText(LoginActivity.this, "�û������������", Toast.LENGTH_LONG).show();
						break;
				}
			}
		};
	}
	
}
