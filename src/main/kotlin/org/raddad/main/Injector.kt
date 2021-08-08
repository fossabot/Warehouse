package org.raddad.main

import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.findAnnotation


class Injector(
    @PublishedApi
    internal val alpha: Alpha
) {
    @PublishedApi
    internal inline fun <reified T> resolveInstance(value: Factory): T = value.resolveInstance(alpha)

    inline fun <reified T> get(dependency: KClass<*>, target: KClass<*>): T {
        val key = DependencyMetadata(classType = dependency)
        val factory = alpha.getFactory(key) ?: error("${key.classType ?: key.className} doesn't exist in the graph")
        return resolveAccessibility(factory, target = target)
    }

    @PublishedApi
    internal inline fun <reified T> resolveAccessibility(
        factory: Factory,
        target: KClass<*>
    ): T = if (factory.injectsIn?.contains(target) == true)
        resolveInstance(factory)
    else if (factory.injectsIn == null)
        resolveInstance(factory)
    else error("class ${T::class.simpleName} can't be injected in ${target.simpleName} unless you declaratively say so")

    @PublishedApi
    internal inline fun <reified T> get(name: String): T {
        return resolve(DependencyMetadata(className = name))
    }

    inline operator fun <reified T, reified K> getValue(target: K, property: KProperty<*>): T {
        val name = property.findAnnotation<Named>()?.name

        return if (name != null)
            resolveTarget<K, T>(DependencyMetadata(className = name))
        else
            resolveTarget<K, T>(DependencyMetadata(classType = T::class))
    }

    @PublishedApi
    internal inline fun <reified T> resolve(key: DependencyMetadata): T = resolveInstance(this.getDependency(key))

    @PublishedApi
    internal inline fun <reified K, reified T> resolveTarget(key: DependencyMetadata): T {
        val factory = alpha.getFactory(key) ?: error("${key.classType ?: key.className} doesn't exist in the graph")
        return resolveAccessibility(factory, K::class)
    }

    @PublishedApi
    internal inline fun <reified T> contains() = this.containsDependency(DependencyMetadata(T::class))

    @PublishedApi
    internal fun getDependency(key: DependencyMetadata) = alpha.getFactory(key)
        ?: error("cannot get instance of ${key.classType}")

    @PublishedApi
    internal fun containsDependency(dependencyMetadata: DependencyMetadata): Boolean =
        alpha.containsDependency(dependencyMetadata)

}

annotation class Named(val name: String)
