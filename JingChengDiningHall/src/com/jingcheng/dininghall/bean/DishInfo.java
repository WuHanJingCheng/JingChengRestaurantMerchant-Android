package com.jingcheng.dininghall.bean;

public class DishInfo {
	private int dishId;
	private String dishName;//²ËÃû
	private float price;//¼ÛÇ®
	private  int image;//Í¼Æ¬
	
	public DishInfo(int dishId, String dishName, float price, int image) {
		super();
		this.dishId = dishId;
		this.dishName = dishName;
		this.price = price;
		this.image = image;
	}
	

	public int getDishId() {
		return dishId;
	}
	public void setDishId(int dishId) {
		this.dishId = dishId;
	}
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "DishInfo [dishId=" + dishId + ", dishName=" + dishName
				+ ", price=" + price + ", image=" + image + "]";
	}
	
	
}
