package org.raddad.main

import kotlin.reflect.KClass

data class Factory(
    @PublishedApi
    internal val creationPattern: CreationPattern,
    @PublishedApi
    internal val contract: KClass<*>,
    @PublishedApi
    internal val name: String? = null,
    @PublishedApi
    internal val injectsIn: List<KClass<*>>? = null,
    @PublishedApi
    internal val constructor: Constructor<*>,
) {

    @PublishedApi
    internal var instance: Any? = null


    @PublishedApi
    internal inline fun <reified T> resolveInstance(warehouse: Warehouse): T {
        val value = when (creationPattern) {
            CreationPattern.NEW -> constructor(warehouse)
            CreationPattern.SINGLETON -> {
                if (instance == null) instance = constructor(warehouse)
                instance
            }
        }
        return this.cast(value)
    }


    @PublishedApi
    internal inline fun <reified T : Any> cast(value: Any?): T {
        if (value == null) throw TypeCastException(
            "the DI graph doesn't contain ${T::class.qualifiedName}"
        )
        if (value !is T) throw TypeCastException(
            "${value::class.qualifiedName} cannot be cast to ${T::class.qualifiedName}"
        )
        return value
    }


}
