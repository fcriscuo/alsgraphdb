package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.Entry
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity(label = "AccessionList")
class AccessionList(@Id val uniProtId: String) {

    @Relationship(type = "HAS_ACCESSION")
    var accessions: MutableList<Accession> = mutableListOf<Accession>()

    companion object {
        fun resolveAccessionListFromEntry(entry: Entry): AccessionList {
            var uniprotId = entry?.getAccessionList()?.get(0) ?: ""
            var accList = AccessionList(uniprotId)
            entry?.getAccessionList()?.forEach {
                accList.accessions.add(Accession(it))
            }
            return accList
        }

    }
}