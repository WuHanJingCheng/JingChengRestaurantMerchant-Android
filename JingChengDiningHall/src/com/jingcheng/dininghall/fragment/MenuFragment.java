package com.jingcheng.dininghall.fragment;

import java.util.ArrayList;

import com.jingcheng.dininghall.adapter.MenuGridAdapter;
import com.jingcheng.dininghall.bean.DishInfo;
import com.jingcheng.jingchengdininghall.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


public class MenuFragment extends BaseFragment {
	private GridView grid_menu;
	private Context context;
	private ArrayList<DishInfo> list;
	private MenuGridAdapter adapter = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_menu, container,false);
		context = getActivity();
		grid_menu = (GridView) root.findViewById(R.id.grid_menu);
		if(adapter == null){
			init();
			adapter = new MenuGridAdapter(context, list);
		}
		grid_menu.setAdapter(adapter);
		return root;
	}

	private void init() {
		if(list == null){
			list = new ArrayList<DishInfo>();
		}
	}
}
