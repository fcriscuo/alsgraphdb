/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.model.harmonizome

data class GeneDiseaseAssociations(
    val associations: List<Association>,
    val attribute: Attribute,
    val dataset: Dataset

)

