package com.pd.reestr.search.controller;

import com.pd.reestr.search.dto.SearchFilterRequest;
import com.pd.reestr.search.dto.SearchPageResponse;
import com.pd.reestr.search.service.SearchStubService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchStubService searchStubService;

    public SearchController(SearchStubService searchStubService) {
        this.searchStubService = searchStubService;
    }

//    @GetMapping
//    public SearchPageResponse search(
//            @RequestParam(required = false) String equipmentName,
//            @RequestParam(required = false) String supplier,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
//            @RequestParam(required = false) BigDecimal amountFrom,
//            @RequestParam(required = false) BigDecimal amountTo,
//            @RequestParam(required = false) String contractNumber,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size,
//            @RequestParam(defaultValue = "contractDate") String sortBy,
//            @RequestParam(defaultValue = "desc") String sortDir
//    ) {
//        SearchFilterRequest filter = new SearchFilterRequest();
//        filter.setEquipmentName(equipmentName);
//        filter.setSupplier(supplier);
//
//        filter.setAmountFrom(amountFrom);
//        filter.setAmountTo(amountTo);
//
//        return searchStubService.search(filter, page, size, sortBy, sortDir);
//    }
}
