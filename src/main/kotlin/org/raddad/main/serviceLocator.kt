package org.raddad.main

import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass


open class Alpha {
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


    infix fun add(alpha: Alpha) =
        dependencyRegistry.putAll(accessibilityManager.resolveServiceLocatorAccess(this, alpha))

    infix fun add(module: Module) = dependencyRegistry.putAll(module.factoryRegistry)

    infix fun add(moduleFactory: ModuleFactory) = this.add(moduleFactory(this))

    fun inject() = injector
    fun getFactory(key: DependencyMetadata): Factory? = dependencyRegistry[key]
    fun containsDependency(dependencyMetadata: DependencyMetadata) = dependencyRegistry.containsKey(dependencyMetadata)
}

fun alpha(
    scope: Any,
    accessibilityManager: AccessibilityManagerContract = AccessibilityManager(),
    block: Alpha.() -> Unit,
) = Alpha(scope, accessibilityManager = accessibilityManager).apply(
    block
)

fun alpha(
    accessibility: Accessibility = Accessibility.ISOLATED,
    accessibilityManager: AccessibilityManagerContract = AccessibilityManager(),
    block: Alpha.() -> Unit,
) = Alpha(accessibility, accessibilityManager = accessibilityManager).apply(
    block
)

fun alpha(
    scope: Any,
    accessibilityManager: AccessibilityManagerContract = AccessibilityManager(),
) = Alpha(scope, accessibilityManager = accessibilityManager)

fun alpha(
    accessibility: Accessibility = Accessibility.ISOLATED,
    accessibilityManager: AccessibilityManagerContract = AccessibilityManager(),
) = Alpha(accessibility, accessibilityManager = accessibilityManager)