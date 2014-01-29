/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.piginzoo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.KeyEvent;
import android.widget.Toast;
import static com.piginzoo.Const.*;

public class PreferencesActivity extends PreferenceActivity  implements OnSharedPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       // setPreferenceScreen(createPreferenceHierarchy());
        addPreferencesFromResource(R.xml.pereference);
        
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    
//    /**
//     * ������Return�󣬾ͻ����OnPause().OnStop(),OnDestroy().������ǰ�Home���Ͳ���ִ��OnDestroy��
//     * ���������й����У����ְ�Back�����ǿ��Է���RESULT_CANCELED�ģ�����SDK doc��ԭ����������������Ҳ��������ݵġ�
//     * ����ζ�ţ�����������ڷ���RESULT_CANCELEDʱ���������ݣ���ô��Ҫ�ػ�Back�����¼�����
//     * ��ԭ������RESULT_CANCELED�ĺ����߼�copy���¼��������档
//     */
//    private void returnValues() {
//        super.onPause();
//    	Intent i = new Intent();
//    	SharedPreferences setting =  this.getPreferences(MODE_PRIVATE);
//    	i.putExtra(KEY_REFRESH_REQUENCY,
//    			parseInt(setting.getString(KEY_REFRESH_REQUENCY,null)));
//    	i.putExtra(KEY_HEAD_MOVE_STEP,
//    			parseInt(setting.getString(KEY_HEAD_MOVE_STEP,null)));
//    	i.putExtra(KEY_PAUSE_TIME,
//    			parseInt(setting.getString(KEY_PAUSE_TIME,null)));
//    	i.putExtra(KEY_HEAD_MOVE_REQUENCY,
//    			parseInt(setting.getString(KEY_HEAD_MOVE_REQUENCY,null))); 
//    	
//        this.setResult(RESULT_CANCELED, i);  
//        this.finish();  
//    	
//    }
    
//    /**
//     * ����Ҫע����ǣ��ڴ���Back���¼���return true���ʾ���¼����ٴ��ݸ������������������Ϊ�ɵ�ǰ����ȫȨ������
//     * ������returnǰfinish��ǰActivity������ԭ������Ϊ���֣���Ȼ���ǿ�������Ϊ���ص�ǰActivity��������Ϊ��
//     */
//    @Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // �Ƿ񴥷�����Ϊback��    
//        if (keyCode == KeyEvent.KEYCODE_BACK) { 
//             
//            // ʵ���� Bundle��������Ҫ���ݵĲ��� 
//        	this.returnValues();
//            return true; 
//        }else { 
//            return super.onKeyDown(keyCode, event); 
//        } 
//	}

//	private int parseInt(String val){
//    	try{
//    		return Integer.parseInt(val);
//    	}catch(Exception e){
//    		return -1;
//    	}
//    }
    
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    	
    	String newValue = sharedPreferences.getString(key,null);
    	
    	if(newValue ==null){
    		Toast.makeText(this, "�������Ը��� ["+key+"] ʧ��:��ֵΪ��.", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	try{
    		Integer.parseInt(newValue);
    		Toast.makeText(this, "������������ ["+key+"] Ϊ��ֵ: "+newValue, Toast.LENGTH_SHORT).show();
    	}catch(NumberFormatException e){
    		Toast.makeText(this, "�������Ը��� ["+key+"] ʧ�ܣ���ֵ���������֣�"+newValue, Toast.LENGTH_SHORT).show();
    	}
    }
    
/**
 * ͨ�����뷽ʽ�������ã������Ѿ��޸ĳ�ΪXML��ʽ�ˡ�
 */
//    private PreferenceScreen createPreferenceHierarchy() {
//        // Root
//        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
//        
//        // Inline preferences 
//        PreferenceCategory inlinePrefCat = new PreferenceCategory(this);
//        inlinePrefCat.setTitle(R.string.pereference);
//        root.addPreference(inlinePrefCat);
//        
//        // Toggle preference
//        CheckBoxPreference togglePref = new CheckBoxPreference(this);
//        togglePref.setKey("toggle_preference");
//        togglePref.setTitle(R.string.pereference);
//        togglePref.setSummary(R.string.pereference);
//        inlinePrefCat.addPreference(togglePref);
//                
//    
//        // Edit text preference
//        EditTextPreference editTextPref = new EditTextPreference(this);
//        editTextPref.setDialogTitle(R.string.pereference);
//        editTextPref.setKey("edittext_preference");
//        editTextPref.setTitle(R.string.pereference);
//        editTextPref.setSummary(R.string.pereference);
//        inlinePrefCat.addPreference(editTextPref);
//
//        return root;
//    }
}
