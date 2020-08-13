package org.biographdb.pocapp

import org.biographdb.alsdb.domain.UniProtProtein
import org.biographdb.alsdb.model.uniprot.Uniprot
import org.biographdb.alsdb.service.EvidenceNodeMapService
import org.biographdb.alsdb.service.OgmDatabaseService
import java.net.URL
import javax.xml.bind.JAXBContext

/**
 * Created by fcriscuo on 4/3/20.
 */
fun main() {
    //val text = FileReader("/tmp/P07900.xml").readText()
    val text = URL(
        "https://www.uniprot.org:443" +
                "/uniprot/P05067.xml?include=yes"
    ).readText()
    println("Text: ${text.subSequence(0, 140)}")
    val session = OgmDatabaseService.getSession()
    val jaxbContext = JAXBContext.newInstance(Uniprot::class.java)
    val unmarshaller = jaxbContext.createUnmarshaller()
    text.reader().use { it ->
        val uniprot = unmarshaller.unmarshal(it) as Uniprot
        uniprot.getEntryList()?.forEach { entry ->
            run {
                val evidenceCount = EvidenceNodeMapService.constructEvidenceMap(entry)
                println("Evidence item count = $evidenceCount")
                val uniProtProtein = UniProtProtein.createUniProtProteinObject(entry)
                session.save(uniProtProtein)
                session.clear()
            }
        }
    }
    OgmDatabaseService.closeSessionFactory()

}
