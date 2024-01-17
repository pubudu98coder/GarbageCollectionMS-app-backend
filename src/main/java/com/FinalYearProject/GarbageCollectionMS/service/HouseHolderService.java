package com.FinalYearProject.GarbageCollectionMS.service;

import com.FinalYearProject.GarbageCollectionMS.dto.HouseHolderDTO;
import com.FinalYearProject.GarbageCollectionMS.entity.HouseHolder;
import com.FinalYearProject.GarbageCollectionMS.repo.HousholderRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class HouseHolderService {
    @Autowired
    private HousholderRepo housholderRepo;
    @Autowired
    private ModelMapper modelMapper;
    public String addHouseHolder(HouseHolderDTO houseHolderDTO){
        if(housholderRepo.existsById(houseHolderDTO.getId())){
            return "Duplicate Data";
        }
        else{
            housholderRepo.save(modelMapper.map(houseHolderDTO, HouseHolder.class));
            return "Success";
        }
    }
}
