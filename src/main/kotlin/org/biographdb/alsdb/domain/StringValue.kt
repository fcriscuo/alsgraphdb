/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.Labels
import org.neo4j.ogm.annotation.NodeEntity
import java.util.*

@NodeEntity (label = "Value")
data class StringValue(val value: String) {
    @Id
    val uuid = UUID.randomUUID().toString()
    @Labels
    private val labels: MutableList<String> = mutableListOf()

    fun addLabel( label: String) {
        if (label.isNullOrBlank() && !labels.contains(label)) {
            labels.add(label)
        }
    }

    companion object {
        fun buildFromStringAndLabel( value: String, label: String): StringValue =
                StringValue(value).apply{
                    addLabel(label)
                }
    }


}