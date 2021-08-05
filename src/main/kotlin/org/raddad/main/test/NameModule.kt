package org.raddad.main.test

import org.raddad.main.*

class NameModule : ModuleFactory {
    override operator fun invoke(alpha: Alpha): Module {
        return module {
            this add FirstNameFactory(contract = Name::class,"osa")
            this add FirstNameFactory(contract = Name::class, name = "first", type = Type.SINGLETON)
            this add LastNameFactory(contract = Name::class, name = "last")
            this add FirstNameFactory()
            this add LastNameFactory()
            this add factory {
                constructor { FullName(alpha.get(), alpha.get()) }
            }
        }
    }
}