package com.jingcheng.dininghall.bean;

public class TableInfo {
	private int tableId;//�������
	private int tableTag;//����״̬--0��ʾͣ�ã�1��ʾ���У�2��ʾʹ���У�3��ʾ�ѽᵥ
	
	
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
