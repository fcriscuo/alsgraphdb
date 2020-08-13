package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.CitationType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity(label = "AuthorList")
class AuthorList(@Id val authorListId: String) {
    @Relationship(type = "HAS_AUTHOR")
    var authors: MutableList<Person> = mutableListOf()

    fun isValid(): Boolean = authors.size > 0

    companion object {
        fun resolveAuthorListFromCitationType(citationType: CitationType, parentId: String): AuthorList {
            val authorList = AuthorList(parentId)
            citationType?.authorList?.getConsortiumOrPersonList()
                ?.forEach { p -> authorList.authors.add(Person(p.toString())) }
            return authorList
        }
    }


}