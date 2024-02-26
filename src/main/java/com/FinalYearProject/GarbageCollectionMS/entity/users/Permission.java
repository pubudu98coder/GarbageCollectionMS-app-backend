package com.FinalYearProject.GarbageCollectionMS.entity.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete"),
    HOUSE_HOLDER_READ("house_holder:read"),
    HOUSE_HOLDER_UPDATE("house_holder:update"),
    HOUSE_HOLDER_CREATE("house_holder:create"),
    HOUSE_HOLDER_DELETE("house_holder:delete"),
    DRIVER_READ("truck_driver:read"),
    DRIVER_UPDATE("truck_driver:update"),
    DRIVER_CREATE("truck_driver:create"),
    DRIVER_DELETE("truck_driver:delete"),
    ;

    private final String permission;
}