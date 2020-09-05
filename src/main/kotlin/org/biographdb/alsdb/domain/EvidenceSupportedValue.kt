/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.AlsdbModel
import org.biographdb.alsdb.model.uniprot.EvidencedStringType
import org.biographdb.alsdb.service.EvidenceNodeMapService
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.Labels
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/*
Represents an attribute whose value is supported by one or more Evidence nodes
Roughly equivalent to the JAXB generated EvidencedStringType
It is expected that all EvidenceSupportedValue nodes will be
assigned an additional, more definitive, label (e.g. Alternative Name")
 */
@NodeEntity(label = "EvidenceSupportedValue")
class EvidenceSupportedValue(val text: String = "") {
    @Id
    val uuid = UUID.randomUUID().toString()

    @Relationship(type = "HAS_EVIDENCE")
    var evidence: MutableList<Evidence> = mutableListOf()

    @Labels
    private val labels: MutableList<String> = mutableListOf()

    fun addLabel( label: String) {
        if (!labels.contains(label)) {
            labels.add(label)
        }
    }

    companion object : AlsdbModel {
        /*
        Completes a List of Evidence objects based on a supplied List of Evidence identifiers
        allows a single Evidence Node to be reference by multiple Nodes (i.e. many to one relationship)
         */
        fun resolveEvidenceListFromEvidenceIdentifiers(evidenceIds: List<Int>): MutableList<Evidence> {
            var evidenceList = mutableListOf<Evidence>()
            // split the String of evidence ids by spaces and
            // look up the appropriate Evidence node in the database
            evidenceIds.stream()
                    .filter { key -> EvidenceNodeMapService.containsEvidence(key) }
                    .forEach { key -> evidenceList.add(EvidenceNodeMapService.getEvidence(key)) }
            return evidenceList
        }

        /*
        Builds an EvidenceSupportedValue object
         */
        @ExperimentalContracts
        fun buildFromEvidenceStringType(est: EvidencedStringType):
                 EvidenceSupportedValue {
            est.assertValid()
            val evidenceList = EvidenceSupportedValue(est.value ?: "")
            evidenceList.evidence.addAll(resolveEvidenceListFromEvidenceIdentifiers(est.getEvidenceList()))
            return evidenceList
        }

        /*
  Function returns an EvidenceList from thw EvidenceList cache based on a
  supplied id
   */

        fun buildFromIds(evidenceIdList: List<Int>?): EvidenceSupportedValue {
            if (evidenceIdList.isNullOrEmpty()) {
                EvidenceSupportedValue()
            }
            val esv = EvidenceSupportedValue()
            evidenceIdList?.stream()
                    ?.filter { id -> EvidenceNodeMapService.containsEvidence(id) }
                    ?.forEach { id -> esv.evidence.add(EvidenceNodeMapService.getEvidence(id)) }
            return esv
        }

//        fun resolveEvidenceListFromEvidenceTypeList(evidenceTypeList: List<EvidencedStringType>?):
//                Either<String, EvidenceList> {
//            if (evidenceTypeList.isNullOrEmpty()) {
//                return Either.left("No Evidence relationships found")
//            }
//            val evidenceList = EvidenceList()
//            evidenceTypeList.stream().map { est -> est. }
//                    .filter { id -> id != null && EvidenceNodeMapService.containsEvidence(id) }
//                    .forEach { id -> evidenceList.evidence.add(EvidenceNodeMapService.getEvidence(id!!)) }
//            return Either.right(evidenceList)
//        }


    }

}

@ExperimentalContracts
fun EvidencedStringType.assertValid(){
    contract{
        returns() implies (this@assertValid != null)
    }
    if (this == null || this.value == null || this.getEvidenceList().isEmpty()){
        throw Exception("Invalid EvidencedStringType")
    }
}

