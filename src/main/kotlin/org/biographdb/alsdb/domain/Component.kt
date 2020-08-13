package org.biographdb.alsdb.domain

import arrow.core.orNull
import org.biographdb.alsdb.model.uniprot.Entry
import org.biographdb.alsdb.model.uniprot.ProteinType
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship


@NodeEntity(label = "ComponentList")
class ComponentList(@Id val uniprotId: String) {

    @Relationship(type = "HAS_COMPONENT")
    var components: MutableList<Component> = mutableListOf()

    companion object {

        fun resolveComponentListFromEntry(entry: Entry): ComponentList {
            var uniprotId = entry?.getAccessionList()?.get(0) ?: ""
            var componentList = ComponentList(uniprotId)
            entry.protein?.getComponentList()?.forEach { component ->
                componentList.components.add(Component.resolveComponentFromProteinComponent(component))
            }
            return componentList
        }
    }
}

@NodeEntity(label = "Component")
data class Component(@Id val fullName: String, val shortName: String = "") : EvidenceSupport() {
    fun isValid(): Boolean = fullName.isNotEmpty()

    companion object {

        fun resolveComponentFromProteinComponent(proteinComponent: ProteinType.Component): Component {
            val fullName = proteinComponent.recommendedName?.fullName?.value ?: ""
            var shortName = ""
            if (!proteinComponent.recommendedName?.getShortNameList().isNullOrEmpty()) {
                shortName = proteinComponent.recommendedName?.getShortNameList()?.get(0)?.value ?: ""
            }
            val component = Component(fullName, shortName)
            val evidenceBuildResult = EvidenceList.resolveEvidenceListFromEvidenceTypeList(
                proteinComponent.recommendedName?.getEcNumberList()
            )
            if (evidenceBuildResult.isRight()) {
                component.evidenceList = evidenceBuildResult.orNull()!!
            }
            return component
        }
    }

}
