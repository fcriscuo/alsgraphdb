/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.CommentType
import org.biographdb.alsdb.model.uniprot.Entry
import org.biographdb.alsdb.model.uniprot.EvidencedStringType
import org.biographdb.alsdb.model.uniprot.SubcellularLocationType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.Labels
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.util.*
import kotlin.contracts.ExperimentalContracts

const val DISEASE_COMMENT_TYPE = "disease"
const val INTERACTION_COMMENT_TYPE = "interaction"
const val SUBCELLULAR_COMMENT_TYPE = "subcellular location"
const val TISSUE_SPECIFICITY_COMMENT_TYPE = "tissue specificity"
const val INDUCTION_COMMENT_TYPE = "induction"
const val DOMAIN_COMMENT_TYPE = "domain"
const val PTM_COMMENT_TYPE = "PTM"
const val MASS_SPEC_COMMENT_TYPE = "mass spectrometry"
const val MISC_COMMENT_TYPE = "miscellaneous"


@NodeEntity(label="CommentList")
class CommentList (@Id val uniprotId: String) {
    @Relationship(type = "HAS_COMMENT")
    var comments:MutableList<Comment> = mutableListOf()

    companion object{
        @ExperimentalContracts
        fun resolveCommentListFromEntry(entry: Entry): CommentList {
            val uniprotId = entry.getAccessionList()?.get(0) ?: ""
            val commentList = CommentList(uniprotId)
            entry.getCommentList()?.forEach { commentType ->
                run {
                    when (commentType.type) {
                        DISEASE_COMMENT_TYPE -> commentList.comments.add(DiseaseComment.buildFromDiseaseType(commentType))
                        INTERACTION_COMMENT_TYPE -> commentList.comments
                                .add(InteractionComment.buildFromInteractionType(commentType))
                        SUBCELLULAR_COMMENT_TYPE -> commentList.comments
                                .add(SubCellularLocationComment.buildFromCommentType(commentType))
                        DOMAIN_COMMENT_TYPE -> commentList.comments
                                .add(DomainComment.buildFromCommentType(commentType))
                        INDUCTION_COMMENT_TYPE -> commentList.comments
                                .add(InductionComment.buildFromCommentType(commentType))
                        PTM_COMMENT_TYPE -> commentList.comments
                                .add(PtmComment.buildFromCommentType(commentType))
                        MASS_SPEC_COMMENT_TYPE -> commentList.comments
                                .add(MassSpecComment.buildFromCommentType(commentType))
                        TISSUE_SPECIFICITY_COMMENT_TYPE -> commentList.comments
                                .add(TissueSpecComment.buildFromCommentType(commentType))
                        else -> println("Comment Type: ${commentType.type} is not supported")
                    }
                }
            }
            return commentList
        }
    }
}


@NodeEntity(label = "Comment")
abstract class Comment (): EvidenceSupported() {
    @Labels
    val labels: MutableList<String> = ArrayList()
    var molecule: String = ""
    @Relationship(type="HAS_TEXT")
    var texts:MutableList<EvidenceSupportedValue> = mutableListOf()
    @Relationship (type="HAS_EVIDENCE_COLLECTION")
    lateinit var evidenceCollection: EvidenceCollection


    companion object{
        @ExperimentalContracts
        fun initializeCommonAttributes(comment: Comment, commentType: CommentType) {
            resolveEvidenceSupportedValuesFromCommentType(comment, commentType)
            resolveEvidenceCollection (comment, commentType)
            comment.molecule = commentType.molecule?.value ?: ""
        }

        @ExperimentalContracts
        fun resolveEvidenceSupportedValuesFromCommentType(comment: Comment, commentType: CommentType)
                = commentType.text?.stream()
                        ?.filter{ est -> est.getEvidenceList().isNotEmpty()}
                        ?.map{ est -> EvidenceSupportedValue.buildFromEvidenceStringType(est)}
                        ?.forEach { esv -> run {
                            esv.addLabel("TEXT")
                            comment.texts.add(esv)
                        } }

         fun resolveEvidenceCollection (comment: Comment, commentType: CommentType) {
             if(commentType.evidence.isNotEmpty()){
                 comment.evidenceCollection = EvidenceCollection.buildFromEvidenceIdList(commentType.evidence)
             }
         }
    }
}

@ExperimentalContracts
@NodeEntity (label = "MassSpec Comment")
class MassSpecComment: Comment () {
    var mass: Float = 0.0F
    var method: String = ""
    companion object{
        @ExperimentalContracts
        fun buildFromCommentType( commentType: CommentType): MassSpecComment {
            val massSpecComment = MassSpecComment()
            massSpecComment.mass = commentType.mass ?: 0.0F
            massSpecComment.method = commentType.method ?: ""
            Comment.initializeCommonAttributes(massSpecComment, commentType)
            return massSpecComment
        }
    }
}

@ExperimentalContracts
@NodeEntity (label = "Tissue Specificty Comment")
class TissueSpecComment(): Comment() {
    companion object {
        @ExperimentalContracts
        fun buildFromCommentType(commentType: CommentType): TissueSpecComment {
            val tsComment = TissueSpecComment()
            Comment.initializeCommonAttributes(tsComment, commentType)
            return tsComment
        }
    }
}


@ExperimentalContracts
@NodeEntity (label = "PTM Comment")
class PtmComment: Comment() {
    companion object{
        @ExperimentalContracts
        fun buildFromCommentType( commentType: CommentType): PtmComment {
            val ptmComment = PtmComment()
            Comment.initializeCommonAttributes(ptmComment, commentType)
            return ptmComment
        }
    }
}

@NodeEntity( label = "Induction Comment" )
class InductionComment: Comment() {
    companion object{
        @ExperimentalContracts
        fun buildFromCommentType( commentType: CommentType): InductionComment {
            val inductionComment = InductionComment()
            Comment.initializeCommonAttributes(inductionComment, commentType)
            return inductionComment
        }
    }
}

@NodeEntity (label = "Domain Comment")
class DomainComment: Comment() {

    companion object{
        @ExperimentalContracts
        fun buildFromCommentType(commentType: CommentType): DomainComment {
            val domainComment = DomainComment()
            Comment.initializeCommonAttributes(domainComment, commentType)
            return domainComment
        }
    }
}

@NodeEntity (label = "SubCellularLocationComment")
class SubCellularLocationComment : Comment() {
    @Relationship(type="HAS_SUBCELLULAR_LOCATION")
    var subCellularLocations: MutableList<SubCellularLocation> = mutableListOf()

    companion object {
        @ExperimentalContracts
        fun buildFromCommentType(commentType: CommentType): SubCellularLocationComment{
            val subCellularLocationComment = SubCellularLocationComment()
            Comment.initializeCommonAttributes(subCellularLocationComment, commentType)
            subCellularLocationComment.labels.add("SubCellularLocationComment")
           commentType.subcellularLocation?.forEach { sclType ->
                subCellularLocationComment.subCellularLocations
                        .add(SubCellularLocation.resolveSubCellularLocationFromSubCellularLocationType(sclType))
            }
            return subCellularLocationComment
        }
    }
}

@NodeEntity(label = "SubCellularLocation")
class SubCellularLocation {
    @Id
    val uuid = UUID.randomUUID().toString()
    @Relationship(type = "HAS_LOCATION")
    var locations:MutableList<Location> = mutableListOf()
    @Relationship(type = "HAS_TOPOLOGY")
    var topologies: MutableList<Topology> = mutableListOf()
     companion object{
         fun resolveSubCellularLocationFromSubCellularLocationType( scl: SubcellularLocationType): SubCellularLocation {
             val subCellularLocation = SubCellularLocation()
             scl.getLocationList()?.forEach {est ->
                subCellularLocation.locations.add(Location.resolveLocationFromEvidenceStringType(est))
             }
             scl.getTopologyList()?.forEach { est ->
                 subCellularLocation.topologies.add(Topology.resolveTopologyFromEvidenceStringType(est))
             }
             return subCellularLocation
         }
     }
}

@NodeEntity(label = "Location")
class Location (val value: String): EvidenceSupported() {

    companion object{
        fun resolveLocationFromEvidenceStringType( evidencedStringType: EvidencedStringType): Location {
            val location = Location(evidencedStringType.value ?: "")
            location.evidenceSupportedValue = EvidenceSupportedValue.buildFromIds(evidencedStringType.getEvidenceList())
            return location
        }
    }
}

@NodeEntity(label = "Topology")
class Topology (val value: String): EvidenceSupported() {

    companion object{
        fun resolveTopologyFromEvidenceStringType( evidencedStringType: EvidencedStringType): Topology {
            val topology = Topology(evidencedStringType.value?:"")
            topology.evidenceSupportedValue = EvidenceSupportedValue.buildFromIds(evidencedStringType.getEvidenceList())
            return topology
        }
    }
}

class InteractionComment( val interactId1: String, val id1: String, val label1: String ="" ,
                          val interactId2: String, val id2: String, val label2: String ="" )
    :Comment()
     {
    companion object{
        fun buildFromInteractionType(commentType: CommentType): InteractionComment {
            val interactionType1 = commentType.interactant?.get(0)
            val interactionType2 = commentType.interactant?.get(1)
            val inter1 = interactionType1?.intactId ?: ""
            val id1 = interactionType1?.id ?: ""
            val label1 = interactionType1?.label ?: ""
            val inter2 = interactionType2?.intactId ?: ""
            val id2 = interactionType2?.id ?: ""
            val label2 = interactionType2?.label ?: ""
            val interactionComment = InteractionComment(inter1, id1, label1, inter2, id2, label2)
            interactionComment.labels.add("Interaction")
            return interactionComment
        }
    }
}

class DiseaseComment (val diseaseId: String, val diseaseName: String, val acronym: String ="",
                      val description: String ="" ):Comment() {
    companion object {
        @ExperimentalContracts
        fun buildFromDiseaseType(commentType: CommentType): Comment{
            val diseaseType = commentType.disease
            val diseaseId = diseaseType?.id ?: ""
            val diseaseName = diseaseType?.name ?: ""
            val  acronym = diseaseType?.acronym?: ""
            val description = diseaseType?.description ?: ""
            val disease = DiseaseComment(diseaseId, diseaseName,acronym, description )
            disease.labels.add("Disease")
            Comment.initializeCommonAttributes(disease, commentType)
            return disease
        }
    }
}