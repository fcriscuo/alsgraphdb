package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.InteractantType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

/*
Represents a protein to tprotein interaction.
An instance my have a relationship to another UniProtProtein node
 */
@NodeEntity(label = "Interaction")
data class Interaction(@Id val intactId: String, val id: String, val label: String = "") {
    @Relationship(type = "HAS_UNIPROT_PROTEIN")
    lateinit var uniProtProtein: UniProtProtein
    fun isvalid(): Boolean = intactId.isNotEmpty() && id.isNotEmpty()

    companion object {
        fun buildFromInteractType(interactantType: InteractantType): Interaction {
            val intactId = interactantType.intactId ?: ""
            val id = interactantType.id ?: ""
            val label = interactantType.label ?: ""

            return Interaction(intactId, id, label)

        }

    }
}