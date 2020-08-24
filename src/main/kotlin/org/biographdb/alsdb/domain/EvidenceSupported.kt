/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.Relationship
import java.util.*

open class EvidenceSupported {
     @Id
     val uuid = UUID.randomUUID().toString()
    @Relationship(type = "HAS_EVIDENCE_LIST")
    lateinit var evidenceList: EvidenceSupportedValue



}