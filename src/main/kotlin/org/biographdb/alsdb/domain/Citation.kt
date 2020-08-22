/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain


import org.biographdb.alsdb.model.uniprot.CitationType
import org.biographdb.alsdb.model.uniprot.Entry
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.util.*

@NodeEntity(label = "CitationList")
class CitationList(@Id val uniprotId: String) {
    @Relationship(type = "HAS_CITATION")
    var citations: MutableList<Citation> = mutableListOf()

    fun isValid(): Boolean = citations.size > 0

    companion object {
        fun resolveCitationListFromEntry(entry: Entry): CitationList {
            val uniprotId = entry?.getAccessionList()?.get(0) ?: ""
            val citationList = CitationList(uniprotId)
            entry?.getReferenceList()?.stream()
                ?.map { ref -> ref.citation }
                ?.forEach { citationType ->
                    citationList.citations.add(
                        Citation.resolveCitationFromCitationType(
                            citationType
                        )
                    )
                }
            return citationList
        }
    }

}

@NodeEntity(label = "Citation")
data class Citation(
    val date: String, val journalName: String, val volume: String,
    val first: String, val last: String, val title: String = ""
) {
    @Id
    var parentIdentifier: String = UUID.randomUUID().toString()

    @Relationship(type = "HAS_DB_REFERENCE_LIST")
    lateinit var dbReferenceList: DbReferenceList

    @Relationship(type = "HAS_AUTHOR_LIST")
    lateinit var authorList: AuthorList

    companion object {
        fun resolveCitationFromCitationType(
            citationType: CitationType
        ): Citation {
            val date = citationType?.date ?: ""
            val journalName = citationType?.name ?: ""
            val volume = citationType?.volume ?: ""
            val first = citationType?.first ?: ""
            val last = citationType?.last ?: ""
            val title = citationType?.title ?: ""
            val citation = Citation(date, journalName, volume, first, last, title)
            citation.parentIdentifier = resolvePrimaryKeyFromCitiationType(citationType)
            citation.dbReferenceList = DbReferenceList.resolveDbReferenceFromCitationType(
                citationType,
                citation.parentIdentifier
            )
            citation.authorList = AuthorList.resolveAuthorListFromCitationType(citationType, citation.parentIdentifier)
            return citation
        }

        /*
        The preferred primary key for a citation is its DOI
        If a DOI reference is not provided use a generated UUID
         */
        fun resolvePrimaryKeyFromCitiationType(citationType: CitationType): String {
            val id = citationType?.dbReference?.stream()
                ?.filter { dbRef -> dbRef.type.equals("DOI") }
                ?.map { ref -> ref.id }
                ?.findFirst()
            if (id != null && id.isPresent) {
                return id.get()
            }
            return UUID.randomUUID().toString()

        }
    }
}