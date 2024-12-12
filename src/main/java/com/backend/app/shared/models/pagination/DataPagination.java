package com.backend.app.shared.models.pagination;

public class DataPagination {

  Integer page;
  Integer limit;
  String orderBy;
  String orderName;
  String search;
  Integer offset;

  public DataPagination(Integer page, Integer limit, String orderBy, String orderName, String search) {
    this.page = (page != null) ? page : 1;
    this.limit = (limit != null) ? limit : 10;
    this.orderBy = (orderBy != null) ? orderBy : "created_date";
    this.orderName = (orderName != null) ? orderName : "DESC";
    this.search = (search != null) ? search : "";
    this.offset = (this.page - 1) * this.limit;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  public String getOrderName() {
    return orderName;
  }

  public void setOrderName(String orderName) {    
    this.orderName = orderName;
  }

  public String getSearch() {
    return search;
  } 

  public void setSearch(String search) {
    this.search = search;
  } 
  
  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

}
