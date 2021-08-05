package org.raddad.main.test

import org.raddad.main.Factory
import org.raddad.main.Type
import org.raddad.main.factory
import kotlin.reflect.KClass

class FirstNameFactory(
    private val contract: KClass<*>?=null,
    private val name: String?=null,
    private val type: Type = Type.NEW
) : () -> Factory {
    override fun invoke() =
        factory(contract, name, type) {
            this constructor { FirstName() }
        }
}