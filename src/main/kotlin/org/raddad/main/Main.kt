package org.raddad.main

import org.raddad.main.test.FullName
import org.raddad.main.test.MainAlpha
import org.raddad.main.test.Name

class Main {
    companion object {

        val mainAlpha = MainAlpha()

        val name0: Name = mainAlpha.userServiceLocator.inject().get("first")
        val name1: Name = mainAlpha.userServiceLocator.inject().get("first")
        val name2: Name = mainAlpha.userServiceLocator.inject().get("first")
        val nameLast3: Name = mainAlpha.userServiceLocator.inject().get("last")
        val nameLast4: Name = mainAlpha.userServiceLocator.inject().get("last")

        val osa: Name = mainAlpha.userServiceLocator.inject().get("osa")
        val fullName: FullName by mainAlpha.userServiceLocator.inject()
        val access: Access = mainAlpha.consumerServiceLocator.inject().get()
        val age: Int = mainAlpha.consumerServiceLocator.inject().get("age")

        @JvmStatic
        fun main(args: Array<String>) {
            //todo RetentionPolicy for dep done
            //todo scoping modules not needed
            //todo add inner test class to sl to access Registry
            //todo injects to


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

    }
}



