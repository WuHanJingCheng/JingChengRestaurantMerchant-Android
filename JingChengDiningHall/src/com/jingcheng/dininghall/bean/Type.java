package com.jingcheng.dininghall.bean;

public class Type {
	private int resId;//����ͼƬ
	private int resId_down;//����ͼƬѡ��״̬
	private String subMenuName;//������
	
	public Type(int resId, int resId_down, String subMenuName) {
		super();
		this.resId = resId;
		this.resId_down = resId_down;
		this.subMenuName = subMenuName;
	}
	public int getResId_down() {
		return resId_down;
	}
	public void setResId_down(int resId_down) {
		this.resId_down = resId_down;
	}
	public int getResId() {
		return resId;
	}
	public void getResId(String pictureUrl) {
		this.resId = resId;
	}
	public String getSubMenuName() {
		return subMenuName;
	}
	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
	}
	@Override
	public String toString() {
		return "Type [pictureUrl=" + resId + ", subMenuName="
				+ subMenuName + "]";
	}
	
	
}
