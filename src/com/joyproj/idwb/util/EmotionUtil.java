package com.joyproj.idwb.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;

public final class EmotionUtil {

	public static final String[] EMOTION_MAP = {
		"[Ц]", "[����]", "[����]", "[��]", "[��]","[ŭ]",
		"[��]", "[��]", "[��]", "[��]", "[����]","[��]",
		"[���]", "[Ǯ��]", "[����]", "[����]", "[��]","[����]",
		"[ί��]", "[��]", "[���]", "[��]", "[����]","[�ʻ�]",
		"[����]", "[��Ц]", "[����]", "[����]", "[б��]","[��ͬ]",
		"[��]", "[����]", "[˯]", "[�ſ�]", "[��ŭ]","[��]",
		"[��]", "[�ʺ�]", "[����]", "[̫��]", "[Ǯ]","[����]",
		"[����]", "[����]", "[����]", "[����]", "[ʤ��]","[��]",
		"[��]", "[OK]"
	};
	
	public static SpannableStringBuilder formatContent(String str, Context context){
		SpannableStringBuilder spannableStringBuilder = null;
		try {
			List<int[]> list = new ArrayList<int[]>();
			for(int i = 0; i < EMOTION_MAP.length; i++){
				int start = -1;
				while((start = str.indexOf(EMOTION_MAP[i], start + 1)) != -1){
					list.add(new int[]{start, i});
				}
			}
			spannableStringBuilder = new SpannableStringBuilder(str);
			Class clazz = Class.forName("com.joyproj.idwb.R$drawable");
			for(int[] pair : list){
				String index = ((pair[1] + 1) < 10) ? ("0" + (pair[1] + 1)) : ("" + (pair[1] + 1));
				int code = clazz.getField("emotion_" + index).getInt(null);
				Drawable drawable = context.getResources().getDrawable(code);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());					
				ImageSpan span = new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BASELINE);	
				spannableStringBuilder.setSpan(span, pair[0], pair[0] + EMOTION_MAP[pair[1]].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spannableStringBuilder;
	}
	
}
