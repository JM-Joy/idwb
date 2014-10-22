package com.joyproj.idwb.fragment;

import java.io.File;

import android.content.Intent;
import android.view.View;

import com.joyproj.idwb.AbstractActivity;
import com.joyproj.idwb.R;
import com.joyproj.idwb.WriteActivity;
import com.joyproj.idwb.adapter.WeiboSimpleAdapter;
import com.joyproj.idwb.data.DataDealer;
import com.joyproj.idwb.data.UrlData;
import com.joyproj.idwb.util.DateUtil;
import com.joyproj.idwb.widget.AutoListView;

public class HomeFragment extends AbstractPowerfulFragment {

	private AutoListView autolistHome;
	private AbstractActivity activity;
	
	@Override
	protected int getResource() {
		return R.layout.frag_home;
	}

	@Override
	protected void doSthOnCreateView(View view) {
		imageRight.setImageResource(R.drawable.pencil);
		autolistHome = (AutoListView) view.findViewById(R.id.autolistHome);
		
		String[] from = {"avatar", "name", "ctime",  "content", "uid", "wid", "praise", "praised"};
		int[] to = {R.id.weibo_avatar, R.id.weibo_name, R.id.weibo_time, R.id.weibo_content, R.id.textId, R.id.textWid, R.id.textPraise, R.id.textPraised};
		DataDealer dataDealer = new DataDealer((AbstractActivity) getActivity(), autolistHome, R.layout.weibo, WeiboSimpleAdapter.class, from, to);
		dataDealer.setHttpDetail(UrlData.WEIBO_ALL, "", DataDealer.LIMIT_WEIBO, new DataDealer.Own() {
			@Override
			public Object[] dealRes(Object[] fields) {
				// ���� * ����Ϊ΢�����ݵı��, ��Ҫת������
				return new Object[]{fields[4], fields[1],  DateUtil.format((String) fields[2]), "*" + fields[3], fields[4], fields[5], fields[6] == null ? 0 : fields[6], fields[7] == null ? 0 : 1};
			} 
		}); 
		dataDealer.work();
	}

	@Override
	protected CharSequence setTitle() {
		return "��ҳ";
	}
	
	/**
	 * ���pencliͼ�� ��΢��
	 */
	@Override
	protected void onRightClick(View v) {
		Intent intent = new Intent(getActivity(), WriteActivity.class);
		getActivity().startActivity(intent);
	}

	
}
