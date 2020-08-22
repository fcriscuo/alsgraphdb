/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.Entry
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

/*
Represents a collection of keywords associated with a specific
UniProtProtein
The primary accession id of the protein is used a a primary key
 */
@NodeEntity(label = "KeywordList")
class KeywordList(@Id val uniProtId: String) {

    @Relationship(type = "HAS_KEYWORD")
    var keywords: MutableList<Keyword> = mutableListOf<Keyword>()

    companion object {
        fun resolveKeywordListFromEntry(entry: Entry): KeywordList {
            val uniprotId = entry?.getAccessionList()?.get(0) ?: ""
            var keywordList = KeywordList(uniprotId)
            entry.getKeywordList()?.forEach {
                val keyword = Keyword.resolveKeywordFromKeywordType(it)
                if (keyword.isValid()) {
                    keywordList.keywords.add(keyword)
                }
            }
            return keywordList
        }
    }
}


