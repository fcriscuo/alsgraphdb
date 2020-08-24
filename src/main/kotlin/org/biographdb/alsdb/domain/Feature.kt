/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.util.*

/**
 * Created by fcriscuo on 7/11/20.
 */
@NodeEntity(label = "Evidence")
class Feature {
    @Id
    val uuid = UUID.randomUUID().toString()

    @Relationship(type = "HAS_EVIDENCE_LIST")
    lateinit var evidenceList: EvidenceSupportedValue

}