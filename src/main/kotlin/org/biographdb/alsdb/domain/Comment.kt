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
import java.util.stream.Collectors
import kotlin.contracts.ExperimentalContracts

@NodeEntity(label="CommentList")
class CommentList (@Id val uniprotId: String) {
    @Relationship(type = "HAS_COMMENT")
    var comments:MutableList<Comment> = mutableListOf()

    companion object{
        @ExperimentalContracts
        fun resolveCommentListFromEntry(entry: Entry): CommentList {
            val uniprotId = entry?.getAccessionList()?.get(0) ?: ""
            val commentList = CommentList(uniprotId)
            entry.getCommentList()?.forEach { commentType ->
                run {
                    when (commentType.type) {
                        "disease" -> commentList.comments.add(DiseaseComment.createDiseaseCommentFromDiseaseType(commentType))
                        "interaction" -> commentList.comments
                                .add(InteractionComment.createInteractionCommentFromInteractionType(commentType))
                        "subcellular location" -> commentList.comments
                                .add(SubCellularLocationComment.resolveSubCellularLocationCommentFromCommentType(commentType))
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
    @Id
    val uuid = UUID.randomUUID().toString()
    var commentType: String = ""
    var molecule: String = ""
    var text: String = ""


    companion object{
        // process the list of EvidenceStringTypes but only use the first one
        @ExperimentalContracts
        fun resolveEvidenceListFromCommentType(commentType: CommentType): EvidenceSupportedValue =
                commentType.text?.stream()
                        ?.filter{ est -> est.getEvidenceList().isNotEmpty()}
                        ?.map{ est -> EvidenceSupportedValue.buildFromEvidenceStringType(est)}
                        ?.collect(Collectors.toList())?.get(0) ?: EvidenceSupportedValue()

        fun resolvePrimaryTextFromCommentType( commentType: CommentType): String =
            commentType.text?.stream()
                    ?.filter { est -> !est.value.isNullOrEmpty()}
                    ?.map{ est -> est.value}
                    ?.collect(Collectors.toList())?.get(0) ?: ""
    }
}
@NodeEntity (label = "SubCellularLocationComment")
class SubCellularLocationComment(): Comment() {
    @Relationship(type="HAS_SUBCELLULAR_LCATION")
    var subCellulatLocations: MutableList<SubCellularLocation> = mutableListOf()

    companion object {
        @ExperimentalContracts
        fun resolveSubCellularLocationCommentFromCommentType(commentType: CommentType): SubCellularLocationComment{
            val subCellularLocationComment = SubCellularLocationComment()
            subCellularLocationComment.labels.add("SubCellularLocationComment")
            subCellularLocationComment.text = Comment.resolvePrimaryTextFromCommentType(commentType)
            subCellularLocationComment.evidenceList = Comment.resolveEvidenceListFromCommentType(commentType)
           commentType.subcellularLocation?.forEach { sclType ->
                subCellularLocationComment.subCellulatLocations
                        .add(SubCellularLocation.resolveSubCellularLocationFromSubCellularLocationType(sclType))
            }
            return subCellularLocationComment
        }
    }

}

@NodeEntity(label = "SubCellularLocation")
class SubCellularLocation (){
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
    @Id
    val uuid = UUID.randomUUID().toString()
    companion object{
        fun resolveLocationFromEvidenceStringType( evidencedStringType: EvidencedStringType): Location {
            val location = Location(evidencedStringType.value ?: "")
            location.evidenceList = EvidenceSupportedValue.buildFromIds(evidencedStringType.getEvidenceList())
            return location
        }
    }
}

@NodeEntity(label = "Topology")
class Topology (val value: String): EvidenceSupported() {
    @Id
    val uuid = UUID.randomUUID().toString()
    companion object{
        fun resolveTopologyFromEvidenceStringType( evidencedStringType: EvidencedStringType): Topology {
            val topology = Topology(evidencedStringType.value?:"")
            topology.evidenceList = EvidenceSupportedValue.buildFromIds(evidencedStringType.getEvidenceList())
            return topology
        }
    }
}

class InteractionComment( val interactId1: String, val id1: String, val label1: String ="" ,
                          val interactId2: String, val id2: String, val label2: String ="" )
    :Comment() {

    companion object{
        fun createInteractionCommentFromInteractionType( commentType: CommentType): InteractionComment {
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
            // interactions don't have relationships to evidence nodes
            //interactionComment.evidenceList = resolveEvidenceListFromCommentType(commentType)
            return interactionComment
        }

    }

}

class DiseaseComment (val diseaseId: String, val diseaseName: String, val acronym: String ="",
                      val description: String ="" ):Comment() {
    companion object {
        @ExperimentalContracts
        fun createDiseaseCommentFromDiseaseType(commentType: CommentType): Comment{
            val diseaseType = commentType.disease
            val diseaseId = diseaseType?.id ?: ""
            val diseaseName = diseaseType?.name ?: ""
            val  acronym = diseaseType?.acronym?: ""
            val description = diseaseType?.description ?: ""
            val disease = DiseaseComment(diseaseId, diseaseName,acronym, description )
            disease.labels.add("Disease")
            disease.evidenceList = Comment.resolveEvidenceListFromCommentType(commentType)
            return disease
        }
    }


}