package com.joyproj.idwb;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public abstract class AbstractTitleBarActivity extends AbstractActivity {

	TextView textBack;
	TextView textTitle;
	TextView textRight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getResource());
		//
		initTitlebar();
		//
		doSthOnCreateView();
	}

	/**
	 * ��ʼ��������
	 */
	private void initTitlebar() {
		textBack = (TextView) findViewById(R.id.textBack);
		textTitle = (TextView) findViewById(R.id.textTitle);
		textRight = (TextView) findViewById(R.id.textRight);
		
		textBack.setText(setBack());
		textTitle.setText(setTitle());
		textRight.setText(setRight());
		
		textBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackClick(v);
			}
		});
		textRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onRightClick(v);
			}
		});
	}

	/**
	 * ������Դ�ļ�
	 * @return
	 */
	protected abstract int getResource();
	
	/**
	 * 
	 * @param view
	 */
	protected abstract void doSthOnCreateView();
	
	/**
	 * ���ñ���
	 * @return
	 */
	protected abstract CharSequence setTitle();
	
	/**
	 * �������������
	 * @return
	 */
	protected abstract CharSequence setBack();
	
	/**
	 * ������߰�ť����¼�
	 */
	protected abstract void onBackClick(View v);
	
	/**
	 * �����ұ߰�ť
	 * @return
	 */
	protected abstract CharSequence setRight();

	/**
	 * �����ұ߰�ť����¼�
	 * @param v �ұ߰�ť
	 */
	protected abstract void onRightClick(View v);
	
}
