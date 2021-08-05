package org.raddad.main

interface AccessibilityManagerContract {
    fun resolveWarehouseAccess(
        myWarehouse: Warehouse,
        hisWarehouse: Warehouse
    ): Map<out Metadata, Factory>

}
