/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.domain

import org.biographdb.alsdb.model.uniprot.Entry
import org.biographdb.alsdb.model.uniprot.ProteinType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.util.*
import kotlin.contracts.ExperimentalContracts


@NodeEntity(label = "ComponentList")
class ComponentList(@Id val uniprotId: String) {

    @Relationship(type = "HAS_COMPONENT")
    var components: MutableList<Component> = mutableListOf()

    companion object {
        @ExperimentalStdlibApi
        @ExperimentalContracts
        fun resolveComponentListFromEntry(entry: Entry): ComponentList {
            var uniprotId = entry?.getAccessionList()?.get(0) ?: ""
            var componentList = ComponentList(uniprotId)
            entry.protein?.getComponentList()?.forEach { component ->
                componentList.components.add(Component.buildFromProteinTypeComponent(component))
            }
            return componentList
        }
    }
}

@NodeEntity(label = "Component")
class Component() : UniProtNameTypes() {
    @Id
    val uuid = UUID.randomUUID().toString()

    companion object {
        @ExperimentalStdlibApi
        @ExperimentalContracts
        fun buildFromProteinTypeComponent(proteinComponent: ProteinType.Component): Component {
            val component = Component()
            component.apply {
                if (proteinComponent.recommendedName != null) {
                    recommendedName = RecommendedName.buildFromRecommendedName(proteinComponent.recommendedName!!)
                }
                if (proteinComponent.submittedName != null) {
                    proteinComponent.submittedName?.forEach { sn ->
                        submittedNames.add(SubmittedName.buildFromSubmittedName(sn))
                    }
                }
                if (proteinComponent.alternativeName != null){
                    proteinComponent.alternativeName?.forEach{ an ->
                        alternaltiveNames.add(AlternativeName.buildFromAlternativeNameType(an))
                    }
                }


            }



            return component
        }
    }
}


