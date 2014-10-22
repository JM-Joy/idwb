package com.joyproj.idwb.data;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.SimpleAdapter;

import com.joyproj.idwb.AbstractActivity;
import com.joyproj.idwb.helper.HttpHelper;
import com.joyproj.idwb.widget.AutoListView;

public class DataDealer implements AutoListView.OnRefreshListener, AutoListView.OnLoadListener{

	public static final int LIMIT_WEIBO = 1;
	public static final int LIMIT_USER = 2;
	
	private String strUrl;
	private String args;
	private AbstractActivity activity;
	private int resource;
	private Class<? extends SimpleAdapter> clazz;
	private String[] from;
	private int[] to;
	private AutoListView autolist;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private SimpleAdapter adapter;
	private Handler handler;
	
	private int page = 1;
	private int limitFlag = 0;
	private Own own;
	
	/**
	 * ���캯��
	 * @param activity
	 * @param autolist
	 * @param resource
	 * @param clazz
	 * @param from
	 * @param to
	 */
	public DataDealer(AbstractActivity activity, AutoListView autolist, int resource, Class<? extends SimpleAdapter> clazz, String[] from, int[] to) {
		this.activity = activity;
		this.resource = resource;
		this.clazz = clazz;
		this.from = from;
		this.to = to;
		this.autolist = autolist;
	}

	/**
	 * ����Http�ύ�ʹ������ϸ��Ϣ, ע�ⲻҪ����page��limit����
	 * @param strUrl post�ĵ�ַ
	 * @param args �ᶯ̬����page����, ���Բ��ô���page
	 * @param own ���ڽ��ձ�������߲������ɵķ���
	 */
	public void setHttpDetail(String strUrl, String args, Own own){
		this.strUrl = strUrl;
		this.args = args;
		this.own = own;
	}
	
	/**
	 * ����Http�ύ�ʹ������ϸ��Ϣ, ע�ⲻҪ����id, password, page��limit����
	 * @param strUrl post�ĵ�ַ
	 * @param args ����������� ���� ""
	 * @param own ���ڽ��ձ�������߲������ɵķ���
	 */
	public void setHttpDetail(String strUrl, String args, int limitFlag, Own own){
		this.strUrl = strUrl;
		this.args = args;
		this.limitFlag = limitFlag;
		this.own = own;
	}
	
	/**
	 * ����limit��ģʽ
	 * @param flag ��ѡ LIMIT_WEIBO , LIMIT_USER
	 */
	public void setLimitMod(int limitFlag){
		this.limitFlag = limitFlag;
	}
	
	/**
	 * ��ʼ����!
	 */
	public void work(){
		// �����ȡ SimpleAdapter ʵ��
		try {
			Constructor<? extends SimpleAdapter> construct = clazz.getConstructor(Context.class, List.class, int.class, String[].class, int[].class);
			adapter = construct.newInstance(activity, data, resource, from, to);
		} catch (Exception e) {
			e.printStackTrace();
		}
		autolist.setAdapter(adapter);
		autolist.setOnRefreshListener(this);
		autolist.setOnLoadListener(this);
		handler = returnHandler();
		initData();
	}
	
	/**
	 * ��������
	 * @return
	 */
	public List<Map<String, Object>> getData() {
		List<Map<String, Object>> reslist = new ArrayList<Map<String, Object>>();
		// ������ȡ�������Ķ���
		HttpHelper httpHelper = new HttpHelper(strUrl, activity);
		// ����Limitģʽ����limitӦ�ü�ʲô����
		String limit = null;
		switch(limitFlag){
			case LIMIT_USER:
				limit = UserData.perListrows + "";
				break;
			case LIMIT_WEIBO:
				limit =UserData.weiboListrows + "";
				break;
			default:
				limit = UserData.DEFAULT_LISTROWS + "";
				break;
		}
		// ƴ��post����
		if(args != null && !"".equals(args)){
			args += "&";
		}
		String resStr = httpHelper.post(args + "id=" + UserData.id + "&password=" + UserData.password  + "&page=" + page + "&limit=" + limit);
		// ���û����Ϊ��
		if(resStr == null || "".equals(resStr)){
			return reslist;
		}
		//  ������ﾭ��Ҫ����
//		System.err.println(resStr);
		// ת��json��list 
		try {
			JSONArray jsonArray = new JSONArray(resStr);
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject jsonObj = (JSONObject) jsonArray.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				// ���ţ�� �Ѵ������ȡ���ֶ���Ϣ���ظ�������ȥ����
				Object[] fields = new Object[from.length];
				for(int ii = 0; ii < from.length; ii++){
					if(jsonObj.isNull(from[ii])){
						fields[ii] = null;
					} else{
						fields[ii] = jsonObj.get(from[ii]);
					}
				}
				Object[] dealedRes = own.dealRes(fields);
				for(int ii = 0; ii < from.length; ii++){
					map.put(from[ii], dealedRes[ii]);
				}
				reslist.add(map);
			}
		} catch (JSONException e) {
			System.out.println(e);
		}
		return reslist;
	}	
	
	private void loadData(final int what) {
		// �ӷ�������ȡ����
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = handler.obtainMessage();
				msg.what = what;
				msg.obj = getData();
				handler.sendMessage(msg);
			}
		}).start();
	}

	private void initData() {
		loadData(AutoListView.REFRESH);
	}	
	
	@Override
	public void onRefresh() {
		page = 1;
		loadData(AutoListView.REFRESH);
	}

	@Override
	public void onLoad() {
		page++;
		loadData(AutoListView.LOAD);
	}

	/**
	 * ����Handler
	 * @return
	 */
	private Handler returnHandler(){
		return new Handler() {
			@Override
			public void handleMessage(Message msg) {
				List<Map<String, Object>> result = (List<Map<String, Object>>) msg.obj;
				switch (msg.what) {
					case AutoListView.REFRESH:
						autolist.onRefreshComplete();
						data.clear();
						data.addAll(result);
						break;
					case AutoListView.LOAD:
						autolist.onLoadComplete();
						data.addAll(result);
						break;
				}
				autolist.setResultSize(result.size());
				adapter.notifyDataSetChanged();
			};
		};		
	}
	
	/**
	 * DataDealer �������ݷ�����
	 * @author JM_Joy
	 *
	 */
	public interface Own{
		/**
		 * ���ݱ����Ĵ��������󵽵Ĳ���, ˳���from��˳��һֱ
		 * @param fields ���from�е�Ԫ��û�ж�Ӧ�ķ���ֵ, ����ڵ�fields��Ԫ��Ϊ��
		 * @return ����������
		 */
		Object[] dealRes(Object[] fields);
	}
}
