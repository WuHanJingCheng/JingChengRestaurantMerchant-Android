package com.jingcheng.dininghall.bean;

public class Type {
	private int resId;//分类图片
	private String subMenuName;//分类名
	public Type(int resId, String subMenuName) {
		super();
		this.resId = resId;
		this.subMenuName = subMenuName;
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
