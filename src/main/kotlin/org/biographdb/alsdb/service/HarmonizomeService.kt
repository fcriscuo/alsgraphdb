/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.service

import com.google.gson.Gson
import org.biographdb.alsdb.model.harmonizome.*
import org.biographdb.alsdb.model.uniprot.Entry
import org.biographdb.alsdb.model.uniprot.Uniprot
import java.net.URL
import java.util.stream.Collectors
import javax.xml.bind.JAXBContext
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime


/*
Represents a collection of functions to interact with the Harmonizome Web service
 */

const val harmonizomeBaseUrl = "http://amp.pharm.mssm.edu/Harmonizome"
const val harmonizomeUrlDiseaseTemplate = harmonizomeBaseUrl +
    "/api/1.0/gene_set/DISEASE_NAME/DISEASES+Text-mining+Gene-Disease+Assocation+Evidence+Scores"
const val harmonizomeAlsGenesUrl = harmonizomeBaseUrl +
        "/api/1.0/gene_set/amyotrophic+lateral+sclerosis/DISEASES+Text-mining+Gene-Disease+Assocation+Evidence+Scores"
const val uniprotUrlTemplate = "https://www.uniprot.org:443/uniprot/UNIPROTID.xml?include=yes"
const val uniprotStartTag = "<uniprot"
val gson = Gson()

fun generateHarmonizomeUrlForDisease(diseaseName: String): String =
    harmonizomeUrlDiseaseTemplate.replace("DISEASE_NAME", diseaseName)

fun fetchHarmonizomeGenesByDiseaseAssociation(diseaseUrl: String = harmonizomeAlsGenesUrl): MutableList<HarmonizomeGene> =
    fetchHarmonizomeDiseaseGeneAssoctions(diseaseUrl).stream()
        .map { assoc -> fetchHarmonizomeGene(assoc) }
        .filter { gene -> gene.proteins.isNotEmpty() }
        .collect(Collectors.toList())

fun fetchDiseasAssociatedUniProtEntries(entryLimit: Long = Long.MAX_VALUE): MutableList<Entry?>? =
    fetchHarmonizomeDiseaseGeneAssoctions(harmonizomeAlsGenesUrl).stream()
        .limit(entryLimit)
        .map { assoc -> fetchHarmonizomeGene(assoc) }
        .filter { gene -> gene.proteins.isNotEmpty() }
        .map { gene -> fetchHarmonizomeProteins(gene) }
        .flatMap { protein ->
            fetchUniProtEntryList(protein).stream()}
        .collect(Collectors.toList())


fun fetchHarmonizomeDiseaseGeneAssoctions(harmonizomeUrl: String): List<Association> {
    val json = URL(harmonizomeUrl).readText()
    val alsGeneAssociation = gson.fromJson(json, GeneDiseaseAssociations::class.java)
    println("ALS gene association count = ${alsGeneAssociation.associations.size}")
    return alsGeneAssociation.associations
}


fun fetchUniProtEntryList(proteinList: List<HarmonizomeProtein>): List<Entry?> {
    val tmpList = arrayListOf<Entry?>()
    proteinList.stream().forEach { protein ->
        val uniprotUrl = protein?.uniprotId?.let { uniprotUrlTemplate.replace("UNIPROTID", it) }
        println("Fetching uniprot: ${protein.uniprotId}  URL = $uniprotUrl")
        val xmlText = skipUniprotXmlHeader(URL(uniprotUrl).readText())
        val jaxbContext = JAXBContext.newInstance(Uniprot::class.java)
        val unmarshaller = jaxbContext.createUnmarshaller()
        xmlText.reader().use {
            val uniprot = unmarshaller.unmarshal(it) as Uniprot
            if (uniprot.getEntryList()?.isNotEmpty()!!) {
                tmpList.add(uniprot.resolveEntryList().get(0))  // only want the 1st entry
            }
        }
    }
    return tmpList.toList()
}

fun fetchHarmonizomeGene(association: Association): HarmonizomeGene {
    val url = harmonizomeBaseUrl + association.gene.href
    val json = URL(url).readText()
    return gson.fromJson(json, HarmonizomeGene::class.java)
}

/*
Return a List of HarmonizomeProtein instances associated with a
specified HarmonizomeGene. Ususally only 1 protein is associated with a gene
 */
fun fetchHarmonizomeProteins(gene: HarmonizomeGene): List<HarmonizomeProtein> =
    gene.proteins.stream()
        .map { protein -> harmonizomeBaseUrl + protein.href }
        .map { url -> URL(url).readText() }
        .map { json -> gson.fromJson(json, HarmonizomeProtein::class.java) }
        .collect(Collectors.toList())

private fun skipUniprotXmlHeader(xml: String): String =
    xml.substring(xml.indexOf(uniprotStartTag))



@ExperimentalTime
fun main() {
    // test fetching uniprot proteins associated with ALS
    val elapsed: Duration = measureTime {
        fetchHarmonizomeGenesByDiseaseAssociation()
            .stream()
            .map { hg -> HarmonizomeGeneProteinGroup.buildHarmonizomeGeneProteinGroup(hg) }
            .forEach { group -> println(group.toString()) }
    }
    println("Elapsed time in seconds = ${elapsed.inSeconds}")

}