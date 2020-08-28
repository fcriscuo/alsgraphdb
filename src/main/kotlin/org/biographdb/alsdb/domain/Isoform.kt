/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.IsoformType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

class IsoformSequence() {
     var type: String = ""
    var ref: String = ""

    companion object {
        fun buildFromIsoformType(isoformType: IsoformType): IsoformSequence =
                IsoformSequence().apply {
                    type = isoformType?.sequence?.type ?: ""
                    ref = isoformType?.sequence?.ref ?: ""
                }
    }
}

/*
Represents an alternative splicing variation (i.e. isoform) of the
parent UniProt protein
@XmlType(name = "isoformType", propOrder = ["id", "name", "sequence", "text"])
 */
@NodeEntity(label = "Isoform")
class Isoform (@Id val id:String =""){
    @Relationship (type = "HAS_ISOFORM_NAME")
    var names: MutableList<StringValue> = mutableListOf()

    lateinit var sequence: IsoformSequence

    companion object{
        fun buildFromIsoformType ( isoformType: IsoformType): Isoform =
                Isoform(isoformType.getIdList()?.get(0) ?: "").apply {
                    isoformType.getNameList()?.map {name -> StringValue.buildFromStringAndLabel(name.value,"Isoform Name")}
                    sequence = IsoformSequence.buildFromIsoformType(isoformType)
                }
    }
}
