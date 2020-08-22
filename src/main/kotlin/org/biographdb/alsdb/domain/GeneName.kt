/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.Entry
import org.biographdb.alsdb.model.uniprot.GeneNameType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.util.*

@NodeEntity(label = "GeneNameList")
class GeneNameList(@Id val uniprotId: String) {
    @Relationship(type = "HAS_GENE_NAME")
    var geneNames: MutableList<GeneName> = mutableListOf()

    companion object {
        fun resolveGeneNameListFromEntry(entry: Entry): GeneNameList {
            var uniprotId = entry?.getAccessionList()?.get(0) ?: ""
            var geneNameList = GeneNameList(uniprotId)
            entry.getGeneList()?.map { geneType ->
                geneType.getNameList()
            }
                ?.forEach { gnl ->
                    gnl?.forEach { gnt ->
                        geneNameList.geneNames.add(GeneName.resolveGeneNameFromGeneNameType(gnt))
                    }
                }
            return geneNameList
        }
    }
}

data class GeneName(val geneName: String, val nameType: String) : EvidenceSupported() {
    @Id
    val uuid = UUID.randomUUID().toString()

    fun isValid(): Boolean = geneName.isNotEmpty() && nameType.isNotEmpty()

    companion object {
        fun resolveGeneNameFromGeneNameType(gnt: GeneNameType): GeneName {
            val name = gnt.value ?: ""
            val type = gnt.type ?: ""
            val geneName = GeneName(name, type)
            val evidenceBuildResult = EvidenceList.buildFromIds(gnt.getEvidenceList())
            if (evidenceBuildResult.isRight()) {
                geneName.evidenceList = evidenceBuildResult.orNull()!!
            }
            return geneName

        }
    }

}