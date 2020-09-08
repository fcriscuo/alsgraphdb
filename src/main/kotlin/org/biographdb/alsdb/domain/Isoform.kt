/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.Entry
import org.biographdb.alsdb.model.uniprot.IsoformType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.contracts.ExperimentalContracts

const val ALT_PRODUCTS_COMMENT_TYPE = "alternative products"

@NodeEntity (label = "Isoform List")
class IsoformList (@Id val uniprotId: String) {
    @Relationship (type = "HAS_ISOFORM")
    var isoforms: MutableList<Isoform> = mutableListOf()

    fun isValid(): Boolean = isoforms.isNotEmpty()

    companion object{
        @ExperimentalContracts
        fun buildFromUniProtEntry(entry: Entry): IsoformList {
           val isoformList = IsoformList(entry.getAccessionList()?.get(0) ?: "")
            entry.getCommentList()?.stream()?.filter { commentType -> commentType.type == ALT_PRODUCTS_COMMENT_TYPE}
                    ?.map { commentType -> commentType.isoform}
                    ?.map {isoformList -> isoformList?.stream()}
                    ?.forEach{isoformStream -> isoformList.isoforms.addAll(reduceIsoformStream(isoformStream))}
            return isoformList
        }

        @ExperimentalContracts
        fun reduceIsoformStream(isoformTypeStream: Stream<IsoformType>?): List<Isoform> =
                if ( isoformTypeStream != null ) {
                    isoformTypeStream.map { isoformType -> Isoform.buildFromIsoformType(isoformType) }
                            .filter(Isoform::isValid)
                            .collect(Collectors.toList())
                } else {
                    emptyList<Isoform>()
                }
    }
}
@NodeEntity(label="Isoform Sequence")
data class IsoformSequence(@Id val id: String, val type: String) {
    @Relationship (type="HAS_ISOFORM_REF")
    var refs: MutableList<String> = mutableListOf()

    companion object {
        fun buildFromIsoformType(isoformType: IsoformType): IsoformSequence {
            val id = isoformType.getIdList()?.get(0) ?: ""
            val type = isoformType.sequence?.type ?: ""
            return IsoformSequence(id,type).apply {
                isoformType.sequence?.ref?.split(" ")
                        ?.forEach {ref -> refs.add(ref)  }
            }
        }
    }
}
/*
Represents an alternative splicing variation (i.e. isoform) of the
parent UniProt protein
@XmlType(name = "isoformType", propOrder = ["id", "name", "sequence", "text"])
 */
@NodeEntity(label = "Isoform")
class Isoform(@Id val id: String = "") {
    @Relationship(type = "HAS_ISOFORM_NAME")
    var names: MutableList<LabeledValue> = mutableListOf()
    @Relationship(type="HAS_TEXT_EVIDENCE")
    var textEvidence: MutableList<EvidenceSupportedValue> = mutableListOf()

    lateinit var sequence: IsoformSequence

    fun isValid(): Boolean = !id.isNullOrEmpty() &&names.isNotEmpty()

    companion object {
        private val isoformNameLabel = "Isoform Name"
        @ExperimentalContracts
        fun buildFromIsoformType(isoformType: IsoformType): Isoform =
                Isoform(isoformType.getIdList()?.get(0) ?: "").apply {
                    isoformType.getNameList()?.stream()
                            ?.filter { name -> !name.value.isNullOrBlank() }
                            ?.map { name -> LabeledValue(Pair(isoformNameLabel, name.value ?: " ")) }
                            ?.forEach { lblValue -> names.add(lblValue) }
                    sequence = IsoformSequence.buildFromIsoformType(isoformType)
                    isoformType.getTextList()?.stream()
                            ?.map { est -> EvidenceSupportedValue.buildFromEvidenceStringType(est) }
                            ?.forEach { esv -> textEvidence.add(esv) }
                }
    }
}
