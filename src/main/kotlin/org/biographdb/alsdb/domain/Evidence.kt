/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.EvidenceType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.util.*

/*
Represents an Evidence object
Individual Evidence nodes are only referenced from a single parent Node
The combination of UniProtId and evidence index number will be unique across
the entire database
 */
@NodeEntity(label = "Evidence")
data class Evidence(@Id val evidenceId: String, val key: Int, val type: String = "") {

    @Relationship(type = "HAS_DBREFERENCE")
    var dbReferences: MutableList<DbReference> = mutableListOf()

    companion object {
        fun createEvidenceObject(evidenceType: EvidenceType, uniprotId: String): Evidence {
            val dbRef = DbReference.createDbReference(evidenceType.source?.dbReference)
            val key = evidenceType.key?.intValueExact() ?: 0
            val evidenceId = uniprotId.plus(":").plus(key.toString())
            val type = evidenceType.type ?: ""
            val evidence = Evidence(evidenceId, key, type)
            if (dbRef != null) {
                evidence.dbReferences.add(dbRef)
            }
            return evidence
        }
    }
}