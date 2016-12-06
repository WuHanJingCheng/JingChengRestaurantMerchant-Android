package com.jingcheng.dininghall.bean;

public class TableInfo {
	private int people;
	private int tableId;
	
	public TableInfo(int people, int tableId) {
		super();
		this.people = people;
		this.tableId = tableId;
	}
	
	public int getPeople() {
		return people;
	}
	public void setPeople(int people) {
		this.people = people;
	}
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	
	
}
