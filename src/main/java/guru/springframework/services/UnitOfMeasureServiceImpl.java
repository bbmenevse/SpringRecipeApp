package guru.springframework.services;

import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

    UnitOfMeasureRepository unitOfMeasureRepository;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }



    public List<UnitOfMeasure> getUnitOfMeasures()
    {
        List<UnitOfMeasure> unitOfMeasureList = new ArrayList<>();
        unitOfMeasureRepository.findAll().iterator().forEachRemaining(unitOfMeasureList::add);
        return unitOfMeasureList;

    }
}
