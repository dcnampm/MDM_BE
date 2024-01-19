package com.mdm.equipmentservice.service.impl;

import com.mdm.equipmentservice.mapper.SupplyUnitMapper;
import com.mdm.equipmentservice.model.dto.base.SupplyUnitDto;
import com.mdm.equipmentservice.model.entity.SupplyUnit;
import com.mdm.equipmentservice.model.repository.SupplyUnitRepository;
import com.mdm.equipmentservice.service.SupplyUnitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyServiceUnitImpl implements SupplyUnitService {

    private final SupplyUnitRepository supplyUnitRepository;

    private final SupplyUnitMapper supplyUnitMapper;


    public SupplyServiceUnitImpl(SupplyUnitRepository supplyUnitRepository, SupplyUnitMapper supplyUnitMapper) {
        this.supplyUnitRepository = supplyUnitRepository;
        this.supplyUnitMapper = supplyUnitMapper;
    }


    @Override
    public Page<SupplyUnitDto> getAllSupplyUnits(String keyword, Pageable pageable) {
        if (keyword != null) {
            return supplyUnitRepository.findAllByNameContainingIgnoreCase(keyword, pageable).map(supplyUnitMapper::toDto);
        }
        return supplyUnitRepository.findAll(pageable).map(supplyUnitMapper::toDto);
    }

    @Override
    public SupplyUnitDto getSupplyUnit(Long id) {
        return supplyUnitRepository.findById(id).map(supplyUnitMapper::toDto).orElseThrow();
    }

    @Override
    public SupplyUnitDto create(SupplyUnitDto supplyUnitDto) {
        SupplyUnit supplyUnit = supplyUnitMapper.toEntity(supplyUnitDto);
        supplyUnitRepository.save(supplyUnit);
        return supplyUnitMapper.toDto(supplyUnit);
    }

    @Override
    public SupplyUnitDto update(SupplyUnitDto supplyUnitDto, Long id) {
        SupplyUnit supplyUnit = supplyUnitMapper.toEntity_Update(supplyUnitDto, id);
        supplyUnitRepository.save(supplyUnit);
        return supplyUnitMapper.toDto(supplyUnit);
    }

    @Override
    public void deleteSupplyUnit(Long supplyUnitId) {
        supplyUnitRepository.deleteById(supplyUnitId);
    }

    @Override
    public void deleteMultipleSupplyUnit(List<Long> supplyUnitIds) {
        if (supplyUnitIds.isEmpty()) {
            return;
        }
        supplyUnitRepository.deleteAllById(supplyUnitIds);
    }
}
