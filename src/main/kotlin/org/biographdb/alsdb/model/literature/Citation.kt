package org.biographdb.alsdb.model.literature

import org.biographdb.alsdb.model.AlsdbModel
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet

data class Citation(
    val journal: String?,
    val issn: String?,
    val volume: Int?,
    val issue: String?,
    val year: Int?,
    val month: String?

) {
    companion object : AlsdbModel {
        fun parseCitationFromJournal(journal: PubmedArticleSet.PubmedArticle.MedlineCitation.Article.Journal): Citation =
            Citation(
                journal.title,
                journal.iSSN.toString(),
                journal.journalIssue?.volume,
                journal.journalIssue?.issue,
                journal.journalIssue?.pubDate?.year,
                journal.journalIssue?.pubDate?.month
            )

        fun isValidJournalIssue(journalIssue: PubmedArticleSet.PubmedArticle.MedlineCitation.Article.Journal.JournalIssue?): Boolean =
            journalIssue != null && journalIssue.volume > 0 && !journalIssue.issue.isNullOrBlank()

        fun isValidCitation(journal: PubmedArticleSet.PubmedArticle.MedlineCitation.Article.Journal): Boolean =
            !journal.title.isNullOrBlank() && isValidJournalIssue(journal.journalIssue)
    }


}