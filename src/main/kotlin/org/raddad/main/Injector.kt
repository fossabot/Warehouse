package org.raddad.main

import kotlin.reflect.KProperty


class Injector(
    @PublishedApi
    internal val alpha: Alpha
) {

    inline fun <reified T> get(): T = get(DependencyMetadata(classType = T::class))
    inline fun <reified T> get(name: String): T = get(DependencyMetadata(className = name))
    inline fun <reified T> contains() = this.containsDependency(DependencyMetadata(T::class))


    @PublishedApi
    internal inline fun <reified T> get(key: DependencyMetadata): T = resolveInstance(this.getDependency(key))


    @PublishedApi
    internal fun getDependency(key: DependencyMetadata) = alpha.getFactory(key)
        ?: error("cannot get instance of ${key.classType}")

    @PublishedApi
    internal fun containsDependency(dependencyMetadata: DependencyMetadata): Boolean =
        alpha.containsDependency(dependencyMetadata)


    @PublishedApi
    internal inline fun <reified T> resolveInstance(value: Factory): T = value.resolveInstance(alpha)

    inline operator fun <reified T, reified K> getValue(target: K, property: KProperty<*>): T {
        val key = DependencyMetadata(classType = T::class)
        val factory = alpha.getFactory(key) ?: error("cannot get instance of ${key.classType}")
        return if (factory.injectsIn?.contains(K::class) == true)
            resolveInstance(factory)
        else if (factory.injectsIn == null)
            resolveInstance(factory)
        else error("class ${T::class.simpleName} can't be injected in ${K::class.simpleName} unless you declaratively say so")
    }
}
