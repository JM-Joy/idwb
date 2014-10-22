package com.joyproj.idwb.data;

public interface UrlData {
	
	/**
	 * ��վͷ
	 */
//	String APP = "http://joyproj.com/app/index.php";
	String APP = "http://jmjoyftp.hk15132.wsdns.cc/app/index.php";
	
	/**
	 * ����ҳ��
	 */
	String SPLASH = APP;
	
	/**
	 * ע������
	 */
	String REGISTER =  APP + "/Register/index";
	
	/**
	 * ��¼����
	 */
	String LOGIN = APP + "/Login/index";
	
	/**
	 * �����Լ���΢��
	 */
	String WEIBO_ONE = APP + "/Weibo/one";
	
	/**
	 * ��������΢��
	 */
	String WEIBO_ALL = APP + "/Weibo/all";
	
	/**
	 * ����΢��
	 */
	String WRITE = APP + "/Write/index";
	
	/**
	 * �������������Ϣ
	 */
	String ME = APP + "/Me/index";

	/**
	 * �����ע
	 */
	String ATTENTION = APP + "/Attention/index";
	
	/**
	 * ȡ����ע
	 */
	String ATTENTION_CANCEL = APP + "/Attention/cancel";
	
	/**
	 * �ؼ���Ѱ���û�
	 */
	String USER_SEARCH = APP + "/User/index";
	
	/**
	 * ��ȡ�ҵĹ�ע
	 */
	String ATTENTION_LIST = APP + "/Attention/getAttention";
	
	/**
	 * ��ȡ�ҵķ�˿
	 */
	String FANS_LIST = APP + "/Attention/getFans";
	
	/**
	 * ��ȡĳ���˵���ҳ��Ϣ
	 */
	String INFO = APP + "/Info/index";
	
	/**
	 * ��ȡĳ���˵���ҳ��Ϣ
	 */
	String MODIFY = APP + "/Info/modify";
	
	/**
	 * ��ȡͷ��
	 */
	String AVATAR = APP + "/Image/avatar";
	
	/**
	 * �ϴ�ͷ��
	 */
	String AVATAR_UPLOAD = APP + "/Upload/avatar";
	
	/**
	 * ����
	 */
	String PRAISE = APP + "/Weibo/praise";
	
}
