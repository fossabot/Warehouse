package org.raddad.main

import kotlin.reflect.KClass

fun warehouse(
    scope: Any,
    accessibilityManager: AccessibilityManagerContract = AccessibilityManager(),
    block: Warehouse.() -> Unit,
) = Warehouse(scope, accessibilityManager = accessibilityManager).apply(
    block
)

fun warehouse(
    accessibility: Accessibility = Accessibility.ISOLATED,
    accessibilityManager: AccessibilityManagerContract = AccessibilityManager(),
    block: Warehouse.() -> Unit,
) = Warehouse(accessibility, accessibilityManager = accessibilityManager).apply(
    block
)

fun warehouse(
    scope: Any,
    accessibilityManager: AccessibilityManagerContract = AccessibilityManager(),
) = Warehouse(scope, accessibilityManager = accessibilityManager)

fun warehouse(
    accessibility: Accessibility = Accessibility.ISOLATED,
    accessibilityManager: AccessibilityManagerContract = AccessibilityManager(),
) = Warehouse(accessibility, accessibilityManager = accessibilityManager)

fun module(scope: Any? = null, block: Module.() -> Unit) = Module(scope).apply(block)

fun factory(
    contract: KClass<*>? = null,
    name: String? = null,
    creationPattern: CreationPattern = CreationPattern.NEW,
    vararg injectsIn: KClass<*>,
    block: FactoryBuilder.() -> Unit
) = FactoryBuilder(
    contract,
    name,
    creationPattern,
    if (injectsIn.isEmpty()) null else injectsIn.toMutableList()
).apply(block)
    .build()
