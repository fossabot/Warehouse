package org.raddad.main


fun main() {
    //todo RetentionPolicy for dep done
    //todo scoping modules
    //todo add inner test class to sl to access Registry
    //todo injects to
    val nameServiceLocator = alpha(Accessibility.LOCAL) {
        this add module {
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
                constructor { FullName(get(), get()) }
            }
        }
    }
    val userIDServiceLocator = alpha(Accessibility.Public) {
        this add module {
            this add factory {
                this constructor { 12 }
                this name "age"
            }
        }
    }
    val userServiceLocator = alpha(scope = Scope.CONSUMER) {
        this add nameServiceLocator
        this add userIDServiceLocator
    }

    val consumerServiceLocator = alpha(scope = Scope.CONSUMER) {
        this add userServiceLocator
        this add module {
            this add factory {
                constructor { Access.ADMIN }
            }
        }
    }

    val name0: Name = userServiceLocator.get("first")
    val name1: Name = userServiceLocator.get("first")
    val name2: Name = userServiceLocator.get("first")
    val nameLast3: Name = userServiceLocator.get("last")
    val nameLast4: Name = userServiceLocator.get("last")
    val osa: Name = userServiceLocator.get("osa")
    val fullName: FullName = userServiceLocator.get()
    val access: Access = consumerServiceLocator.get()
    val age: Int = consumerServiceLocator.get("age")

    assert(name0 == name1 && name1 == name2, "name0 to name2 should be the same instance")
    assert(name0 != osa, "name0 shouldn't be the same instance as osa")
    assert(nameLast3 != nameLast4, "name0 shouldn't be the same instance as osa")

    println(name0)
    println(name1)
    println(name2)
    println("===========")
    println(nameLast3)
    println(nameLast4)
    println("===========")
    println(osa.value())
    println(access)
    println(age)
    println(name0.value())
    println(fullName.value())

}

fun assert(boolean: Boolean, message: String) {
    if (!boolean) println(message)
}


enum class Access {
    ADMIN, USER
}

enum class Scope {
    CONSUMER
}

interface Name {
    fun value(): String
}

internal class FirstName : Name {
    override fun value() = "Osama"
}

internal class LastName : Name {
    override fun value() = "Raddad"

}

class FullName internal constructor(private var firstName: FirstName, private var lastName: LastName) : Name {
    override fun value() = firstName.value() + " " + lastName.value()
}




