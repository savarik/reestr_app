package com.pd.reestr.search.controller;

import com.pd.reestr.search.dto.SearchFilterRequest;
import com.pd.reestr.search.dto.SearchPageResponse;
import com.pd.reestr.search.service.SearchService;
import com.pd.reestr.search.service.SearchService;
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
            @RequestParam(defaultValue = "20") int size,
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