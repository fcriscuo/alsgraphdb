package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.DbReferenceType
import org.biographdb.alsdb.model.uniprot.EvidenceType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.util.*

@NodeEntity(label = "DbReference")
data class DbReference(val type: String, val id: String) {
    @Id
    val uuid = UUID.randomUUID().toString()

    @Relationship(type = "HAS_PROPERTY_LIST")
    lateinit var propertyList: PropertyList

    fun isValid() = type.isNotEmpty() && id.isNotEmpty()

    companion object {
        fun createDbReference(dbReferenceType: DbReferenceType?): DbReference {
            val type: String = dbReferenceType?.type ?: ""
            val id: String = dbReferenceType?.id ?: ""
            val dbRef = DbReference(type, id)
            // some dbReference entries have properties
            if (dbRef.isValid() && dbReferenceType?.getPropertyList()?.size ?: 0 > 0)
                dbRef.propertyList = PropertyList.resolvePropertyListFromDbReferenceType(dbReferenceType)
            return dbRef
        }

        fun createDbReferenceFromEvidenceType(evidenceType: EvidenceType): DbReference =
            createDbReference(evidenceType.source?.dbReference)

    }
}