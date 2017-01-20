package com.jingcheng.dininghall.bean;

public class DishInfo {
	private int dishId;
	private String dishName;//²ËÃû
	private String price;//¼ÛÇ®
	private  String PictureUrlLarge;//Í¼Æ¬
	
	


	public DishInfo(String dishName, String price, String image) {
		super();
		this.dishName = dishName;
		this.price = price;
		this.PictureUrlLarge = image;
	}


	public DishInfo(int dishId, String dishName, String price, String image) {
		super();
		this.dishId = dishId;
		this.dishName = dishName;
		this.price = price;
		this.PictureUrlLarge = image;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getImage() {
		return PictureUrlLarge;
	}
	public void setImage(String image) {
		this.PictureUrlLarge = image;
	}
	@Override
	public String toString() {
		return "DishInfo [dishId=" + dishId + ", dishName=" + dishName
				+ ", price=" + price + ", image=" + PictureUrlLarge + "]";
	}
	
	
}
