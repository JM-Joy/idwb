package com.joyproj.idwb;

import com.joyproj.idwb.fragment.UserListFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;

public class AttentionActivity extends AbstractSearchableActivity {

	private CharSequence title;
	private String uid;
	
	@Override
	protected void doNextCreateView() {
		//
		editInput.setHint("��" + title + "������");
		// ����ǳ�ʼ�� AttentionList
		doNextFromClickSearch(null);
	}

	@Override
	protected void doNextFromClickSearch(View v) {
		// �����Ǹ�Fragment
		FragmentManager fragmentManager = getFragmentManager();  
	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	    Fragment userFrag = new UserListFragment();
	    fragmentTransaction.replace(R.id.replace, userFrag);
	    fragmentTransaction.commit();
	}

	@Override
	protected void beginOnCreateView() {
		title = getIntent().getCharSequenceExtra("title");
		uid = (String) getIntent().getCharSequenceExtra("uid");
		url = (String) getIntent().getCharSequenceExtra("url");
	}
	
	@Override
	protected CharSequence returnTitleText() {
		return title;
	}

	@Override
	protected CharSequence returnRightText() {
		return "";
	}

	@Override
	protected void onRightClick(View v) {
	}

	@Override
	protected int returnChildResource() {
		return R.layout.act_attend;
	}

	@Override
	protected void uiWait() {
	}

	@Override
	protected void uiReset() {
	}

	@Override
	protected void uiOther() {
	}


}
