package org.raddad.main.test

import org.raddad.main.*

class NameModule : ModuleFactory {
    override operator fun invoke(alpha: Alpha): Module {
        return module {
            this add factory(contract = Name::class) {
                this constructor { FirstName() }
                this name "osa"
            }
            this add factory(contract = Name::class) {
                this constructor { FirstName() }
                this name "first"
                this type Type.SINGLETON
            }
            this add factory(contract = Name::class) {
                constructor { LastName() }
                this name "last"
            }
            this add factory {
                this constructor { FirstName() }
            }
            this add factory {
                constructor { LastName() }
            }
            this add factory {
                constructor { FullName(alpha.get(), alpha.get()) }
            }
        }
    }
}