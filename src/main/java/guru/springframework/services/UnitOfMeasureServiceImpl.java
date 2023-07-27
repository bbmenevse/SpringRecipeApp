package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UOMToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

    UnitOfMeasureRepository unitOfMeasureRepository;

    private final UOMToUnitOfMeasureCommand unitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,UOMToUnitOfMeasureCommand unitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureCommand=unitOfMeasureCommand;
    }

    public List<UnitOfMeasure> getUnitOfMeasures()
    {
        List<UnitOfMeasure> unitOfMeasureList = new ArrayList<>();
        unitOfMeasureRepository.findAll().iterator().forEachRemaining(unitOfMeasureList::add);
        return unitOfMeasureList;

    }

    @Override
    public UnitOfMeasure findById(Long id) {
        return unitOfMeasureRepository.findById(id).orElse(null);

    }

    @Override
    public UnitOfMeasureCommand findCommandByID(Long id) {
        return unitOfMeasureCommand.convert(findById(id));
    }
}
