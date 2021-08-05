package org.raddad.main

typealias Constructor<T> = (Alpha) -> T
typealias MutableRegistry = MutableMap<DependencyMetadata, Factory>
typealias Registry = Map<DependencyMetadata, Factory>