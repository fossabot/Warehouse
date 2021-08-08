package org.raddad.main.test

class FullName internal constructor(private var firstName: FirstName, private var lastName: LastName) : Name {
    override fun value() =
        firstName.value() + " " + lastName.value()
}