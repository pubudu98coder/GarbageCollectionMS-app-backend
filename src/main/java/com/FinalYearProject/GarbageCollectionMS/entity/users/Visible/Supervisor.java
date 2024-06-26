package com.FinalYearProject.GarbageCollectionMS.entity.users.Visible;

import com.FinalYearProject.GarbageCollectionMS.entity.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@PrimaryKeyJoinColumn
public class Supervisor extends User {
    private String empNumber;
}
