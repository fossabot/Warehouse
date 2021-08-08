package org.raddad.main

import org.raddad.main.test.FullName
import org.raddad.main.test.MainAlpha
import org.raddad.main.test.Name

class Main {
    companion object {

        private val mainAlpha = MainAlpha()

        private val fullName: FullName by mainAlpha.userServiceLocator.inject()
        private val access: Access by mainAlpha.consumerServiceLocator.inject()

        @Named("age")
        private val age: Int by mainAlpha.userServiceLocator.inject()

        @JvmStatic
        fun main(args: Array<String>) {
            //todo add inner test class to sl to access Registry

            println(access)
            println(age)
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



