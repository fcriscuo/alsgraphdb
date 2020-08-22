/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.service

import org.biographdb.alsdb.domain.Evidence
import org.biographdb.alsdb.model.uniprot.Entry

/*
Represents a Map of Evidence objects for a specific UniProt entry
Supports the establishment of a relationship between an Evidence node and
client nodes (e.g. AlternativeName) that are supported by that evidence
The map must be cleared for each unique UniProt entry processed
This allows multiple client nodes within a UniProt entry to access the same Evidence node
 */
object EvidenceNodeMapService {

    private val evidenceMap: MutableMap<Int, Evidence> = mutableMapOf()

    fun saveEvidence(evidence: Evidence): Evidence? {
        if (evidence != null) {
            OgmDatabaseService.getSession().save(evidence)
        }
        return evidenceMap.putIfAbsent(evidence.key, evidence)
    }

    fun getEvidence(key: Int): Evidence = evidenceMap.getOrDefault(key, Evidence("",0, ""))

    fun containsEvidence(key: Int): Boolean = evidenceMap.contains(key)

    fun getAllEvidenceObjects() = evidenceMap.values

    fun retrieveEvidenceCollection(idList: List<Int>): List<Evidence> {
        var evidenceList: MutableList<Evidence> = mutableListOf()
        idList.stream()
            .filter { id -> containsEvidence(id) }
            .forEach { id -> evidenceList.add(getEvidence(id)) }
        return evidenceList
    }

    fun constructEvidenceMap(entry: Entry): Int {
        evidenceMap.clear()
        val uniprotId = entry?.getAccessionList()?.get(0) ?: ""
        entry.getEvidenceList()?.forEach { evidenceType ->
            saveEvidence(Evidence.createEvidenceObject(evidenceType, uniprotId))
        }
        return evidenceMap.size
    }

}