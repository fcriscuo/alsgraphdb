package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.PersonType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity

@NodeEntity(label = "Person")
data class Person(@Id val name: String) {

    companion object {
        fun resolvePersonFromPersonType(personType: PersonType): Person =
            Person(personType.name)
    }
}