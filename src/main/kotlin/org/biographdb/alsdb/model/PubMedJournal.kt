package org.biographdb.alsdb.model

import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation.Article.Journal


data class PubMedJournal(
    val issn: String,
    val journalTitle: String,
    val isoAbbreviation: String,
    val issue: PubMedJournalIssue
) {

    fun isValid(): Boolean = journalTitle.isNotEmpty() && issue.isValid()

    companion object : AlsdbModel {
        fun resolveFromPubMedJournal(journal: Journal?) =
            PubMedJournal(
                journal?.iSSN?.issnType ?: " ",
                journal?.title ?: "",
                journal?.iSOAbbreviation ?: " ",
                PubMedJournalIssue.resolveFromPubMedJournalIssue(journal?.journalIssue)
            )
    }
}