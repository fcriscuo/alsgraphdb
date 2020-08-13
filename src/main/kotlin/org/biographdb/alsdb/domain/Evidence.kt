package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.EvidenceType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.util.*

/*
Represents an Evidence object
Individual Evidence nodes are only reference by a single parent Node
 */
@NodeEntity(label = "Evidence")
data class Evidence(val key: Int, val type: String = "") {
    @Id
    val uuid = UUID.randomUUID().toString()

    @Relationship(type = "HAS_DBREFERENCE")
    var dbReferences: MutableList<DbReference> = mutableListOf()

    companion object {
        fun createEvidenceObject(evidenceType: EvidenceType): Evidence {
            val dbRef = DbReference.createDbReference(evidenceType.source?.dbReference)
            val key = evidenceType.key?.intValueExact() ?: 0
            val type = evidenceType.type ?: ""
            val evidence = Evidence(key, type)
            if (dbRef != null) {
                evidence.dbReferences.add(dbRef)
            }
            return evidence
        }
    }
}