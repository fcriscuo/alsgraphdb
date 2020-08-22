/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.service

import org.biographdb.alsdb.model.uniprot.Uniprot
import java.net.URL
import javax.xml.bind.JAXBContext

/*
Responsible for fetching a specified UniProt protein entry
The request is based on the protein's UniProt Id (e.g. P02786)
Returns either a org.biographdb.alsdb.model.uniprot.Uniprot instance containing
an object graph of the unmarshalled XML data or the entire entry in XML format
 */
object FetchUniProtProteinService {
    val uniprotIdPattern = "UNIPROTID"
    val urlTermplate =
        "https://www.uniprot.org:443" +
                "/uniprot/UNIPROTID.xml?include=yes"

    fun fetchUniProtDataByUniProtId(uniprotId: String): Uniprot {
        val jaxbContext = JAXBContext.newInstance(Uniprot::class.java)
        val unmarshaller = jaxbContext.createUnmarshaller()
        val text = fetchUniProtXmlByUniProtId(uniprotId)
        text.reader().use { it ->
            return unmarshaller.unmarshal(it) as Uniprot
        }
    }

    private fun fetchUniProtXmlByUniProtId(uniprotId: String): String {
        val url = urlTermplate.replace(uniprotIdPattern, uniprotId)
        return URL(url).readText()
    }


}