/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.model.harmonizome

import org.biographdb.alsdb.service.fetchHarmonizomeProteins
import java.lang.StringBuilder

/*
Represents a HarmonizomeGene and related HarmonizomeProteins within a
single entity
The protein attribute within the HarmonizomeGene object is not the equivalent
of the HarmonizomeProtein object
 */
data class HarmonizomeGeneProteinGroup( val harmonizomeGene: HarmonizomeGene) {
     var harmonizomeProteinList: MutableList<HarmonizomeProtein> = mutableListOf()

    override fun toString(): String{
        val sb = StringBuilder()
        sb.append("HarmonizomeGene: ${harmonizomeGene.name} \n")
        sb.append("        description: ${harmonizomeGene.description}\n")
        harmonizomeProteinList.forEach { hp -> sb.append(" Protein ${hp.uniprotId}  ${hp.name}") }
        return sb.toString()
    }
    fun displayHarmonizomeGene() {
        harmonizomeGene.apply {
            println("HarminizomeGene  name: ${name}    symbol: ${symbol}")
            println("                 description: ${description}  ")
            println("                  NCBI url : ${ncbiEntrezGeneUrl}")
            println("          synonym count: ${synonyms.size}")
            proteins.forEach{protein -> protein.apply{
                println("      protein: ${protein.symbol}")
            }}
        }
    }

    fun displayHarmonizomeProtein() {
        harmonizomeProteinList.forEach {protein ->
            protein.apply {
                println("Protein UniProtId $uniprotId")
                println("          name $name")
                println("          description $description")
                println("          symbol: $symbol")
                println("          URL: $uniprotUrl")
            }
            protein.gene.apply {
                println("    Gene symbol $symbol")
                println("          href   $href")
            }
        }
    }

    companion object {
        fun buildHarmonizomeGeneProteinGroup( harmonizomeGene:HarmonizomeGene ): HarmonizomeGeneProteinGroup {
            val harmonizomeGeneProteinGroup = HarmonizomeGeneProteinGroup(harmonizomeGene)
            harmonizomeGeneProteinGroup.harmonizomeProteinList.addAll(
               fetchHarmonizomeProteins(harmonizomeGene)
            )
            return harmonizomeGeneProteinGroup
        }
    }

}