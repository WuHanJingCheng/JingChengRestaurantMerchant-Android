package com.jingcheng.dininghall.bean;

import java.io.Serializable;

public class Type implements Serializable{
	private int typeId;//ID
	private String PictureUrl;//����ͼƬ
	private String PictureUrlSelected;//����ͼƬѡ��״̬
	private String MenuName;//������
	
	


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
