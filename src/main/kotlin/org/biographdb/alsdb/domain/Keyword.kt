package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.KeywordType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity

/*
Represents a UniProt keyword
May not be unique across multiple UniProt entries
 */
@NodeEntity(label = "Keyword")
data class Keyword(@Id val keywordId: String, val keyword: String) {

    fun isValid(): Boolean = keywordId.isNotEmpty() && keyword.isNotEmpty()

    companion object {
        fun resolveKeywordFromKeywordType(keywordType: KeywordType): Keyword {
            val id = keywordType.id ?: ""
            val keyWord = keywordType.value ?: ""
            return Keyword(id, keyWord)
        }
    }
}