package org.raddad.main

import kotlin.reflect.KClass

data class Metadata(
    val classType: KClass<*>? = null,
    val className: String? = null,
    var scope: Any? = null,
    val isClosed: Boolean = false
) {
    override fun hashCode() = getHashCode()

    override fun equals(other: Any?): Boolean = getHashCode() == other.hashCode()

    private fun getHashCode() = className?.hashCode() ?: classType.hashCode()

}