package org.biographdb.alsdb.domain

import org.neo4j.ogm.annotation.Relationship

abstract class EvidenceSupport {
    @Relationship(type = "HAS_EVIDENCE_LIST")
    lateinit var evidenceList: EvidenceList
}