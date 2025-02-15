package com.example.productcatalogservice.dtos;

import com.example.productcatalogservice.models.SortParam;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchRequestDto {
    private String query;
    private int pageNumber;
    private int pageSize;

    private List<SortParam> sortParams;
//    private String sortBy;
//    private String sortOrder;
}
