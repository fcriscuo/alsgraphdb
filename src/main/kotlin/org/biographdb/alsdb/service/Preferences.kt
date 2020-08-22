/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.service

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

object Preferences {
    val neo4j_password by EnvDelegate()
    val neo4j_user by EnvDelegate()
}
class EnvDelegate: ReadOnlyProperty<Preferences, String> {
    override fun getValue(thisRef: Preferences, property: KProperty<*>): String =
            System.getenv(property.name.toUpperCase()) ?: "undefined"
}