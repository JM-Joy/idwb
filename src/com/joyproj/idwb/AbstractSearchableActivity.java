package com.joyproj.idwb;

import com.joyproj.idwb.util.UrlCodeUtil;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public abstract class AbstractSearchableActivity extends AbstractPowerfulActivity {

	protected EditText editInput;
	
	// ���ݸ���Fragment�Ķ���
	private String postArgs = "";
	protected String url;
	
	@Override
	protected void doSthOnCreateView() {
		editInput = (EditText) findViewById(R.id.editInput);
		doNextCreateView();
	}
	
	/**
	 * ���Search��ť��ʱ��
	 * @param v
	 */
	public void clickSearch(View v){
		// ��ȡ����Ĺؼ���
		String searchWord = UrlCodeUtil.encode(editInput.getText().toString());
		if(searchWord != null && !"".equals(searchWord)){
			postArgs = "search=" + searchWord;
		}
		// �������뷨
		InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );     
        if ( imm.isActive( ) ) {     
            imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );   
        }
        doNextFromClickSearch(v);
	}

	/**
	 * ���������ؼ���
	 * @return �����ؼ���
	 */
	public String getPostArgs(){
		return postArgs;
	}	
	
	/**
	 * 
	 */
	public String getUrl(){
		return url;
	}
	
	/**
	 * ����û��ȡ��
	 */
	protected abstract void doNextCreateView();
	
	/**
	 * ���������ť �����ʻ���� Ȼ����
	 */
	protected abstract void doNextFromClickSearch(View v);
	
}
