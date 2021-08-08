package org.raddad.main

import org.raddad.main.Factory.FactoryBuilder
import kotlin.reflect.KClass

data class Factory(
    @PublishedApi
    internal val type: Type,
    @PublishedApi
    internal val contract: KClass<*>,
    @PublishedApi
    internal val name: String? = null,
    @PublishedApi
    internal val injectsIn: List<KClass<*>>? = null,
    @PublishedApi
    internal val constructor: Constructor<Any>,
) {

    @PublishedApi
    internal var instance: Any? = null


    @PublishedApi
    internal inline fun <reified T> resolveInstance(alpha: Alpha): T {
        val value = when (type) {
            Type.NEW -> constructor(alpha)
            Type.SINGLETON -> {
                if (instance == null) instance = constructor(alpha)
                instance
            }
        }
        return this.cast(value)
    }


    @PublishedApi
    internal inline fun <reified T : Any> cast(value: Any?): T {
        if (value == null) throw TypeCastException("the DI graph doesn't contain ${T::class.qualifiedName}")
        if (value !is T) throw TypeCastException("${value::class.qualifiedName} cannot be cast to ${T::class.qualifiedName}")
        return value
    }

    class FactoryBuilder(
        private var contractVal: KClass<*>? = null,
        private var nameVal: String? = null,
        private var typeVal: Type = Type.NEW,
        private var injectsInVal: MutableList<KClass<*>>?
    ) {

        @PublishedApi
        internal lateinit var constructor: Constructor<Any>

        @PublishedApi
        internal lateinit var tempType: KClass<*>


        infix fun type(type: Type) = apply {
            this.typeVal = type
        }

        infix fun contract(contract: KClass<*>) = apply {
            this.contractVal = contract
        }

        infix fun name(name: String) = apply {
            this.nameVal = name
        }

        infix fun injectsIn(injectsIn: MutableList<KClass<*>>) = apply {
            this.injectsInVal = injectsIn
        }

        infix fun injectsIn(injectsIn: KClass<*>) = apply {
            if (injectsInVal == null) {
                injectsInVal = mutableListOf(injectsIn)
            } else {
                injectsInVal?.add(injectsIn)
            }
        }

        inline infix fun <reified T : Any> constructor(noinline constructor: Constructor<T>) = apply {
            tempType = T::class
            this.constructor = constructor
        }


        fun build(): Factory {
            return Factory(typeVal, contractVal ?: tempType, nameVal, injectsInVal, constructor)
        }
    }
}

enum class Type {
    NEW, SINGLETON
}

fun factory(
    contract: KClass<*>? = null,
    name: String? = null,
    type: Type = Type.NEW,
    vararg injectsIn: KClass<*>,
    block: FactoryBuilder.() -> Unit
) = FactoryBuilder(contract, name, type, if (injectsIn.isEmpty()) null else injectsIn.toMutableList()).apply(block).build()