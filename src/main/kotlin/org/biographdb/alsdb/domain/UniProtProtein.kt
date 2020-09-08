/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.harmonizome.HarmonizomeProtein
import org.biographdb.alsdb.model.uniprot.Entry
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import kotlin.contracts.ExperimentalContracts


@NodeEntity(label = "UniProtProtein")
class UniProtProtein(@Id val uniprotId: String) {
    var symbol: String = ""
    var name: String = ""
    var description: String = ""
    var uniprotXmlUrl: String = ""
    var sequence: String = ""
    var mass: Int = 0

    @Relationship(type = "HAS_GENE")
    lateinit var gene: Gene

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

    @Relationship(type = "HAS_COMMENT_LIST")
    lateinit var commentList: CommentList

    @Relationship(type = "HAS_ISOFORM_LIST")
    lateinit var isoformList: IsoformList

    @Relationship(type ="HAS_FEATURE_LIST")
    lateinit var featureList: FeatureList

    fun completeGeneRelationship(relatedGene: Gene) {
        gene = relatedGene
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("***UniprotId $uniprotId   name: $name  description:  $description\n")
        sb.append("    Gene ${gene.symbol}   name: ${gene.symbol}\n")
        sb.append("   DB reference count: ${dbReferenceList.dbReferences.size}")
        return sb.toString()
    }

    @ExperimentalContracts
    @ExperimentalStdlibApi
    fun completeUniProtProteinObject(entry: Entry) {
        sequence = entry?.sequence?.value ?: ""
        mass = entry?.sequence?.mass ?: 0
        alternativeNameList = AlternativeNameList.resolveAlternativeNameListFromEntry(entry)
        accessionList = AccessionList.resolveAccessionListFromEntry(entry)
        dbReferenceList = DbReferenceList.resolveDbReferenceListFromEntry(entry)
        keywordList = KeywordList.resolveKeywordListFromEntry(entry)
        componentList = ComponentList.resolveComponentListFromEntry(entry)
        geneNameList = GeneNameList.resolveGeneNameListFromEntry(entry)
        citationList = CitationList.resolveCitationListFromEntry(entry)
        commentList = CommentList.resolveCommentListFromEntry(entry)
        completeIsoformList(entry)
        //isoformList = IsoformList.buildFromUniProtEntry(entry)
        featureList = FeatureList.buildFromEntry(entry)
    }
     @ExperimentalContracts
     fun completeIsoformList(entry: Entry) {
         val isoList = IsoformList.buildFromUniProtEntry(entry)
         if ( isoList.isValid()){
             isoformList = isoList
         }
     }

    companion object {
        fun createUniProtProteinFromHarmonizomeProtein(harmonizomeProtein: HarmonizomeProtein): UniProtProtein {
            val uniProtProtein = UniProtProtein(harmonizomeProtein.uniprotId)
            with(uniProtProtein) {
                symbol = harmonizomeProtein.symbol
                name = harmonizomeProtein.name
                description = harmonizomeProtein.description
                uniprotXmlUrl = harmonizomeProtein.uniprotUrl.plus(".xml")
            }
            return uniProtProtein
        }


    }
}
