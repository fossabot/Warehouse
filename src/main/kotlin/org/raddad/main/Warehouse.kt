package org.raddad.main

import java.util.concurrent.ConcurrentHashMap


open class Warehouse {
    val accessibility: Accessibility
    val scope: Any?
    private val accessibilityManager: AccessibilityManagerContract
    private val injector: Injector by lazy { Injector(this) }

    @PublishedApi
    internal var dependencyRegistry: MutableRegistry = ConcurrentHashMap()

    constructor(
        scope: Any,
        accessibilityManager: AccessibilityManagerContract,
        constructorRegistry: MutableRegistry =
            ConcurrentHashMap()
    ) {
        this.accessibility = Accessibility.ISOLATED
        this.scope = scope
        this.dependencyRegistry = constructorRegistry
        this.accessibilityManager = accessibilityManager
    }

    constructor(
        accessibility: Accessibility,
        accessibilityManager: AccessibilityManagerContract,
        constructorRegistry: MutableRegistry =
            ConcurrentHashMap(),
    ) {
        this.accessibility = accessibility
        this.scope = null
        this.dependencyRegistry = constructorRegistry
        this.accessibilityManager = accessibilityManager
    }

    constructor(scope: Any) {
        this.accessibility = Accessibility.ISOLATED
        this.scope = scope
        this.accessibilityManager = AccessibilityManager()
    }

    constructor(accessibility: Accessibility) {
        this.accessibility = accessibility
        this.scope = null
        this.accessibilityManager = AccessibilityManager()
    }


    infix fun add(warehouse: Warehouse) =
        dependencyRegistry.putAll(accessibilityManager.resolveWarehouseAccess(this, warehouse))

    infix fun add(module: Module) = dependencyRegistry.putAll(module.factoryRegistry)

    fun inject() = injector
    fun getFactory(key: Metadata): Factory? = dependencyRegistry[key]
    fun containsDependency(metadata: Metadata) = dependencyRegistry.containsKey(metadata)
}