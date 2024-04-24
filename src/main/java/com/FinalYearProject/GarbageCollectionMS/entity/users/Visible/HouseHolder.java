package com.FinalYearProject.GarbageCollectionMS.entity.users.Visible;

import com.FinalYearProject.GarbageCollectionMS.entity.GarbageBin;
import com.FinalYearProject.GarbageCollectionMS.entity.HouseHolderNotificationLog;
import com.FinalYearProject.GarbageCollectionMS.entity.Inquiry;
import com.FinalYearProject.GarbageCollectionMS.securityImplentation.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;



@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@PrimaryKeyJoinColumn//(name = "houseHolder_id")
public class HouseHolder extends User {
//    //@Column(nullable = false)
//    private float longitude;
//    //@Column(nullable = false)
//    private float latitude;
    private String houseNo;
    private boolean approved;
    @ManyToOne
    @JoinColumn(name = "garbageBinID",referencedColumnName = "id")
    private GarbageBin garbageBin;

    @ManyToOne
    @JoinColumn(name="inquiryID",referencedColumnName = "id")
    private Inquiry inquiry;

    @OneToMany(mappedBy = "houseHolder")

    private List<HouseHolderNotificationLog> houseHolderNotificationLogList;

    //methods
    //register


}
