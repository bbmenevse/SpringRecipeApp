package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;

import java.util.List;

public interface UnitOfMeasureService {


    List<UnitOfMeasure> getUnitOfMeasures();

    UnitOfMeasureCommand findCommandByID(Long id);

    UnitOfMeasure findById(Long id);
}
