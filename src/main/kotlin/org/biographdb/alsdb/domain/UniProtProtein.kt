package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.Entry
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.util.*

@NodeEntity(label = "UniProtProtein")
class UniProtProtein {
    val baseUniProtUri = "http://uniprot.org/uniprot/"
    @Id
    val uuid = UUID.randomUUID().toString()
    var primaryAccessionNumber: String = ""
    var fullName: String = ""
    var shortName: String = ""
    var uri: String = ""
    var sequence: String = ""
    var mass: Int = 0

    @Relationship(type = "HAS_ACCESSION_LIST")
    lateinit var accessionList: AccessionList

    @Relationship(type = "HAS_DB_REFERENCE_LIST")
    lateinit var dbReferenceList: DbReferenceList

    @Relationship(type = "HAS_KEYWORD_LIST")
    lateinit var keywordList: KeywordList

    @Relationship(type = "HAS_ALTERNATIVE_NAME_LIST")
    lateinit var alternativeNameList: AlternativeNameList

    @Relationship(type = "HAS_COMPONENT_LIST")
    lateinit var componentList: ComponentList

    @Relationship(type = "HAS_GENE_NAME_LIST")
    lateinit var geneNameList: GeneNameList

    @Relationship(type = "HAS_CITATION_LIST")
    lateinit var citationList: CitationList


    companion object {
        fun createUniProtProteinObject(entry: Entry): UniProtProtein {
            val uniProtProtein = UniProtProtein()
            with(uniProtProtein) {
                primaryAccessionNumber = entry.getAccessionList()?.get(0) ?: ""
                fullName = entry.protein?.recommendedName?.fullName?.value ?: ""
                shortName = entry.protein?.recommendedName?.getShortNameList()?.get(0)?.value ?: ""
                uri = baseUniProtUri.plus(primaryAccessionNumber)
                sequence = entry?.sequence?.value ?: ""
                mass = entry?.sequence?.mass ?: 0
                alternativeNameList = AlternativeNameList.resolveAlternativeNameListFromEntry(entry)
                accessionList = AccessionList.resolveAccessionListFromEntry(entry)
                dbReferenceList = DbReferenceList.resolveDbReferenceListFromEntry(entry)
                keywordList = KeywordList.resolveKeywordListFromEntry(entry)
                componentList = ComponentList.resolveComponentListFromEntry(entry)
                geneNameList = GeneNameList.resolveGeneNameListFromEntry(entry)
                citationList = CitationList.resolveCitationListFromEntry(entry)
            }
            return uniProtProtein
        }
    }
}
