package org.biographdb.alsdb.service

import org.biographdb.alsdb.model.uniprot.Uniprot
import java.net.URL
import javax.xml.bind.JAXBContext

/*
Responsible for fetching a specified UniProt protein entry in XML format
The request is based on the protein's primary accession number
Returns a org.biographdb.alsdb.model.uniprot.Uniprot instance containing
an object graph of the unmarshalled XM data
 */
object FetchUniProtProteinService {
    val uniprotIdPattern = "UNIPROTID"
    val urlTermplate =
        "https://www.uniprot.org:443" +
                "/uniprot/UNIPROTID.xml?include=yes"

    fun fetchUniProtDataByPrimaryAccessionNumber(accessionNumber: String): Uniprot {
        val jaxbContext = JAXBContext.newInstance(Uniprot::class.java)
        val unmarshaller = jaxbContext.createUnmarshaller()
        val text= fetchUniProtXmlByID(accessionNumber)
        text.reader().use { it ->
            return unmarshaller.unmarshal(it) as Uniprot
        }
    }

    private fun fetchUniProtXmlByID(id: String): String {
        val url = urlTermplate.replace(uniprotIdPattern, id)
        return URL(url).readText()
    }


}