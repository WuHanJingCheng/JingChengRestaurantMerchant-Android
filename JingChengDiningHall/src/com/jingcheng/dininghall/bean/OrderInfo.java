package com.jingcheng.dininghall.bean;

public class OrderInfo {
	private String name;//菜品名称
	private float price;//菜品价格
	private int count;//点菜数量
	private Boolean push;//是否上桌
	
	public OrderInfo(String name, float price, int count, Boolean push) {
		super();
		this.name = name;
		this.price = price;
		this.count = count;
		this.push = push;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Boolean getPush() {
		return push;
	}
	public void setPush(Boolean push) {
		this.push = push;
	}
	
	
	
}	
