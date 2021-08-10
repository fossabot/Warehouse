package org.raddad.main

typealias Constructor<T> = (Warehouse) -> T
typealias MutableRegistry = MutableMap<Metadata, Factory>
typealias Registry = Map<Metadata, Factory>
