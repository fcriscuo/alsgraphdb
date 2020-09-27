/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain.geneontology

import org.neo4j.ogm.annotation.*
import java.util.ArrayList


/**
 * Created by fcriscuo on 9/20/20.
 */
@RelationshipEntity(type = "HAS_GO_RELATIONSHIP")
class GeneOntologyRelationship (@StartNode val sourceGO: GeneOntologyTerm, @EndNode val targetGO: GeneOntologyTerm ) {
    @Id
    @GeneratedValue
    private val id: Long? = null
    @Labels
    val labels: MutableList<String> = ArrayList()

}