package com.pd.reestr.search.service;

import com.pd.reestr.search.dto.SearchFilterRequest;
import com.pd.reestr.search.dto.SearchPageResponse;
import com.pd.reestr.search.model.SearchEquipmentRecord;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class SearchStubService {

    private final List<SearchEquipmentRecord> records = createStubData();

    public SearchPageResponse search(SearchFilterRequest filter, int page, int size, String sortBy, String sortDir) {
        List<SearchEquipmentRecord> filtered = records.stream()
                .filter(record -> containsIgnoreCase(record.getEquipmentName(), filter.getEquipmentName()))
                .filter(record -> containsIgnoreCase(record.getSupplier(), filter.getSupplier()))
                .filter(record -> equalsIfPresent(record.getContractNumber(), filter.getContractNumber()))
                .filter(record -> dateFromMatches(record.getContractDate(), filter.getDateFrom()))
                .filter(record -> dateToMatches(record.getContractDate(), filter.getDateTo()))
                .filter(record -> amountFromMatches(record.getAmount(), filter.getAmountFrom()))
                .filter(record -> amountToMatches(record.getAmount(), filter.getAmountTo()))
                .sorted(resolveComparator(sortBy, sortDir))
                .collect(Collectors.toList());

        int safeSize = Math.max(size, 1);
        int safePage = Math.max(page, 0);
        int fromIndex = safePage * safeSize;
        int toIndex = Math.min(fromIndex + safeSize, filtered.size());

        List<SearchEquipmentRecord> pageContent = fromIndex >= filtered.size()
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

    private Comparator<SearchEquipmentRecord> resolveComparator(String sortBy, String sortDir) {
        Comparator<SearchEquipmentRecord> comparator;

        switch (sortBy) {
            case "equipmentName" -> comparator = Comparator.comparing(
                    SearchEquipmentRecord::getEquipmentName,
                    Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
            );
            case "supplier" -> comparator = Comparator.comparing(
                    SearchEquipmentRecord::getSupplier,
                    Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
            );
            case "amount" -> comparator = Comparator.comparing(
                    SearchEquipmentRecord::getAmount,
                    Comparator.nullsLast(BigDecimal::compareTo)
            );
            case "contractNumber" -> comparator = Comparator.comparing(
                    SearchEquipmentRecord::getContractNumber,
                    Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
            );
            case "contractDate" -> comparator = Comparator.comparing(
                    SearchEquipmentRecord::getContractDate,
                    Comparator.nullsLast(LocalDate::compareTo)
            );
            default -> comparator = Comparator.comparing(
                    SearchEquipmentRecord::getContractDate,
                    Comparator.nullsLast(LocalDate::compareTo)
            );
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

    private boolean dateFromMatches(LocalDate actualDate, LocalDate dateFrom) {
        return dateFrom == null || (actualDate != null && !actualDate.isBefore(dateFrom));
    }

    private boolean dateToMatches(LocalDate actualDate, LocalDate dateTo) {
        return dateTo == null || (actualDate != null && !actualDate.isAfter(dateTo));
    }

    private boolean amountFromMatches(BigDecimal actualAmount, BigDecimal amountFrom) {
        return amountFrom == null || (actualAmount != null && actualAmount.compareTo(amountFrom) >= 0);
    }

    private boolean amountToMatches(BigDecimal actualAmount, BigDecimal amountTo) {
        return amountTo == null || (actualAmount != null && actualAmount.compareTo(amountTo) <= 0);
    }

    private List<SearchEquipmentRecord> createStubData() {
        List<SearchEquipmentRecord> data = new ArrayList<>();

        data.add(new SearchEquipmentRecord(1L, "Ноутбук Lenovo ThinkPad", "ООО ТехСнаб",
                LocalDate.of(2026, 1, 15), new BigDecimal("78000.00"), "CNT-001"));
        data.add(new SearchEquipmentRecord(2L, "Проектор Epson EB-X06", "АО ПроектСервис",
                LocalDate.of(2026, 2, 10), new BigDecimal("52000.00"), "CNT-002"));
        data.add(new SearchEquipmentRecord(3L, "Принтер HP LaserJet", "ООО ТехСнаб",
                LocalDate.of(2026, 2, 18), new BigDecimal("31000.00"), "CNT-003"));
        data.add(new SearchEquipmentRecord(4L, "Монитор Samsung 24", "ИП Орлов",
                LocalDate.of(2026, 3, 2), new BigDecimal("14500.00"), "CNT-004"));
        data.add(new SearchEquipmentRecord(5L, "Сканер Canon", "АО КанцТрейд",
                LocalDate.of(2026, 3, 20), new BigDecimal("22300.00"), "CNT-005"));

        return data;
    }
}
