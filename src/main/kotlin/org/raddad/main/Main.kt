package org.raddad.main

import org.raddad.main.test.FullName
import org.raddad.main.test.MainAlpha
import org.raddad.main.test.Name

class Main {
    companion object {

        private val mainAlpha = MainAlpha()

        private val name0: Name = mainAlpha.userServiceLocator.inject().get("first")
        private val name1: Name = mainAlpha.userServiceLocator.inject().get("first")
        private val name2: Name = mainAlpha.userServiceLocator.inject().get("first")
        private val nameLast3: Name = mainAlpha.userServiceLocator.inject().get("last")
        private val nameLast4: Name = mainAlpha.userServiceLocator.inject().get("last")

        private val osa: Name = mainAlpha.userServiceLocator.inject().get("osa")
        private val fullName: FullName by mainAlpha.userServiceLocator.inject()
        private val access: Access = mainAlpha.consumerServiceLocator.inject().get()
        private val age: Int = mainAlpha.consumerServiceLocator.inject().get("age")

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

        private fun assert(boolean: Boolean, message: String) {
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



