package com.pd.reestr.controller;

import com.pd.reestr.service.DeleteService;
import jakarta.persistence.Column;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delete")
public class DeleteController {
    private final DeleteService service;

    public DeleteController(DeleteService service) {
        this.service = service;
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String name){
        service.deleteEquipment(name);
    }
}
