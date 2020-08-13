package org.biographdb.alsdb.domain

import arrow.core.Either
import org.biographdb.alsdb.model.AlsdbModel
import org.biographdb.alsdb.model.uniprot.EvidencedStringType
import org.biographdb.alsdb.service.EvidenceNodeMapService
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.util.*

/**
 * Created by fcriscuo on 7/11/20.
 */
@NodeEntity(label = "EvidenceList")
class EvidenceList() {
    @Id
    val uuid = UUID.randomUUID().toString()

    @Relationship(type = "HAS_EVIDENCE")
    var evidence: MutableList<Evidence> = mutableListOf()

    companion object : AlsdbModel {
        fun resolveEvidenceListFromEvidenceIdentifiers(evidenceIds: String): EvidenceList {
            var evidenceList = EvidenceList()
            // split the String of evidence ids by spaces and
            // look up the appropriate Evidence node in the database
            parseStringOnSpace(evidenceIds).stream()
                .map { id -> id.toInt() }
                .filter { key -> EvidenceNodeMapService.containsEvidence(key) }
                .forEach { key -> evidenceList.evidence.add(EvidenceNodeMapService.getEvidence(key)) }
            return evidenceList
        }

        /*
  Function returns an EvidenceList object or a no evidence message
  encapsulated in an Either container
  Input is a List of evidence ids that may be empty or null
   */
        fun buildEvidenceList(evidenceIdList: List<Int>?): Either<String, EvidenceList> {
            if (evidenceIdList.isNullOrEmpty()) {
                return Either.left("No Evidence relationships found")
            }
            val evidenceList = EvidenceList()
            evidenceIdList.stream()
                .filter { id -> EvidenceNodeMapService.containsEvidence(id) }
                .forEach { id -> evidenceList.evidence.add(EvidenceNodeMapService.getEvidence(id)) }
            return Either.right(evidenceList)
        }

        fun resolveEvidenceListFromEvidenceTypeList(evidenceTypeList: List<EvidencedStringType>?): Either<String, EvidenceList> {
            if (evidenceTypeList.isNullOrEmpty()) {
                return Either.left("No Evidence relationships found")
            }
            val evidenceList = EvidenceList()
            evidenceTypeList.stream().map { est -> est.value?.toInt() }
                .filter { id -> id != null && EvidenceNodeMapService.containsEvidence(id) }
                .forEach { id -> evidenceList.evidence.add(EvidenceNodeMapService.getEvidence(id!!)) }
            return Either.right(evidenceList)
        }

    }


}