package com.bank.mortgage.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
@Data
public class CustomPageResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalItems;
    private int totalPages;
    private boolean isLast;

    public CustomPageResponse(List<T> content, Page<?> pageData) {
        this.content = content;
        this.page = pageData.getNumber();
        this.size = pageData.getSize();
        this.totalItems = pageData.getTotalElements();
        this.totalPages = pageData.getTotalPages();
        this.isLast = pageData.isLast();
    }

}