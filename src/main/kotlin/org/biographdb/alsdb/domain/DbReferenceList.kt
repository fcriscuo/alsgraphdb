/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.CitationType
import org.biographdb.alsdb.model.uniprot.Entry
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship


@NodeEntity(label = "DbReferenceList")
data class DbReferenceList(@Id val parentId: String) {

    @Relationship(type = "HAS_DBREFERENCE")
    var dbReferences: MutableList<DbReference> = mutableListOf()

    // ensure that a valid parent identifier has been supplied
    fun isValid(): Boolean = parentId.isNotEmpty()

    companion object {
        fun resolveDbReferenceFromCitationType(citationType: CitationType, parentId: String): DbReferenceList {
            var dbRefList = DbReferenceList(parentId)
            citationType.dbReference?.forEach { refType ->
                dbRefList.dbReferences.add(DbReference.createDbReference(refType))
            }
            return dbRefList
        }


        fun resolveDbReferenceListFromEntry(entry: Entry): DbReferenceList {
            // parent id is the uniprot id
            val uniprotId = entry?.getAccessionList()?.get(0) ?: ""
            var dbRefList = DbReferenceList(uniprotId)
            entry.getDbReferenceList()?.forEach { refType ->
                dbRefList.dbReferences.add(DbReference.createDbReference(refType))
            }
            return dbRefList
        }
    }
}