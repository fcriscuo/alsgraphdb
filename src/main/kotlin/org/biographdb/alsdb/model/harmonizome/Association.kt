package org.biographdb.alsdb.model.harmonizome

data class Association(
    val gene: Gene,
    val standardizedValue: Double,
    val thresholdValue: Double
)