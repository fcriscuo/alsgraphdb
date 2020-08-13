package org.biographdb.alsdb.domain


import org.biographdb.alsdb.model.uniprot.PropertyType
import org.neo4j.graphalgo.GraphAlgoFactory
import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity

@NodeEntity(label = "Property")
class Property(val type: String = "", val value: String = "") {
    // @Id val uuid = UUID.randomUUID().toString()
    @Id
    val propertyId = type.plus(":").plus(value)

    companion object {
        fun createPropertyFromPropertyType(propertyType: PropertyType): Property {
            val type = propertyType.type ?: ""
            val value = propertyType.value ?: ""
            return Property(type, value)
        }

    }
}