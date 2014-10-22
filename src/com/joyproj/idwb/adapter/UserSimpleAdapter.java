package com.joyproj.idwb.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.joyproj.idwb.AbstractActivity;
import com.joyproj.idwb.AvatarActivity;
import com.joyproj.idwb.InfoActivity;
import com.joyproj.idwb.R;
import com.joyproj.idwb.data.UrlData;
import com.joyproj.idwb.data.UserData;
import com.joyproj.idwb.helper.HttpHelper;
import com.joyproj.idwb.util.ImageUtil;

public class UserSimpleAdapter extends SimpleAdapter{

	private Handler mainHandler;
	private AbstractActivity activity;
	
	/**
	 * ��¼ͷ����б�, �´β�Ҫ��������!
	 */
	private Map<String, Drawable> mapAvatar = new HashMap<String, Drawable>();	
	
	public UserSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.activity =  ((AbstractActivity) context);
		this.mainHandler = activity.getMainHandler();
	}
	
	@Override
	public void setViewImage(final ImageView v, final String value) {
		super.setViewImage(v, value);
		// ����ͷ��
		if(mapAvatar.containsKey(value)){
			v.setImageDrawable(mapAvatar.get(value));
		}
		else{
			new Thread(){
				@Override
				public void run() {
					mapAvatar.put(value, loadAvatar(v, value));					
				};
			}.start();
		}		
	}
	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// ��ȡlistView����Ԫ��
		final View view = super.getView(position, convertView, parent);
		// ��ȡ
		final ToggleButton toggleAttend = (ToggleButton) view.findViewById(R.id.toggleAttention);
		final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		// �жϹ�ע״̬
		boolean b = "1".equals(toggleAttend.getText().toString());
		toggleAttend.setChecked(b);
		// ͨ�����ص�TextView��ȡ���������û���id  
		TextView textId = (TextView) view.findViewById(R.id.textId);
		final String id = textId.getText().toString();
		// ���� ToggleButton ����¼�
		toggleAttend.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				onToggleAttendClick(toggleAttend, progressBar, id, position);
			}	
		});
		// ����view ����¼�
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity, InfoActivity.class);
				intent.putExtra("title", "��/������ҳ");
				intent.putExtra("uid", id);
				activity.startActivity(intent);
			}
		});
		//
		return view;
	}
	
	/**
	 * ToggleButton ����¼�
	 * @param position 
	 * @param v
	 */
	private void onToggleAttendClick(final ToggleButton toggleAttend, final ProgressBar progressBar, final String id, final int position){
		// UI �ȴ�
		progressBar.setVisibility(View.VISIBLE);
		toggleAttend.setEnabled(false);
		// �������ù�ע״̬
		new Thread(){
			@Override
			public void run() {
				HttpHelper httpHelper = null;
				if(toggleAttend.isChecked()){
					httpHelper = new HttpHelper(UrlData.ATTENTION, activity);
				}
				else{
					httpHelper = new HttpHelper(UrlData.ATTENTION_CANCEL, activity);
				}
				String args = "id=" + UserData.id + "&password=" + UserData.password + "&aid=" + id;
				final String res = httpHelper.post(args);
				// ������
				mainHandler.post(new Runnable() {
					@Override
					public void run() {
						// ʧ�� , �ɹ��Ͳ������
						if(!"1".equals(res)){
							toggleAttend.setChecked(!toggleAttend.isChecked());
							Toast.makeText(activity, "ʧ��!", Toast.LENGTH_SHORT).show();
						}
						// UI : �ָ�
						toggleAttend.setEnabled(true);
						progressBar.setVisibility(View.GONE);
					}
				});
			};
		}.start();
	}
	
	/**
	 * ¼ȡͷ��
	 * @param imageAvatar
	 * @param id
	 */
	private Drawable loadAvatar(final ImageView imageAvatar, String id){
		String url = UrlData.AVATAR + "?id=" + id;
		return ImageUtil.loadImgByNetwork(url, mainHandler, new ImageUtil.Own() {
			@Override
			public void ok(Drawable drawable) {
				imageAvatar.setImageDrawable(drawable);
			}
			@Override
			public void failed(Exception e) {
				e.printStackTrace(System.err);
			}
		});
	}	
	
}
