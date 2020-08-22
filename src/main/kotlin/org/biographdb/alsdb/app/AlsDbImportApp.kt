/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.app

import org.biographdb.alsdb.domain.Gene
import org.biographdb.alsdb.domain.UniProtProtein
import org.biographdb.alsdb.model.harmonizome.HarmonizomeGeneProteinGroup
import org.biographdb.alsdb.service.EvidenceNodeMapService
import org.biographdb.alsdb.service.FetchUniProtProteinService
import org.biographdb.alsdb.service.OgmDatabaseService
import org.biographdb.alsdb.service.fetchHarmonizomeGenesByDiseaseAssociation
import java.util.stream.Collectors


const val alsDiseaseSearchString = "amyotrophic+lateral+sclerosis"

class AlsDbImportApp(val disaseName: String) {

    fun resolveHarmonizomeGeneProteinGroups(): List<HarmonizomeGeneProteinGroup> =
        fetchHarmonizomeGenesByDiseaseAssociation(alsDiseaseSearchString)
            .stream()
            .map { hg -> HarmonizomeGeneProteinGroup.buildHarmonizomeGeneProteinGroup(hg) }
            .collect(Collectors.toList())

    // TODO: add null checks
    fun resolveUniProtProtein(geneProteinGroup: HarmonizomeGeneProteinGroup): UniProtProtein {
        val uniProtProtein =
            UniProtProtein.createUniProtProteinFromHarmonizomeProtein(geneProteinGroup.harmonizomeProteinList.get(0))
        uniProtProtein.gene = Gene.resolveGeneFromHarmonizomeGene(geneProteinGroup.harmonizomeGene)
        val uniprot = FetchUniProtProteinService.fetchUniProtDataByUniProtId(uniProtProtein.uniprotId)
        val entry = uniprot.resolveEntryList().get(0)
        EvidenceNodeMapService.constructEvidenceMap(entry)
        uniProtProtein.completeUniProtProteinObject(entry)
        return uniProtProtein
    }

    fun persistUniProtProtein(uniprotProtein: UniProtProtein) {
        val session = OgmDatabaseService.getSession()
        session.save(uniprotProtein)
    }
}

fun main() {
    AlsDbImportApp(alsDiseaseSearchString).apply {
        resolveHarmonizomeGeneProteinGroups().stream()
            .limit(10L)
            .map { hgpg -> resolveUniProtProtein(hgpg) }
            .forEach { uniprot -> persistUniProtProtein(uniprot) }
    }
    OgmDatabaseService.closeSessionFactory()
}

