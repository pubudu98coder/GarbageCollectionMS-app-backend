package com.FinalYearProject.GarbageCollectionMS.repo;


import com.FinalYearProject.GarbageCollectionMS.entity.GarbageBin;
import com.FinalYearProject.GarbageCollectionMS.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TruckRepo extends JpaRepository<Truck,Integer> {


   List<Truck> findByStatus(String status);



}
