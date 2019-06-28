package com.cocay.sicecd.dto;

public class ResponseGenericPagination {
	private Integer total;
	private Object rows;
	private String paginaSig;
	private String paginaAnt;
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Object getRows() {
		return rows;
	}
	public void setRows(Object rows) {
		this.rows = rows;
	}
	public String getPaginaSig() {
		return paginaSig;
	}
	public void setPaginaSig(String paginaSig) {
		this.paginaSig = paginaSig;
	}
	public String getPaginaAnt() {
		return paginaAnt;
	}
	public void setPaginaAnt(String paginaAnt) {
		this.paginaAnt = paginaAnt;
	}
}
