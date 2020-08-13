package org.biographdb.alsdb.model.harmonizome

data class GeneDiseaseAssociations(
    val associations: List<Association>,
    val attribute: Attribute,
    val dataset: Dataset

)

