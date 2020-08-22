/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.neo4j.ogm.annotation.Relationship

 open class EvidenceSupported {
    @Relationship(type = "HAS_EVIDENCE_LIST")
    lateinit var evidenceList: EvidenceList



}