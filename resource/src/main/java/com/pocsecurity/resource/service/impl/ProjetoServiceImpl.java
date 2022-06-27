package com.pocsecurity.resource.service.impl;

import com.pocsecurity.resource.jpa.entity.Project;
import com.pocsecurity.resource.model.ProjetoDTO;
import com.pocsecurity.resource.service.ProjetoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetoServiceImpl implements ProjetoService {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public List<ProjetoDTO> getAllProjectResources() {
        List<Project> projectsFromRepo = Arrays.asList(Project.builder().id(1l).title("titulo 1").creatingDate(LocalDate.now()).build(),
                                                    Project.builder().id(2l).title("titulo 2").creatingDate(LocalDate.now()).build());
        return projectsFromRepo.stream()
                .map(project -> convertToDto(project))
                .collect(Collectors.toList());
    }

    private ProjetoDTO convertToDto(Project project) {
        ProjetoDTO projectModel = modelMapper.map(project, ProjetoDTO.class);
        return projectModel;
    }
}
