package com.jingcheng.dininghall.bean;

import java.io.Serializable;

public class Type implements Serializable{
	private int typeId;//ID
	private String PictureUrl;//分类图片
	private String PictureUrlSelected;//分类图片选中状态
	private String MenuName;//分类名
	
	


	public Type(int typeId, String pictureUrl, String pictureUrlSelected,
			String menuName) {
		super();
		this.typeId = typeId;
		PictureUrl = pictureUrl;
		PictureUrlSelected = pictureUrlSelected;
		MenuName = menuName;
	}



	public Type(String pictureUrl, String pictureUrlSelected, String menuName) {
		super();
		PictureUrl = pictureUrl;
		PictureUrlSelected = pictureUrlSelected;
		MenuName = menuName;
	}



	public int getTypeId() {
		return typeId;
	}



	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}



	public String getPictureUrl() {
		return PictureUrl;
	}



	public void setPictureUrl(String pictureUrl) {
		PictureUrl = pictureUrl;
	}



	public String getPictureUrlSelected() {
		return PictureUrlSelected;
	}



	public void setPictureUrlSelected(String pictureUrlSelected) {
		PictureUrlSelected = pictureUrlSelected;
	}



	public String getMenuName() {
		return MenuName;
	}



	public void setMenuName(String menuName) {
		MenuName = menuName;
	}



	
}
