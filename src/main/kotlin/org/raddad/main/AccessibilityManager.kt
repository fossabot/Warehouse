package org.raddad.main

import kotlin.reflect.KVisibility

open class AccessibilityManager :
    AccessibilityManagerContract {


    override fun resolveServiceLocatorAccess(
        myAlpha: Alpha,
        hisAlpha: Alpha
    ): Registry {
        return when {
            isNotScope(hisAlpha) -> resolveAccessibility(
                myAlpha,
                hisAlpha
            )
            hasSameScope(myAlpha, hisAlpha) -> getPublicDeclarations(
                hisAlpha
            )

            else -> error("failed to add ${hisAlpha::class.java.name}#(SCOPED) to ${myAlpha::class.java.name} miss matched scope")
        }
    }

    private fun resolveAccessibility(
        myAlpha: Alpha,
        hisAlpha: Alpha
    ): Registry =
        when (hisAlpha.accessibility) {
            Accessibility.ISOLATED -> error("failed to add ${hisAlpha::class.java.name}#(PRIVATE) to ${myAlpha::class.java.name} ")
            Accessibility.OPEN -> getPublicDeclarations(hisAlpha)
            Accessibility.LOCAL -> getPublicDeclarations(hisAlpha).mapKeys {
                it.key.copy(isClosed = true)
            }
        }

    private fun isNotScope(hisAlpha: Alpha) = hisAlpha.scope == null


    private fun hasSameScope(myAlpha: Alpha, hisAlpha: Alpha) =
        hisAlpha.scope == myAlpha.scope

    private fun getPublicDeclarations(alpha: Alpha) =
        alpha.dependencyRegistry
            .filter(::isVisibleDeclaration)

    private fun isVisibleDeclaration(declaration: Map.Entry<DependencyMetadata, Factory>): Boolean {
        return declaration.key.classType?.visibility == KVisibility.PUBLIC && !declaration.key.isClosed
    }


}