/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.Entry
import org.biographdb.alsdb.model.uniprot.EvidencedStringType
import org.biographdb.alsdb.model.uniprot.ProteinType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.util.*


@NodeEntity(label = "ComponentList")
class ComponentList(@Id val uniprotId: String) {

    @Relationship(type = "HAS_COMPONENT")
    var components: MutableList<Component> = mutableListOf()

    companion object {

        fun resolveComponentListFromEntry(entry: Entry): ComponentList {
            var uniprotId = entry?.getAccessionList()?.get(0) ?: ""
            var componentList = ComponentList(uniprotId)
            entry.protein?.getComponentList()?.forEach { component ->
                componentList.components.add(Component.buildFromProteinTypeComponent(component))
            }
            return componentList
        }
    }
}

@NodeEntity(label = "Component")
class Component() : EvidenceSupported() {
    @Id
    val uuid = UUID.randomUUID().toString()

    @Relationship(type = "HAS_RECOMMENDED_NAME")
    var recommendedName: RecommendedName = RecommendedName()

    @Relationship(type = "HAS_ALTERNATIVE_NAME")
    var alternaltiveNames: MutableList<AlternativeName> = mutableListOf()

    @Relationship(type = "HAS_SUBMITTED_NAME")
    var submittedNames: MutableList<SubmittedName> = mutableListOf()

    // minimum requirement for use
    fun isValid(): Boolean = recommendedName != null

    companion object {

        fun buildFromProteinTypeComponent(proteinComponent: ProteinType.Component): Component {
            val component = Component()
            component.apply{
                if ( proteinComponent.recommendedName != null) {
                    recommendedName = RecommendedName.buildFromRecommendedName(proteinComponent.recommendedName!!)
                }
            }
            val fullName = proteinComponent.recommendedName?.fullName?.value ?: ""
            var shortName = ""
            if (!proteinComponent.recommendedName?.getShortNameList().isNullOrEmpty()) {
                shortName = proteinComponent.recommendedName?.getShortNameList()?.get(0)?.value ?: ""
            }

            proteinComponent.recommendedName?.getEcNumberList()?.forEach { est ->
                component.evidenceList.add(EvidenceList.buildFromEvidenceStringType(est)
            }

            return component
        }
    }
}

/*
var fullName: EvidencedStringType? = null
        var shortName: List<EvidencedStringType>? = null
        var ecNumber: List<EvidencedStringType>? = null
 var recommendedName: RecommendedName? = null
        protected var alternativeName: List<AlternativeName>? = null
        protected var submittedName: List<SubmittedName>? = null

        protected var cdAntigenName: List<EvidencedStringType>? = null
        protected var innName: List<EvidencedStringType>? = null
        @Relationship(type = "HAS_COMPONENT")
    var components: MutableList<Component> = mutableListOf()

 */
@NodeEntity(label = "Recommended Name")
class RecommendedName() {
    @Id
    val uuid = UUID.randomUUID().toString()

    @Relationship(type = "HAS_FULL_NAME")
    var fullName: FullName = FullName()

    @Relationship(type = "HAS_ALTERNATIVE_NAME")
    var shortNames: MutableList<ShortName> = mutableListOf()

    @Relationship(type = "HAS_EC_NAME")
    var ecNumbers: MutableList<EcNumber> = mutableListOf()

    companion object {
        fun buildFromRecommendedName(recName: ProteinType.RecommendedName?): RecommendedName {
            val recommendedName = RecommendedName()
            recommendedName.apply {
                if (recName != null && recName.fullName != null) {
                    if (recName.fullName != null) {
                        val est = recName.fullName
                        fullName = FullName.buildFromEvidencedStringType(est)
                    }
                    recName.shortName?.forEach { est ->
                        shortNames.add(ShortName.buildFromEvidencedStringType(est))
                    }
                    recName.ecNumber?.forEach { est ->
                        ecNumbers.add(EcNumber.buildFromEvidencedStringType(est))
                    }
                }
            }
            return recommendedName
        }
    }


}

/*
 var fullName: EvidencedStringType? = null
        var ecNumber: List<EvidencedStringType>? = null
 */
@NodeEntity(label = "Submitted Name")
class SubmittedName() {
    @Id
    val uuid = UUID.randomUUID().toString()

    @Relationship(type = "HAS_FULL_NAME")
    lateinit var fullName: FullName

    @Relationship(type = "HAS_EC_NUMBER")
    var ecNumbers: MutableList<EcNumber> = mutableListOf()

    fun isValid(): Boolean = fullName != null

    companion object {
        fun buildFromSubmittedName(subName: ProteinType.SubmittedName): SubmittedName {
            val submittedName = SubmittedName()
            if (subName.fullName != null) {
                submittedName.fullName = FullName.buildFromEvidencedStringType(subName.fullName!!)
            }
            subName.getEcNumberList()?.forEach { est ->
                submittedName.ecNumbers.add(EcNumber.buildFromEvidencedStringType(est))
            }
            return submittedName
        }
    }
}

@NodeEntity(label = "BioTech Name")
class BiotechName(evidenceList: EvidenceList) : EvidenceSupported() {
    @Id
    val uuid = UUID.randomUUID().toString()

    companion object {
        fun buildFromEvidencedStringType(est: EvidencedStringType): BiotechName =
                BiotechName(EvidenceList.Companion.buildFromEvidenceStringType(est))
    }
}

@NodeEntity(label = "Allergen Name")
class AllergenName() : EvidenceSupported() {
    @Id
    val uuid = UUID.randomUUID().toString()

    companion object {
        fun buildFromEvidencedStringType(est: EvidencedStringType): AllergenName {
            val allergenName = AllergenName()
            allergenName.evidenceList = EvidenceList.Companion.buildFromEvidenceStringType(est)
            return allergenName
        }
    }
}

@NodeEntity(label = "FullName")
class FullName() : EvidenceSupported() {
    @Id
    val uuid = UUID.randomUUID().toString()

    companion object {
        fun buildFromEvidencedStringType(est: EvidencedStringType): FullName {
            val fullName = FullName()
            fullName.evidenceList = EvidenceList.Companion.buildFromEvidenceStringType(est)
            return fullName
        }
    }
}

@NodeEntity(label = "ShortName")
class ShortName() : EvidenceSupported() {
    @Id
    val uuid = UUID.randomUUID().toString()

    companion object {
        fun buildFromEvidencedStringType(est: EvidencedStringType): ShortName {
            val shortName = ShortName()
            shortName.evidenceList = EvidenceList.Companion.buildFromEvidenceStringType(est)
            return shortName
        }
    }
}

@NodeEntity(label = "EC_number")
class EcNumber() : EvidenceSupported() {
    @Id
    val uuid = UUID.randomUUID().toString()

    companion object {
        fun buildFromEvidencedStringType(est: EvidencedStringType): EcNumber {
            val ecNumber = EcNumber()
            ecNumber.evidenceList = EvidenceList.Companion.buildFromEvidenceStringType(est)
            return ecNumber
        }
    }
}