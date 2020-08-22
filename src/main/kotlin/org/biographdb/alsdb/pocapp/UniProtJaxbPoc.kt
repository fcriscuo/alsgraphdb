/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.pocapp

import org.biographdb.alsdb.service.EvidenceNodeMapService
import org.biographdb.alsdb.service.FetchUniProtProteinService
import org.biographdb.alsdb.service.OgmDatabaseService

/**
 * Created by fcriscuo on 4/3/20.
 */
fun main() {

    val session = OgmDatabaseService.getSession()
    val uniprot = FetchUniProtProteinService.fetchUniProtDataByUniProtId("P05067")
    uniprot.getEntryList()?.forEach { entry ->
        run {
            val evidenceCount = EvidenceNodeMapService.constructEvidenceMap(entry)
            println("Evidence item count = $evidenceCount")
           // val uniProtProtein = UniProtProtein.createUniProtProteinObject(entry)
           // session.save(uniProtProtein)
            session.clear()
        }
    }
    OgmDatabaseService.closeSessionFactory()

}
