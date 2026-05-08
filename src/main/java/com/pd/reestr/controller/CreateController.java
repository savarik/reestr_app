package com.pd.reestr.controller;

import com.pd.reestr.DTO.Equipment_country_dto;
import com.pd.reestr.DTO.UpdateDto;
import com.pd.reestr.service.CreateService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/create")//Денис, пиши endpoint сюда
public class CreateController {
    private final CreateService service;

    public CreateController(CreateService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Equipment_country_dto createEquipment(@RequestBody UpdateDto request) {
        return service.createEquipment(request);
    }
}
