package com.pd.reestr.controller;

import com.pd.reestr.Pagination.SearchFilterRequest;
import com.pd.reestr.Pagination.SearchPageResponse;
import com.pd.reestr.service.SearchService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public SearchPageResponse search(
            @RequestParam(required = false) String equipmentName,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String okid2,
            @RequestParam(required = false) BigDecimal amountFrom,
            @RequestParam(required = false) BigDecimal amountTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        SearchFilterRequest filter = new SearchFilterRequest();
        filter.setEquipmentName(equipmentName);
        filter.setCountry(country);
        filter.setOkid2(okid2);
        filter.setAmountFrom(amountFrom);
        filter.setAmountTo(amountTo);

        return searchService.search(filter, page, size, sortBy, sortDir);
    }
}