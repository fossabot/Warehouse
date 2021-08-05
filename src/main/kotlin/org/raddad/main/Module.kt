package org.raddad.main

import java.util.concurrent.ConcurrentHashMap

open class Module(private var scope: Any? = null) {
    @PublishedApi
    internal val factoryRegistry: MutableRegistry = ConcurrentHashMap()


    infix fun add(factory: Factory) {
        factoryRegistry[Metadata(factory.contract, factory.name, scope)] = factory
    }
}
