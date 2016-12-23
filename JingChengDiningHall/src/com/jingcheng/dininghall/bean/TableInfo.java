package com.jingcheng.dininghall.bean;

public class TableInfo {
	private int tableId;//餐桌编号
	private int tableTag;//餐桌状态--0表示停用；1表示空闲；2表示使用中；3表示已结单
	
	
	public TableInfo(int tableId, int tableTag) {
		super();
		this.tableId = tableId;
		this.tableTag = tableTag;
	}
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	public int getTableTag() {
		return tableTag;
	}
	public void setTableTag(int tableTag) {
		this.tableTag = tableTag;
	}
	
	
}
