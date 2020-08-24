/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.Entry
import org.biographdb.alsdb.model.uniprot.ProteinType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import kotlin.contracts.ExperimentalContracts

/*
Represents a collection of alternative names for a protein
An AlternativeNameList is related to a single UniProt protein
 */
@NodeEntity(label = "AlternativeNameList")
class AlternativeNameList(@Id val uniprotId: String) {

    @Relationship(type = "HAS_ALTERNATIVENAME")
    var alternateNames: MutableList<AlternativeName> = mutableListOf()

    companion object {
        @ExperimentalContracts
        fun resolveAlternativeNameListFromEntry(entry: Entry): AlternativeNameList {
            var uniprotId = entry?.getAccessionList()?.get(0) ?: ""
            var altNameList = AlternativeNameList(uniprotId)
            entry.protein?.getAlternativeNameList()?.forEach { altName ->
                altNameList.alternateNames.add(AlternativeName.buildFromAlternativeNameType(altName))
            }
            return altNameList
        }
    }
}


