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
//     * 当按了Return后，就会调用OnPause().OnStop(),OnDestroy().你如果是按Home，就不会执行OnDestroy。
//     * 另外在运行过程中，发现按Back键后，是可以返回RESULT_CANCELED的，看了SDK doc后，原来真的是那样，而且不带有数据的。
//     * 这意味着，如果你设想在返回RESULT_CANCELED时并返回数据，那么需要截获Back键的事件处理，
//     * 把原来返回RESULT_CANCELED的核心逻辑copy到事件处理里面。
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
//     * 这里要注意的是，在处理Back键事件后return true则表示本事件不再传递给其他函数处理，可理解为由当前函数全权负责处理，
//     * 所以在return前finish当前Activity，保持原来的行为表现，当然我们可以设置为隐藏当前Activity等其他行为。
//     */
//    @Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // 是否触发按键为back键    
//        if (keyCode == KeyEvent.KEYCODE_BACK) { 
//             
//            // 实例化 Bundle，设置需要传递的参数 
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
    		Toast.makeText(this, "您的属性更改 ["+key+"] 失败:新值为空.", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	try{
    		Integer.parseInt(newValue);
    		Toast.makeText(this, "您更改了属性 ["+key+"] 为新值: "+newValue, Toast.LENGTH_SHORT).show();
    	}catch(NumberFormatException e){
    		Toast.makeText(this, "您的属性更改 ["+key+"] 失败，新值必须是数字："+newValue, Toast.LENGTH_SHORT).show();
    	}
    }
    
/**
 * 通过代码方式加载配置，不过已经修改成为XML方式了。
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
