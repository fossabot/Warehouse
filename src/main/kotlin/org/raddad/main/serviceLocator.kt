package org.raddad.main

import java.util.concurrent.ConcurrentHashMap


open class Alpha {

    val accessibility: Accessibility
    val scope: Any?
    private val accessibilityManager: AccessibilityManagerContract
    internal var dependencyRegistry: MutableRegistry = ConcurrentHashMap()

    constructor(
        scope: Any,
        accessibilityManager: AccessibilityManagerContract,
        constructorRegistry: MutableRegistry =
            ConcurrentHashMap(),
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
    inline fun <reified T> get(): T = get(DependencyMetadata(classType = T::class))
    inline fun <reified T> get(name: String): T = get(DependencyMetadata(className = name))
    inline fun <reified T> contains() = this.containsDeclaration(DependencyMetadata(T::class))


    @PublishedApi
    internal inline fun <reified T> get(key: DependencyMetadata): T = resolveInstance(this.getDependency(key))


    @PublishedApi
    internal fun getDependency(key: DependencyMetadata) = dependencyRegistry[key]
        ?: error("cannot get instance of ${key.classType}")

    @PublishedApi
    internal fun containsDeclaration(dependencyMetadata: DependencyMetadata) =
        dependencyRegistry.contains(dependencyMetadata)

    @PublishedApi
    internal inline fun <reified T> resolveInstance(value: Factory): T = value.resolveInstance(this)
    infix fun add(moduleFactory: ModuleFactory) = add(moduleFactory(this))


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