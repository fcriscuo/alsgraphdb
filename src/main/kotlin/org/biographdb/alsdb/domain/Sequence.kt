/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.SequenceType

class Sequence {
    var length: Int = 0
    var mass: Int = 0
    var fragment: String = ""
    var isPrecursor: Boolean = false
    var value: String = ""


    companion object {
        fun buildFromSequenceType( sequenceType: SequenceType?): Sequence =
            Sequence().apply {
                length = sequenceType?.length ?: 0
                mass = sequenceType?.mass ?: 0
                fragment = sequenceType?.fragment ?: ""
                isPrecursor = sequenceType?.isPrecursor ?: false
                value = sequenceType?.value ?: ""
            }


    }
}