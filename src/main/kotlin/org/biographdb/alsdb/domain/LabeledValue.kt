/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.Labels
import org.neo4j.ogm.annotation.NodeEntity
import java.util.*

/*
Represents a Node that may have multiple labels and a single
String property
 */
@NodeEntity (label = "LabeledValue")
data class LabeledValue(val labeledValuedPair: Pair<String,String>) {
    @Id
    val uuid = UUID.randomUUID().toString()
    @Labels
    private val labels: MutableList<String> = mutableListOf(labeledValuedPair.first)
    val value: String = labeledValuedPair.second

    fun addLabel( label: String) {
        if (label.isNullOrBlank() && !labels.contains(label)) {
            labels.add(label)
        }
    }

}