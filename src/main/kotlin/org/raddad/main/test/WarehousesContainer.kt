package org.raddad.main.test

import org.raddad.main.*
import org.raddad.main.Accessibility.LOCAL
import org.raddad.main.Accessibility.OPEN
import org.raddad.main.CreationPattern.SINGLETON
import org.raddad.main.Main.Companion.Access.ADMIN
import org.raddad.main.Main.Companion.Scope.CONSUMER

class WarehousesContainer {
    private val nameWarehouse = warehouse(LOCAL) {
        this add module {
            this add factory {
                constructor { FirstName() }
                this injectsIn FullName::class
                this creation SINGLETON
            }
            this add factory {
                constructor { LastName() }
                this injectsIn FullName::class
                this creation SINGLETON
            }

            this add factory {
                constructor {
                    FullName(param(), param())
                }
            }
        }
    }

    private val userIDWarehouse = warehouse(OPEN) {
        this add module {
            this add factory {
                this constructor { 12 }
                this name "age"
                this creation SINGLETON
            }
        }
    }
    val userWarehouse = warehouse(scope = CONSUMER) {
        this add nameWarehouse
        this add userIDWarehouse
    }

    val consumerWarehouse = warehouse(scope = CONSUMER) {
        this add userWarehouse
        this add module {
            this add factory {
                constructor { ADMIN }
            }
        }
    }
}