package org.biographdb.alsdb.service

import java.net.URL

object FetchUniProtProteinService {
    val urlTermplate =
        "https://www.uniprot.org:443" +
                "/uniprot/UNIPROTID.xml?include=yes"
    val uniprotIdPattern = "UNIPROTID"

    fun fetchUniProtXmlByID(id: String): String {
        val url = urlTermplate.replace(uniprotIdPattern, id)
        return URL(url).readText()
    }


}