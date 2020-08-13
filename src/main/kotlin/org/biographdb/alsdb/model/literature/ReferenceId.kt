package org.biographdb.alsdb.model.literature

import org.biographdb.alsdb.model.AlsdbModel
import org.biographdb.alsdb.model.pubmed.PubmedArticleSet

data class ReferenceId(val idType: String = "PMID", val id: String) {

    companion object : AlsdbModel {
        fun parseFromMedlineCitation(citation: PubmedArticleSet.PubmedArticle.MedlineCitation) =
            ReferenceId("PMID", citation.pMID.toString())
    }
}