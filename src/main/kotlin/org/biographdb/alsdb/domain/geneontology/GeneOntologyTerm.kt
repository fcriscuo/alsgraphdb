/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain.geneontology

import org.neo4j.ogm.annotation.*


/**
 * Created by fcriscuo on 9/20/20.
 */

@NodeEntity(label="GeneOntologyTerm")
class GeneOntologyTerm (@Id val goId: String) {
    var name:String = ""
    var namespace: String = ""
    var def: String =""
    @Relationship(type = "HAS_GO_SYNONYM")
    var synonyms: MutableList<GoSynonym> = mutableListOf()
    @Relationship (type = "HAS_GO_XREF")
    var xrefs: MutableList<GoXref> = mutableListOf()
    var comment: String = ""
    var isObsolete: Boolean = false

    fun isValid(): Boolean = name.isNotEmpty() && namespace.isNotEmpty() &&
            def.isNotEmpty() && !isObsolete

    fun display() {
        println("Gene Ontology Term: ${goId}")
        println("               name: ${name}")
        println("          namespace: ${namespace}")
        println("                def: ${def}")
        synonyms.forEach { syn -> println("   synonym: $syn") }
        xrefs.forEach { xref -> println("     xref: $xref") }
        if(comment.isNotEmpty()){
            println("     comment: $comment")
        }}

}
fun removeTagFromLine(line:String, tag: String): String =
        line.replace(tag,"").trim()

fun String.extractQuotedString(): String {
    val startIndex: Int = this.indexOf('"')+1
    val lastIndex:Int = this.lastIndexOf('"')
    return this.substring(startIndex,lastIndex)
}
@NodeEntity(label="GeneOntologySynonym")
data class GoSynonym (val synonym: String, val type:String ="") {
    @Id
    @GeneratedValue
    private val id: Long? = null

    companion object {
        fun resolveSynonymFromLine(line: String): GoSynonym {
            val synonym = line.extractQuotedString()
            val type = resolveSynonymTypeFromLine(line)
            return GoSynonym(synonym, type)
        }
        fun resolveSynonymTypeFromLine(line: String): String = line.substring(line.lastIndexOf('"') + 2).split(" ").get(0)
    }
}
@NodeEntity(label="GeneOntologyXref")
data class GoXref (val referenceSource: String, val referenceIdentifier: String, val referenceDef: String = "" ) {
    @Id
    @GeneratedValue
    private val id: Long? = null

    companion object {
        fun resolveGoXrefFromLine(line: String): GoXref {
            val xrefData = removeTagFromLine(line, GeneOntologyFunctions.XREF_TAG)
            val xrefParts = xrefData.split(" ")
            val idParts = xrefParts.get(0).split(":")
            val source = idParts.get(0)
            val id = idParts.get(1)
            val def = if (xrefParts.size > 1) line.extractQuotedString() else ""
            return GoXref(source, id, def)
        }
    }
}
