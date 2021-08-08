package org.raddad.main

import kotlin.reflect.KVisibility

open class AccessibilityManager :
    AccessibilityManagerContract {


    override fun resolveWarehouseAccess(
        myWarehouse: Warehouse,
        hisWarehouse: Warehouse
    ): Registry {
        return when {
            isNotScope(hisWarehouse) -> resolveAccessibility(
                myWarehouse,
                hisWarehouse
            )
            hasSameScope(myWarehouse, hisWarehouse) -> getPublicDeclarations(
                hisWarehouse
            )

            else -> error("failed to add ${hisWarehouse::class.java.name}#(SCOPED) to ${myWarehouse::class.java.name} miss matched scope")
        }
    }

    private fun resolveAccessibility(
        myWarehouse: Warehouse,
        hisWarehouse: Warehouse
    ): Registry =
        when (hisWarehouse.accessibility) {
            Accessibility.ISOLATED -> error("failed to add ${hisWarehouse::class.java.name}#(PRIVATE) to ${myWarehouse::class.java.name} ")
            Accessibility.OPEN -> getPublicDeclarations(hisWarehouse)
            Accessibility.LOCAL -> getPublicDeclarations(hisWarehouse).mapKeys {
                it.key.copy(isClosed = true)
            }
        }

    private fun isNotScope(hisWarehouse: Warehouse) = hisWarehouse.scope == null


    private fun hasSameScope(myWarehouse: Warehouse, hisWarehouse: Warehouse) =
        hisWarehouse.scope == myWarehouse.scope

    private fun getPublicDeclarations(warehouse: Warehouse) =
        warehouse.dependencyRegistry
            .filter(::isVisibleDeclaration)

    private fun isVisibleDeclaration(declaration: Map.Entry<Metadata, Factory>): Boolean {
        return declaration.key.classType?.visibility == KVisibility.PUBLIC && !declaration.key.isClosed
    }


}