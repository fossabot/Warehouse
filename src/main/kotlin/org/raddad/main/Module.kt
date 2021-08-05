package org.raddad.main

import java.util.concurrent.ConcurrentHashMap

open class Module(private var scope: Any? = null) {
    @PublishedApi
    internal val factoryRegistry: MutableRegistry = ConcurrentHashMap()


    infix fun add(factory: Factory) {
        factoryRegistry[DependencyMetadata(factory.contract, factory.name, scope)] = factory
    }

    infix fun add(factory: () -> Factory) {
        add(factory())
    }
//
//    infix fun add(scope: Any) {
//        this.scope = scope
//    }

}

fun module(scope: Any? = null, block: Module.() -> Unit) = Module(scope).apply(block)

