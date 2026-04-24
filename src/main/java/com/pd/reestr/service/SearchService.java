package com.pd.reestr.service;

import com.pd.reestr.DTO.Equipment_country_dto;
import com.pd.reestr.Mappers.EquipmentMapper;
import com.pd.reestr.Tables.Equipment;
import com.pd.reestr.repository.EquipmentRepository;
import com.pd.reestr.Pagination.SearchFilterRequest;
import com.pd.reestr.Pagination.SearchPageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentMapper equipmentMapper;

    public SearchPageResponse search(SearchFilterRequest filter, int page, int size, String sortBy, String sortDir) {
        // 1. Строим спецификацию для фильтрации
        Specification<Equipment> spec = buildSpecification(filter);

        // 2. Создаём Pageable с сортировкой
        Sort sort = buildSort(sortBy, sortDir);
        Pageable pageable = PageRequest.of(page, size, sort);

        // 3. Выполняем запрос к базе данных
        Page<Equipment> equipmentPage = equipmentRepository.findAll(spec, pageable);

        // 4. Преобразуем Entity в DTO (Equipment_country_dto)
        List<Equipment_country_dto> content = equipmentPage.getContent().stream()
                .map(equipmentMapper::toCountryDto)
                .collect(Collectors.toList());

        // 5. Возвращаем ответ
        return new SearchPageResponse(
                content,
                equipmentPage.getTotalElements(),
                equipmentPage.getTotalPages(),
                equipmentPage.getNumber(),
                equipmentPage.getSize(),
                sortBy,
                sortDir
        );
    }

    private Specification<Equipment> buildSpecification(SearchFilterRequest filter) {
        Specification<Equipment> spec = (root, query, cb) -> cb.conjunction();

        if (filter.getEquipmentName() != null && !filter.getEquipmentName().isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("name")), "%" + filter.getEquipmentName().toLowerCase() + "%"));
        }

        if (filter.getCountry() != null && !filter.getCountry().isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(cb.lower(root.get("country")), filter.getCountry().toLowerCase()));
        }

        if (filter.getOkid2() != null && !filter.getOkid2().isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("okid2")), "%" + filter.getOkid2().toLowerCase() + "%"));
        }

        if (filter.getAmountFrom() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("count"), filter.getAmountFrom().intValue()));
        }

        if (filter.getAmountTo() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("count"), filter.getAmountTo().intValue()));
        }

        return spec;
    }

    private Sort buildSort(String sortBy, String sortDir) {
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        // Маппинг полей из запроса в поля сущности
        String sortField = switch (sortBy) {
            case "name" -> "name";
            case "count" -> "count";
            case "country" -> "country";
            case "okid2" -> "okid2";
            default -> "name";
        };

        return Sort.by(direction, sortField);
    }
}