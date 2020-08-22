/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.model

import org.biographdb.alsdb.model.pubmed.PubmedArticleSet.PubmedArticle.MedlineCitation.Article.Journal.JournalIssue
import java.time.LocalDate

data class PubMedJournalIssue(
    val volume: String?,
    val issue: String?,
    val date: LocalDate?
) {
    fun isValid(): Boolean = volume != null && issue != "null"

    companion object : AlsdbModel {
        fun resolveFromPubMedJournalIssue(journalIssue: JournalIssue?): PubMedJournalIssue {
            val monthName = journalIssue?.pubDate?.month
            val month = parseMonthNumberFromAbbreviatedMonthName(monthName)
            return PubMedJournalIssue(
                journalIssue?.volume.toString(), journalIssue?.issue,
                LocalDate.of(journalIssue?.pubDate?.year ?: 1900, month ?: 1, 1)
            )
        }
    }

}
