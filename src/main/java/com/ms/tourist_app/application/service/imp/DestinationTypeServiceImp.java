package com.ms.tourist_app.application.service.imp;

import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.DestinationTypeRepository;
import com.ms.tourist_app.application.input.type_destination.DestinationTypeDataInput;
import com.ms.tourist_app.application.input.type_destination.GetListDestinationTypeInput;
import com.ms.tourist_app.application.mapper.DestinationTypeMapper;
import com.ms.tourist_app.application.output.type_destination.DestinationTypeDataOutput;
import com.ms.tourist_app.application.service.DestinationTypeService;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.config.exception.BadRequestException;
import com.ms.tourist_app.config.exception.NotFoundException;
import com.ms.tourist_app.domain.entity.DestinationType;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DestinationTypeServiceImp implements DestinationTypeService {

    private final DestinationTypeMapper mapper = Mappers.getMapper(DestinationTypeMapper.class);

    private final JwtUtil jwtUtil;
    private final DestinationTypeRepository repository;

    public DestinationTypeServiceImp(JwtUtil jwtUtil, DestinationTypeRepository repository) {
        this.jwtUtil = jwtUtil;
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<DestinationTypeDataOutput> getListDestinationType(GetListDestinationTypeInput input) {
        List<DestinationType> destinationTypes = new ArrayList<>();
        if (input.getKeyword().trim().isBlank()) {
            destinationTypes = repository.findAll(PageRequest.of(input.getPage(), input.getSize())).getContent();
        }
        if (!input.getKeyword().trim().isBlank()) {
            destinationTypes = repository.findAllByNameContainingIgnoreCase(input.getKeyword().trim(), PageRequest.of(input.getPage(), input.getSize()));
        }
        List<DestinationTypeDataOutput> dataOutputs = new ArrayList<>();
        for (DestinationType destinationType :
                destinationTypes) {
            DestinationTypeDataOutput output = mapper.toDestinationTypeDataOutput(destinationType);
            dataOutputs.add(output);
        }
        return dataOutputs;
    }

    @Override
    @Transactional
    public DestinationTypeDataOutput getDestinationTypeDetail(Long id) {
        Optional<DestinationType> destinationType = repository.findById(id);
        if (destinationType.isEmpty()) {
            throw new NotFoundException(AppStr.DestinationType.tableTypeDestination + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        DestinationTypeDataOutput dataOutput = mapper.toDestinationTypeDataOutput(destinationType.get());
        return dataOutput;
    }

    @Override
    @Transactional
    public DestinationTypeDataOutput createDestinationType(DestinationTypeDataInput input) {
        DestinationType oldDestinationType = repository.findAllByNameContainingIgnoreCase(input.getName());
        if (oldDestinationType != null) {
            throw new BadRequestException(AppStr.DestinationType.tableTypeDestination + AppStr.Base.whiteSpace + AppStr.Exception.duplicate);
        }
        DestinationType destinationType = mapper.toDestinationType(input, null);
        destinationType.setCreateBy(jwtUtil.getUserIdFromToken());
        repository.save(destinationType);
        return new DestinationTypeDataOutput(destinationType.getId(),destinationType.getName());
    }

    @Override
    @Transactional
    public DestinationTypeDataOutput editDestinationType(Long id, DestinationTypeDataInput input) {
        Optional<DestinationType> oldDestinationType = repository.findById(id);
        if (oldDestinationType.isEmpty()) {
            throw new NotFoundException(AppStr.DestinationType.destinationType + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        DestinationType destinationType = mapper.toDestinationType(input, id);
        destinationType.setUpdateBy(jwtUtil.getUserIdFromToken());
        repository.save(destinationType);
        DestinationTypeDataOutput output = mapper.toDestinationTypeDataOutput(destinationType);
        return output;
    }

    @Override
    @Transactional
    public DestinationTypeDataOutput deleteDestinationType(Long id) {
        Optional<DestinationType> destinationType = repository.findById(id);
        if (destinationType.isEmpty()) {
            throw new NotFoundException(AppStr.DestinationType.destinationType + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        repository.delete(destinationType.get());
        DestinationTypeDataOutput output = mapper.toDestinationTypeDataOutput(destinationType.get());
        return output;
    }
}
