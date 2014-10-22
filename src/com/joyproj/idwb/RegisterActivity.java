package com.joyproj.idwb;

import com.joyproj.idwb.data.UrlData;
import com.joyproj.idwb.helper.HttpHelper;
import com.joyproj.idwb.util.UrlCodeUtil;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AbstractProgressbarActivity {

	private static final int VAL_SAME_OK = 100;
	private static final int VAL_SAME_NAME = -100;
	private static final int VAL_SAME_EMAIL = -101;
	private static final int VAL_SAME_UNKNOW = -102;
	
	
	EditText inputName;
	EditText inputEmail;
	EditText inputPassword;
	EditText inputRepassword;
	Button btnRegister;
	
	private Handler handler;
	
	@Override
	protected int setResource() {
		return R.layout.act_register;
	}

	@Override
	protected void doSthOnCreateView() {
		inputName = (EditText) findViewById(R.id.inputName);
		inputEmail = (EditText) findViewById(R.id.inputEmail);
		inputPassword = (EditText) findViewById(R.id.inputPassword);
		inputRepassword = (EditText) findViewById(R.id.inputRepassword);
		btnRegister = (Button) findViewById(R.id.btnRegister);	
		// ����UI��Handler
		handler = returnHandler();
	}		
	
	/**
	 * ע�ᰴť����¼�
	 * @param v
	 */
	public void register(View v){
		// ��ȡ�û�����
		final String name = inputName.getText().toString();
		final String email = inputEmail.getText().toString();
		final String password = inputPassword.getText().toString();
		String repassword = inputRepassword.getText().toString();		
		// ����Ϸ���
		if(!validate(name, email, password, repassword)){
			return;
		}
		// UI �ȴ�״̬
		handler.sendEmptyMessage(UI_WAIT);
		// ����ע��
		new Thread(){
			@Override
			public void run() {
				String args = "name=" + UrlCodeUtil.encode(name) + "&email=" + email + "&password=" + password;
				HttpHelper httpUtil = new HttpHelper(UrlData.REGISTER, RegisterActivity.this);
				String res = httpUtil.post(args);
				System.out.println(res);
				// ���ע��ɹ����
				resHandle(res);
				// UI���� 
				handler.sendEmptyMessage(UI_RESET);
			};
		}.start();
	}
	
	/**
	 * ��⴫������Ϸ���
	 */
	private boolean validate(String name, String email, String password, String repassword) {
		if(!name.matches("^[\\u4E00-\\u9FA5\\w\\-]{3,8}$")){
			Toast.makeText(this, "�û���������3-8�������ַ�", Toast.LENGTH_LONG).show();
			return false;
		}
		if(!email.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")){
			Toast.makeText(this, "���䲻�Ϸ�", Toast.LENGTH_LONG).show();
			return false;
		}
		if(!password.matches("^\\S{4,12}$")){
			Toast.makeText(this, "���������4-12���ַ�", Toast.LENGTH_LONG).show();
			return false;
		}
		if(!password.equals(repassword)){
			Toast.makeText(this, "�������벻һ��", Toast.LENGTH_LONG).show();
			return false;	
		}
		return true;
	}	
	
	/**
	 * ���ݷ��ؽ����������
	 * 1.ok		-1.�û����ظ�	-2.�����ظ�		-3.δ֪
	 * @param res
	 */
	private void resHandle(String res){
		if(res == null || "".equals(res)){
			return;
		}
		if(res.equals("1")){
			handler.sendEmptyMessage(VAL_SAME_OK);
			finish();
		}
		else if(res.equals("-1")){
			handler.sendEmptyMessage(VAL_SAME_NAME);
		}
		else if(res.equals("-2")){
			handler.sendEmptyMessage(VAL_SAME_EMAIL);
		}
		else if(res.equals("-3")){
			handler.sendEmptyMessage(VAL_SAME_UNKNOW);
		}
	}
	
	/**
	 * ����һ������UI��Handler
	 * @return
	 */
	private Handler returnHandler() {
		return new Handler(){
			@Override
			public void handleMessage(Message msg){
				switch (msg.what) {
					case UI_WAIT:
						viewBase.setAlpha(0.5f);
						progressbar.setVisibility(View.VISIBLE);
						inputName.setEnabled(false);
						inputEmail.setEnabled(false);
						inputPassword.setEnabled(false);
						inputRepassword.setEnabled(false);
						btnRegister.setEnabled(false);
						break;
						
					case UI_RESET:
						viewBase.setAlpha(1f);
						progressbar.setVisibility(View.GONE);
						inputName.setEnabled(true);
						inputEmail.setEnabled(true);
						inputPassword.setEnabled(true);
						inputRepassword.setEnabled(true);
						btnRegister.setEnabled(true);
						break;
						
					case VAL_SAME_OK:
						Toast.makeText(RegisterActivity.this, "ע��ɹ�", Toast.LENGTH_SHORT).show();
						break;
						
					case VAL_SAME_NAME:
						Toast.makeText(RegisterActivity.this, "sorry, �û�����ע��!", Toast.LENGTH_LONG).show();
						break;
						
					case VAL_SAME_EMAIL:
						Toast.makeText(RegisterActivity.this, "sorry, ������ע��!", Toast.LENGTH_LONG).show();						
						break;
						
					case VAL_SAME_UNKNOW:
						Toast.makeText(RegisterActivity.this, "̫�ֲ���, δ֪����!", Toast.LENGTH_LONG).show();						
						break;
				}
			}
		};
	}	
	
}
