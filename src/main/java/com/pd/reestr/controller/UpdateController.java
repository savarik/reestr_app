package com.pd.reestr.controller;

import com.pd.reestr.DTO.UpdateDto;
import com.pd.reestr.repository.EquipmentRepository;
import com.pd.reestr.service.UpdateService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/update")
public class UpdateController {
    private final UpdateService service;

    public UpdateController(UpdateService service) {
        this.service=service;
    }

    @PatchMapping("/{name}")
    public UpdateDto update(@PathVariable String name, @RequestBody UpdateDto dto){
        return service.updateEquipment(name, dto);
    }
}
