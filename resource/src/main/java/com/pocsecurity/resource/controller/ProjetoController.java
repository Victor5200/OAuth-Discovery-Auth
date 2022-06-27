package com.pocsecurity.resource.controller;

import com.pocsecurity.resource.model.ProjetoDTO;
import com.pocsecurity.resource.service.ProjetoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "Operations pertaining to project")
@RestController
@RequestMapping("/rest/project")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @GetMapping("/")
    @ApiOperation(value = "Todos os Projetos")
    public List<ProjetoDTO> getAllProjectResources() {
        return projetoService.getAllProjectResources();
    }

}
