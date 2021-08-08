package org.raddad.main

import org.raddad.main.test.FullName
import org.raddad.main.test.WarehousesContaner

class Main {
    companion object {

        private val warehousesContainer = WarehousesContaner()

        private val fullName: FullName by warehousesContainer.userWarehouse.inject()
        private val access: Access by warehousesContainer.consumerWarehouse.inject()

        @Named("age")
        private val age: Int by warehousesContainer.userWarehouse.inject()

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



