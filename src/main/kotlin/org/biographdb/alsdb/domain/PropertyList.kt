package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.DbReferenceType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity(label = "PropertyList")
class PropertyList(@Id val dbRefId: String) {

    @Relationship(type = "HAS_PROPERTY")
    var properties: MutableList<Property> = mutableListOf()

    companion object {
        fun resolvePropertyListFromDbReferenceType(dbRef: DbReferenceType?): PropertyList {
            val dbRefId = dbRef?.id ?: " "
            var propList = PropertyList(dbRefId)
            dbRef?.getPropertyList()?.forEach { prop ->
                propList.properties.add(Property.createPropertyFromPropertyType(prop))
            }
            return propList
        }
    }
}