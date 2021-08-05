package org.raddad.main

import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.findAnnotation


class Injector(
    @PublishedApi
    internal val warehouse: Warehouse
) {


    @PublishedApi
    internal inline fun <reified T> get(): T {
        val key = Metadata(classType = T::class)
        return resolve(key)
    }


    @PublishedApi
    internal inline fun <reified T> get(dependency: KClass<*>, target: KClass<*>): T {
        val key = Metadata(classType = dependency)
        val factory = warehouse.getFactory(key) ?: error("${key.classType ?: key.className} doesn't exist in the graph")
        return resolveAccessibility(factory, target = target)
    }

    @PublishedApi
    internal inline fun <reified T> get(name: String, target: KClass<*>): T {
        val key = Metadata(className = name)
        val factory = warehouse.getFactory(key) ?: error("${key.classType ?: key.className} doesn't exist in the graph")
        return resolveAccessibility(factory, target = target)
    }

    @PublishedApi
    internal inline fun <reified T> resolveAccessibility(
        factory: Factory,
        target: KClass<*>
    ): T = if (factory.injectsIn?.contains(target) == true)
        resolveInstance(factory)
    else if (factory.injectsIn == null || factory.injectsIn.isEmpty())
        resolveInstance(factory)
    else error("class ${T::class.simpleName} can't be injected in ${target.simpleName} unless you declaratively say so")

    @PublishedApi
    internal inline fun <reified T> get(name: String): T {
        return resolve(Metadata(className = name))
    }

    inline operator fun <reified T, reified K> getValue(target: K, property: KProperty<*>): T {
        val name = property.findAnnotation<Named>()?.name

        return if (name != null)
            resolveTarget<K, T>(Metadata(className = name))
        else
            resolveTarget<K, T>(Metadata(classType = T::class))
    }

    @PublishedApi
    internal inline fun <reified T> resolve(key: Metadata): T = resolveInstance(this.getDependency(key))

    @PublishedApi
    internal inline fun <reified T> resolveInstance(value: Factory): T = value.resolveInstance(warehouse)

    @PublishedApi
    internal inline fun <reified K, reified T> resolveTarget(key: Metadata): T {
        val factory = warehouse.getFactory(key) ?: error("${key.classType ?: key.className} doesn't exist in the graph")
        return resolveAccessibility(factory, K::class)
    }

    @PublishedApi
    internal inline fun <reified T> contains() = this.containsDependency(Metadata(T::class))

    @PublishedApi
    internal fun getDependency(key: Metadata) = warehouse.getFactory(key)
        ?: error("cannot get instance of ${key.classType}")

    @PublishedApi
    internal fun containsDependency(metadata: Metadata): Boolean =
        warehouse.containsDependency(metadata)

}
