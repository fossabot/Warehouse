package org.raddad.main

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.*

class EndToEndFactoryTest {
    private val alpha = alpha()

    private fun addFactory(factory: Factory) {
        alpha.add(module { this add factory })
    }


    @Test
    fun `Test add dependency`() {
        val fakeDependency = "fakeDependency1"
        addFactory(factory {
            this constructor { fakeDependency }
        })

        Assertions.assertTrue(alpha.contains<String>(), "fail to add dependency")
    }

    @Test
    fun `Test retrieve dependency`() {
        val fakeDependency = "fakeDependency"
        addFactory(factory {
            this constructor { fakeDependency }
        })

        Assertions.assertEquals(fakeDependency, alpha.get<String>(), "fail to retrieve dependency")
    }

    @Test
    fun `Test retrieve newInstance dependency`() {
        val fakeDependency = "fakeDependency"

        data class Test(var a: String)
        addFactory(factory {
            constructor { fakeDependency }
        })
        addFactory(factory {
            constructor { Test(alpha.get()) }
        })

        val testA: Test = alpha.get()
        val testB: Test = alpha.get()

        Assertions.assertFalse(testA === testB, "fail to retrieve newInstance dependency")
    }

    @Test
    fun `Test retrieve SINGLETON (declared in builder constructor) dependency`() {
        val fakeDependency = "fakeDependency"

        data class Test(var a: String)

        addFactory(factory {
            constructor { fakeDependency }
        })
        addFactory(factory(type = Type.SINGLETON) {
            constructor { Test(alpha.get()) }
        })
        val testA: Test = alpha.get()
        val testB: Test = alpha.get()

        Assertions.assertTrue(testA === testB, "fail to retrieve SINGLETON dependency")
    }

    @Test
    fun `Test retrieve SINGLETON (declared after builder constructor) dependency`() {
        val fakeDependency = "fakeDependency"

        data class Test(var a: String)

        addFactory(factory {
            constructor { fakeDependency }
        })
        addFactory(factory {
            constructor { Test(alpha.get()) }
            this type Type.SINGLETON
        })

        val testA: Test = alpha.get()
        val testB: Test = alpha.get()

        Assertions.assertTrue(testA === testB, "fail to retrieve SINGLETON dependency")
    }

    @Test
    fun `Test retrieve contract Instance (declared in builder constructor) dependency`() {
        val fakeDependency = "fakeDependency"


        data class Test(var a: String) : TestContract

        addFactory(factory {
            constructor { fakeDependency }
        })
        addFactory(factory(contract = TestContract::class) {
            constructor { Test(alpha.get()) }
        })

        try {
            alpha.get<TestContract>()
        } catch (e: TypeCastException) {
            fail("fail to retrieve contract Instance dependency",e)
        }
    }

    @Test
    fun `Test retrieve contract Instance (declared after builder constructor) dependency`() {
        val fakeDependency = "fakeDependency"

        data class Test(var a: String) : TestContract

        addFactory(factory {
            constructor { fakeDependency }
        })
        addFactory(factory {
            constructor { Test(alpha.get()) }
            this contract TestContract::class
        })

        try {
            alpha.get<TestContract>()
        } catch (e: TypeCastException) {
            fail("fail to retrieve contract Instance dependency",e)
        }
    }


    @Test
    fun `Test retrieve named dependency (declared in builder constructor)`() {
        val name1 = "1"
        val name2 = "2"
        val fakeDependency1 = "fakeDependency1"
        val fakeDependency2 = "fakeDependency2"

        addFactory(factory(name = name1) {
            constructor { fakeDependency1 }
        })
        addFactory(factory(name = name2) {
            constructor { fakeDependency2 }
        })

        val test1: String = alpha.get(name1)
        val test2: String = alpha.get(name2)

        Assertions.assertEquals(fakeDependency1, test1, "fail to retrieve named dependency")
        Assertions.assertEquals(fakeDependency2, test2, "fail to retrieve named dependency")
        Assertions.assertNotEquals(test1, test2, "fail to retrieve named dependency")
    }

    @Test
    fun `Test retrieve named dependency (declared after builder constructor) dependency`() {
        val name1 = "1"
        val name2 = "2"
        val fakeDependency1 = "fakeDependency1"
        val fakeDependency2 = "fakeDependency2"

        addFactory(factory {
            constructor { fakeDependency1 }
            this name name1
        })
        addFactory(factory {
            constructor { fakeDependency2 }
            this name name2
        })

        val test1: String = alpha.get(name1)
        val test2: String = alpha.get(name2)

        Assertions.assertEquals(fakeDependency1, test1, "fail to retrieve named dependency")
        Assertions.assertEquals(fakeDependency2, test2, "fail to retrieve named dependency")
        Assertions.assertNotEquals(test1, test2, "fail to retrieve named dependency")
    }
}

interface TestContract