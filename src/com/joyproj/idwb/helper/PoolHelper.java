package com.joyproj.idwb.helper;

import android.app.Activity;

/**
 * ������
 * @author JM_Joy
 *
 */
public class PoolHelper {

	// ��ȡ MainActivity ����
	public Activity activity;
	
	private static PoolHelper instance = new PoolHelper();
	
	private PoolHelper(){
	}
	
	public static PoolHelper getInstance(){
		return instance;
	}
}
