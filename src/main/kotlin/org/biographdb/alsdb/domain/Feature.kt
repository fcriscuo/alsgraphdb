/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.Entry
import org.biographdb.alsdb.model.uniprot.FeatureType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.Labels
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.util.*

/**
 * Created by fcriscuo on 7/11/20.
 */

@NodeEntity(label = "Feature List")
class FeatureList(@Id val uniprotId: String) {
    @Relationship(type = "HAS_FEATURE")
    var features: MutableList<Feature> = mutableListOf()

    companion object {
        fun buildFromEntry(entry: Entry): FeatureList =
                FeatureList(entry.getAccessionList()?.get(0) ?: "").apply {
                    entry.getFeatureList()?.forEach { featureType -> features.add(Feature.buildFromFeatureType(featureType)) }
                }

    }

}

@NodeEntity(label = "Feature")
class Feature() : EvidenceSupported() {
    @Labels
    val labels: MutableList<String> = ArrayList()
    var description: String = ""
    var featureId: String = ""
    var original: String = ""
    var variations: String = ""
    var ref: String = ""

    @Relationship(type = "HAS_LOCATION")
    lateinit var location: Location

    companion object {
        fun buildFromFeatureType(featureType: FeatureType): Feature =
                Feature().apply {
                    labels.add(featureType.type)
                    description = featureType.description ?: ""
                    evidenceSupportedValue = EvidenceSupportedValue.buildFromIds(featureType.getEvidenceList())
                    variations = featureType?.getVariationList()?.joinToString() ?: ""
                    featureId = featureType.id ?: ""
                    original = featureType.original ?: ""
                    ref = featureType.ref ?: ""
                }

    }


}