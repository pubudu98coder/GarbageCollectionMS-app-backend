package com.FinalYearProject.GarbageCollectionMS.repo;

import com.FinalYearProject.GarbageCollectionMS.entity.users.HouseHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseHolderRepository extends JpaRepository<HouseHolder,Integer> {

    Optional<HouseHolder> findByEmail(String email);
    Optional<HouseHolder> findByHouseNo(String houseNo);
}
