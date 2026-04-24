package com.pd.reestr.service;

import com.pd.reestr.DTO.Equipment_country_dto;
import com.pd.reestr.Pagination.SearchFilterRequest;
import com.pd.reestr.Pagination.SearchPageResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class SearchStubService {

    private final List<Equipment_country_dto> records = createStubData();

    public SearchPageResponse search(SearchFilterRequest filter, int page, int size, String sortBy, String sortDir) {
        List<Equipment_country_dto> filtered = records.stream()
                .filter(record -> containsIgnoreCase(record.getName(), filter.getEquipmentName()))
                .filter(record -> amountFromMatches(BigDecimal.valueOf(record.getCount()), filter.getAmountFrom()))
                .filter(record -> amountToMatches(BigDecimal.valueOf(record.getCount()), filter.getAmountTo()))
                .filter(record ->equalsIfPresent(record.getCountry(), filter.getCountry()))
                .filter(record -> equalsIfPresent(record.getOkid2(), filter.getOkid2()))
                .sorted(resolveComparator(sortBy, sortDir))
                .collect(Collectors.toList());

        int safeSize = Math.max(size, 1);
        int safePage = Math.max(page, 0);
        int fromIndex = safePage * safeSize;
        int toIndex = Math.min(fromIndex + safeSize, filtered.size());

        List<Equipment_country_dto> pageContent = fromIndex >= filtered.size()
                ? List.of()
                : filtered.subList(fromIndex, toIndex);

        int totalPages = (int) Math.ceil((double) filtered.size() / safeSize);

        return new SearchPageResponse(
                pageContent,
                filtered.size(),
                totalPages,
                safePage,
                safeSize,
                sortBy,
                sortDir
        );
    }

    private Comparator<Equipment_country_dto> resolveComparator(String sortBy, String sortDir) {
        Comparator<Equipment_country_dto> comparator;

        switch (sortBy) {
            case "name":
                comparator = Comparator.comparing(
                        Equipment_country_dto::getName,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
                );
                break;
            case "count":
                comparator = Comparator.comparing(
                        Equipment_country_dto::getCount,
                        Integer::compareTo
                );
                break;
            case "country":
                comparator = Comparator.comparing(
                        Equipment_country_dto::getCountry,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
                );
                break;
            case "okid2":
                comparator = Comparator.comparing(
                        Equipment_country_dto::getOkid2,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
                );
                break;
            default:
                comparator = Comparator.comparing(
                        Equipment_country_dto::getName,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
                );
                break;
        }

        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    private boolean containsIgnoreCase(String actualValue, String filterValue) {
        if (filterValue == null || filterValue.isBlank()) {
            return true;
        }
        if (actualValue == null) {
            return false;
        }
        return actualValue.toLowerCase(Locale.ROOT).contains(filterValue.toLowerCase(Locale.ROOT));
    }

    private boolean equalsIfPresent(String actualValue, String filterValue) {
        if (filterValue == null || filterValue.isBlank()) {
            return true;
        }
        if (actualValue == null) {
            return false;
        }
        return actualValue.equalsIgnoreCase(filterValue);
    }

    private boolean amountFromMatches(BigDecimal actualAmount, BigDecimal amountFrom) {
        return amountFrom == null || (actualAmount != null && actualAmount.compareTo(amountFrom) >= 0);
    }

    private boolean amountToMatches(BigDecimal actualAmount, BigDecimal amountTo) {
        return amountTo == null || (actualAmount != null && actualAmount.compareTo(amountTo) <= 0);
    }

    private List<Equipment_country_dto> createStubData() {
        List<Equipment_country_dto> data = new ArrayList<>();

        data.add(Equipment_country_dto.builder()
                .name("Ноутбук Lenovo ThinkPad")
                .count(5)
                .country("Russia")
                .okid2("45.98.11.031")
                .build());

        data.add(Equipment_country_dto.builder()
                .name("Проектор Epson EB-X06")
                .count(2)
                .country("Russia")
                .okid2("35.35.35.235")
                .build());

        data.add(Equipment_country_dto.builder()
                .name("Принтер HP LaserJet")
                .count(12)
                .country("Russia")
                .okid2("36.44.55.131")
                .build());

        data.add(Equipment_country_dto.builder()
                .name("Монитор Samsung 24")
                .count(4)
                .country("Russia")
                .okid2("36.01.25.18")
                .build());

        data.add(Equipment_country_dto.builder()
                .name("Сканер Canon")
                .count(14)
                .country("Russia")
                .okid2("32.99.53.130")
                .build());

        return data;
    }
}
