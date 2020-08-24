/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.EvidencedStringType
import org.biographdb.alsdb.model.uniprot.ProteinType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.util.*
import kotlin.contracts.ExperimentalContracts

const val submittedNameLabel: String = "Submitted Name"
const val recommendedNameLabel: String = "Recommended Name"
const val alternativeNameLabel: String = "Alternative Name"
const val fullNameLabel: String = "Full Name"
const val shortNameLabel: String = "Short Name"
const val ecNumberLabel: String = "EC Number"

abstract class UniProtNameTypes() {

    @Relationship(type = "HAS_RECOMMENDED_NAME")
    var recommendedName: RecommendedName = RecommendedName()

    @Relationship(type = "HAS_ALTERNATIVE_NAME")
    var alternaltiveNames: MutableList<AlternativeName> = mutableListOf()

    @Relationship(type = "HAS_SUBMITTED_NAME")
    var submittedNames: MutableList<SubmittedName> = mutableListOf()

    // minimum requirement for use
    fun isValid(): Boolean = recommendedName != null
}

@NodeEntity(label = alternativeNameLabel)
class AlternativeName() : EvidenceSupported() {

    @Relationship(type = "HAS_FULL_NAME")
    lateinit var fullName: EvidenceSupportedValue

    @Relationship(type = "HAS_SHORT_NAME")
    var shortNames: MutableList<EvidenceSupportedValue> = mutableListOf()

    companion object {
        @ExperimentalContracts
        fun buildFromAlternativeNameType(altNameType: ProteinType.AlternativeName): AlternativeName =
                AlternativeName().apply {
                    altNameType.fullName?.assertValid()
                    fullName = EvidenceSupportedValue.buildFromEvidenceStringType(altNameType.fullName!!)
                    fullName.addLabel(fullNameLabel)
                    altNameType.getShortNameList()?.map { est -> EvidenceSupportedValue.buildFromEvidenceStringType(est) }
                            ?.forEach { el ->
                                run {
                                    el.addLabel(shortNameLabel)
                                    shortNames.add(el)

                                }
                            }
                }
    }
}

@NodeEntity(label = submittedNameLabel)
class SubmittedName() {
    @Id
    val uuid = UUID.randomUUID().toString()

    @Relationship(type = "HAS_FULL_NAME")
    lateinit var fullName: EvidenceSupportedValue

    @Relationship(type = "HAS_EC_NUMBER")
    var ecNumbers: MutableList<EvidenceSupportedValue> = mutableListOf()

    fun isValid(): Boolean = fullName != null

    companion object {
        @ExperimentalContracts
        fun buildFromSubmittedName(subName: ProteinType.SubmittedName): SubmittedName {
            val submittedName = SubmittedName()
            if (subName.fullName != null) {
                submittedName.fullName = EvidenceSupportedValue.buildFromEvidenceStringType(subName.fullName!!)
                submittedName.fullName.addLabel(fullNameLabel)
            }
            subName.getEcNumberList()?.map { est -> EvidenceSupportedValue.buildFromEvidenceStringType(est) }
                    ?.forEach { el ->
                        run {
                            submittedName.ecNumbers.add(el)
                            el.addLabel(ecNumberLabel)
                        }
                    }
            return submittedName
        }
    }
}

@NodeEntity(label = recommendedNameLabel)
class RecommendedName() {
    @Id
    val uuid = UUID.randomUUID().toString()

    @Relationship(type = "HAS_FULL_NAME")
    lateinit var fullName: EvidenceSupportedValue

    @Relationship(type = "HAS_ALTERNATIVE_NAME")
    var shortNames: MutableList<EvidenceSupportedValue> = mutableListOf()

    @Relationship(type = "HAS_EC_NAME")
    var ecNumbers: MutableList<EvidenceSupportedValue> = mutableListOf()

    companion object {
        @ExperimentalContracts
        fun buildFromRecommendedName(recName: ProteinType.RecommendedName?): RecommendedName {
            val recommendedName = RecommendedName()
            recommendedName.apply {
                if (recName != null) {
                    recName.fullName!!.assertValid()
                    fullName = EvidenceSupportedValue.buildFromEvidenceStringType(recName.fullName!!)
                    fullName.addLabel(fullNameLabel)
                    recName.shortName?.map { est -> EvidenceSupportedValue.buildFromEvidenceStringType(est) }
                            ?.forEach { el ->
                                run {
                                    el.addLabel(shortNameLabel)
                                    shortNames.add(el)
                                }
                            }
                    recName.ecNumber?.map { est -> EvidenceSupportedValue.buildFromEvidenceStringType(est) }
                            ?.forEach { el ->
                                run {
                                    el.addLabel(ecNumberLabel)
                                    ecNumbers.add(el)
                                }
                            }
                }
            }
            return recommendedName
        }
    }
}

@NodeEntity(label = "BioTech Name")
class BiotechName() : EvidenceSupported() {
    companion object {
        @ExperimentalContracts
        fun buildFromEvidencedStringType(est: EvidencedStringType): BiotechName =
                BiotechName().apply {
                    est.assertValid()
                    evidenceList = EvidenceSupportedValue.buildFromEvidenceStringType(est)
                }
    }
}

@NodeEntity(label = "Allergen Name")
class AllergenName() : EvidenceSupported() {
    companion object {
        @ExperimentalContracts
        fun buildFromEvidencedStringType(est: EvidencedStringType): AllergenName =
                AllergenName().apply {
                    est.assertValid()
                    evidenceList = EvidenceSupportedValue.buildFromEvidenceStringType(est)
                }
    }
}

