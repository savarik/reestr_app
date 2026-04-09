package com.pd.reestr.search.dto;

import com.pd.reestr.search.model.SearchEquipmentRecord;

import java.util.List;

public class SearchPageResponse {
    private List<SearchEquipmentRecord> content;
    private long totalElements;
    private int totalPages;
    private int page;
    private int size;
    private String sortBy;
    private String sortDir;

    public SearchPageResponse() {
    }

    public SearchPageResponse(List<SearchEquipmentRecord> content, long totalElements, int totalPages,
                              int page, int size, String sortBy, String sortDir) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortDir = sortDir;
    }

    public List<SearchEquipmentRecord> getContent() {
        return content;
    }

    public void setContent(List<SearchEquipmentRecord> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }
}
