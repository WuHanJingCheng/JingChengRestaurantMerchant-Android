package com.jingcheng.dininghall.fragment;

import java.lang.reflect.Field;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
//	@Override
//	public void onDetach() {
//		 super.onDetach();  
//	        try {  
//	            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");  
//	            childFragmentManager.setAccessible(true);  
//	            childFragmentManager.set(this, null);  
//	  
//	        } catch (NoSuchFieldException e) {  
//	            throw new RuntimeException(e);  
//	        } catch (IllegalAccessException e) {  
//	            throw new RuntimeException(e);  
//	        }  
//	}
}
