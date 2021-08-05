package org.raddad.main

interface AccessibilityManagerContract {
    fun resolveServiceLocatorAccess(
        myAlpha: Alpha,
        hisAlpha: Alpha
    ): Map<out DependencyMetadata, Factory>

}