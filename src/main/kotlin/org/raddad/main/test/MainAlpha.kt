package org.raddad.main.test

import org.raddad.main.*

class MainAlpha {
    private val nameServiceLocator = alpha(Accessibility.LOCAL) {
        this add module {

            this add factory {
                constructor { FirstName() }
                this injectsIn FullName::class
                this type Type.SINGLETON
            }
            this add factory {
                constructor { LastName() }
                this injectsIn FullName::class
                this type Type.SINGLETON
            }
            this add factory {
                constructor {
                    val firstName: FirstName = param()
                    val lastName: LastName = param()
                    FullName(firstName, lastName)
                }
            }
        }
    }

    private val userIDServiceLocator = alpha(Accessibility.OPEN) {
        this add module {
            this add factory {
                this constructor { 12 }
                this name "age"
                this type Type.SINGLETON
            }
        }
    }
    val userServiceLocator = alpha(scope = Main.Companion.Scope.CONSUMER) {
        this add nameServiceLocator
        this add userIDServiceLocator
    }

    val consumerServiceLocator = alpha(scope = Main.Companion.Scope.CONSUMER) {
        this add userServiceLocator
        this add module {
            this add factory {
                constructor { Main.Companion.Access.ADMIN }
            }
        }
    }
}