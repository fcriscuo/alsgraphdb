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

/*
Represents a collection of alternative names for a protein
An AlternativeNameList is related to a single UniProt protein
 */
@NodeEntity(label = "AlternativeNameList")
class AlternativeNameList(@Id val uniprotId: String) {

    @Relationship(type = "HAS_ALTERNATIVENAME")
    var alternateNames: MutableList<AlternativeName> = mutableListOf()

    companion object {
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

/*
Represents an alternative name for an entity
An AlternativeName may have a relationship to >= Evidence nodes
An individual AlternativeName node my be referenced by multiple nodes (i.e. many to one)
 */
@NodeEntity(label = "AlternativeName")
data class AlternativeName(@Id val fullName: String, val shortName: String = "") : EvidenceSupported() {

    fun isValid(): Boolean = fullName.isNotEmpty()

    companion object {
        fun buildFromAlternativeNameType(altName: ProteinType.AlternativeName): AlternativeName {
            val fullName: String = altName.fullName?.value ?: ""
            var shortName = ""
            if (!altName.getShortNameList().isNullOrEmpty())
                shortName = altName.getShortNameList()?.get(0)?.value ?: ""
            val alternativeName = AlternativeName(fullName, shortName)
            alternativeName.evidenceList = EvidenceList.buildFromIds(altName.fullName?.getEvidenceList())
            return alternativeName
        }
    }

}