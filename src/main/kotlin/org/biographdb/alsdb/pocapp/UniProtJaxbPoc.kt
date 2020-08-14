package org.biographdb.pocapp

import org.biographdb.alsdb.domain.UniProtProtein
import org.biographdb.alsdb.model.uniprot.Uniprot
import org.biographdb.alsdb.service.EvidenceNodeMapService
import org.biographdb.alsdb.service.FetchUniProtProteinService
import org.biographdb.alsdb.service.OgmDatabaseService
import java.net.URL
import javax.xml.bind.JAXBContext

/**
 * Created by fcriscuo on 4/3/20.
 */
fun main() {

    val session = OgmDatabaseService.getSession()
    val uniprot = FetchUniProtProteinService.fetchUniProtDataByPrimaryAccessionNumber("P05067")
    uniprot.getEntryList()?.forEach { entry ->
        run {
            val evidenceCount = EvidenceNodeMapService.constructEvidenceMap(entry)
            println("Evidence item count = $evidenceCount")
            val uniProtProtein = UniProtProtein.createUniProtProteinObject(entry)
            session.save(uniProtProtein)
            session.clear()
        }
    }
    OgmDatabaseService.closeSessionFactory()

}
