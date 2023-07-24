package com.ms.tourist_app.application.output.common;

import com.ms.tourist_app.application.constants.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingMeta {

  private Long total;

  private Integer pageNum;

  private Integer pageSize;

  private String sortType;

  private String sortBy;

  private Integer totalPage;

  private Integer offSet;

  private Integer limit;

  public PagingMeta(Long total, Integer pageNum, Integer pageSize, String sortBy, String sortType) {
    this.total = total;
    this.pageSize = pageSize;
    this.pageNum = getPageNum(pageNum);
    this.sortType = sortType;
    this.sortBy = sortBy;
    this.totalPage = getTotalPage();
    this.offSet = getOffSet();
    this.limit = getLimit();
  }

  public Integer getPageNum(int pageNum) {
    if (total.equals(CommonConstant.ZERO_VALUE)) {
      return CommonConstant.ONE_VALUE.intValue();
    }
    if ((long) pageNum * pageSize > total) {
      pageNum = (int) Math.ceil(total * 1.0 / pageSize);
    }
    return pageNum;
  }

  public Integer getOffSet() {
    int offSet = (pageNum - CommonConstant.ONE_INT_VALUE) * pageSize;
    if (totalPage < pageNum) {
      offSet = totalPage;
    }
    return offSet;
  }

  public Integer getLimit() {
    return pageSize;
  }

  public Integer getTotalPage() {
    if (pageSize.equals(CommonConstant.ZERO_INT_VALUE)) {
      return CommonConstant.ZERO_INT_VALUE;
    }
    return (int) Math.ceil(total * 1.0 / pageSize);
  }

}