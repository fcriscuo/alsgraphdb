/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.LocationType
import org.biographdb.alsdb.model.uniprot.PositionType
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.math.BigInteger


val defaultPosition = BigInteger.valueOf(-1L)

@NodeEntity(label = "Position")
class Position(val position: BigInteger) : EvidenceSupported() {

    fun isValid():Boolean = position >= BigInteger.ZERO

    companion object {
        fun buildFromPositionType(positionType: PositionType): Position =
           Position(positionType.position ?: defaultPosition).apply {
               evidenceSupportedValue = EvidenceSupportedValue.buildFromIds(positionType.getEvidenceList())
           }
        }
}

@NodeEntity(label = "Location")
class Location() {
    var sequence: String = ""
    @Relationship(type="HAS_BEGIN_POSITION")
    lateinit var beginPosition:Position
    @Relationship(type="HAS_END_POSITION")
    lateinit var endPosition:Position
    @Relationship (type = "HAS_POSITION")
    lateinit var position:Position

    fun isValid() = position != null || (beginPosition != null && endPosition != null)

    companion object{
        fun buildFromLocationType(locationType: LocationType): Location =
            Location().apply {
                sequence = locationType.sequence ?: ""
                if (locationType.position != null){
                    position = Position.buildFromPositionType(locationType.position!!)
                } else if (locationType.begin != null && locationType.end != null){
                    beginPosition = Position.buildFromPositionType(locationType.begin!!)
                    endPosition = Position.buildFromPositionType(locationType.end !!)
                }
            }
    }


}
