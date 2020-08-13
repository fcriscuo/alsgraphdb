package org.biographdb.alsdb.model.literature

import org.biographdb.alsdb.model.AlsdbModel
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation.Article.AuthorList.Author

data class Author(
    val lastName: String?,
    val foreName: String?
) {
    fun isValid(): Boolean = lastName != null

    companion object : AlsdbModel {
        fun parseFromPubMedAuthor(author: Author) =
            Author(author?.lastName, author?.foreName)
    }
}