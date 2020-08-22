/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.harmonizome.HarmonizomeGene
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity

@NodeEntity(label = "Gene")
data class Gene( @Id val symbol: String, val name: String, val description:String="",
                 val entrezGeneUrl: String="")
{
    companion object {
        fun resolveGeneFromHarmonizomeGene(harmonizomeGene: HarmonizomeGene): Gene
          = Gene(harmonizomeGene.symbol, harmonizomeGene.name, harmonizomeGene.description,
                harmonizomeGene.ncbiEntrezGeneUrl)


    }

}