/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.model.literature

import org.biographdb.alsdb.model.AlsdbModel

data class Reference(
    val title: String,
    val idList: List<ReferenceId>,
    val articleAuthorList: List<Author>,
    val citation: Citation
) {


    companion object : AlsdbModel {
//        fun parseFromArticle (article: PubmedArticleSet.PubmedArticle.MedlineCitation.Article)
//                = PubMedArticle ( article.articleTitle?: "",
//                        article.abstract?.abstractText ?: "",
//                         PubMedJournal.resolveFromPubMedJournal(article?.journal)
//
//        )


    }
}