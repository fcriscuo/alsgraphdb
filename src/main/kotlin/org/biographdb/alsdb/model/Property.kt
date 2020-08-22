/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.model

import org.biographdb.alsdb.model.uniprot.PropertyType
import kotlin.contracts.contract

/**
 * Created by fcriscuo on 4/24/20.
 */
@kotlin.contracts.ExperimentalContracts
data class Property(val type: String, val value: String, val valid: Boolean = false) {
    companion object {
        fun parseXmlPropertyType(prop: PropertyType): Property {
            try {
                prop.assertValidProperty()
                return Property(prop.type!!, prop.value!!, true)
            } catch (e: Exception) {
                println("ERROR: ${e.message}")
            }
            return Property(" ", " ")
        }
    }
}

// Contract to assure that the PropertyType is valid
//@OptIn(kotlin.contracts.ExperimentalContracts::class)
@kotlin.contracts.ExperimentalContracts
fun PropertyType.assertValidProperty() {
    contract {
        returns() implies (this@assertValidProperty != null)
    }
    if (this == null || this.type == null || this.value == null) {
        throw Exception("Invalid PropertyType instance")
    }
}
