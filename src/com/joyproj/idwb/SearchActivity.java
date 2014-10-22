package com.joyproj.idwb;

import com.joyproj.idwb.data.UrlData;
import com.joyproj.idwb.fragment.UserListFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchActivity extends AbstractSearchableActivity {
	
	Spinner spinner;
	ArrayAdapter<String> adapter;
	
	@Override
	protected void doNextCreateView() {
		spinner = (Spinner) findViewById(R.id.spinner);
		
		String[] items = {"�û�", "΢��"}; 
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		url = UrlData.USER_SEARCH;
	}

	@Override
	protected void doNextFromClickSearch(View v) {
		int index = spinner.getSelectedItemPosition();
		switch(index){
			case 0:{
				doSearchUser();
				break;
			}
			case 1:{
				// TODO ����΢��
				Toast.makeText(this, "δ��", Toast.LENGTH_SHORT).show();
				break;
			}
		}		
	}
	
	/**
	 * ִ���û�����
	 */
	private void doSearchUser(){
		// �����Ǹ�Fragment
		FragmentManager fragmentManager = getFragmentManager();  
	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	    Fragment userFrag = new UserListFragment();
	    fragmentTransaction.replace(R.id.replace, userFrag);
	    fragmentTransaction.commit();
	}	
	
	@Override
	protected CharSequence returnTitleText() {
		return "����";
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
		return R.layout.act_child_search;
	}

	@Override
	protected void uiWait() {
	}

	@Override
	protected void uiReset() {
	}

	@Override
	protected void beginOnCreateView() {
	}

	@Override
	protected void uiOther() {
		// TODO Auto-generated method stub
		
	}

}