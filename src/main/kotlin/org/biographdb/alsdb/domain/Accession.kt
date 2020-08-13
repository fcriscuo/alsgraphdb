package org.biographdb.alsdb.domain

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity

@NodeEntity(label = "Accession")
data class Accession(@Id val accessionId: String) {

}