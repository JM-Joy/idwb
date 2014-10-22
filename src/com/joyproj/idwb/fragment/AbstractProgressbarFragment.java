package com.joyproj.idwb.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.joyproj.idwb.R;

public abstract class AbstractProgressbarFragment extends AbstractFragment {

	protected static final int UI_WAIT = 1;
	protected static final int UI_RESET = 2;	
	protected static final int UI_OTHER = 3;
	
	protected ProgressBar progressBar;
	protected View base;
	
	protected Handler handler;	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//
		View view = inflater.inflate(getResource(), container, false);
		// 
		progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		base = view.findViewById(R.id.base);
		// ��ʼ��Handler
		handler = returnHandler();
		//
		doSthOnCreateView(view);
		return view;
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
	protected abstract void doSthOnCreateView(View view);
	
	/**
	 * ѡ������д ����handler����UI_OTHRT��Ϣʱִ�еĶ���
	 */
	protected void uiOther(){
	}
	
	/**
	 * ����Handler
	 * @return
	 */
	private Handler returnHandler(){
		return new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case UI_WAIT:
						base.setVisibility(View.GONE);
						progressBar.setVisibility(View.VISIBLE);
						break;
						
					case UI_RESET:
						base.setVisibility(View.VISIBLE);
						progressBar.setVisibility(View.GONE);
						break;
						
					case UI_OTHER:
						uiOther();
						break;
				}
			}
		};
	}
		
	
}
