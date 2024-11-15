package com.backend.app.shared.models.pagination;

public class DataPagination {

  Integer page;
  Integer limit;
  String orderBy;
  String orderName;
  String search;
  Integer offset;

  public DataPagination(Integer page, Integer limit, String orderBy, String orderName, String search, Integer offset) {
    this.page = page;
    this.limit = limit;
    this.orderBy = orderBy;
    this.orderName = orderName;
    this.search = search;
    this.offset = offset;
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
