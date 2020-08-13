package org.biographdb.alsdb.service

import arrow.core.Either


/*
Represents a service that will support a queue of UniProt identifiers (e.g. P05067)
whose data need to fetched.
The service will also maintain a set of UniProt identifiers that have already been fetched
to avoid multiple requests for the same data
UniProt identifers will be classified as either disease-specific or disease-related. The former list
represents identifers that are directly associated with a specific disease. The later classification
contains proteins that have interactions with proteins in the first group. Only a limited amount of
data is retrieved for disease-related proteins.
 */
object UniProtIdQueueService {
    // working queue
    val uniProtIdQueue = java.util.ArrayDeque<String>()

    var diseaseSpecificIdSet: MutableSet<String> = mutableSetOf<String>()
    var processedSet: MutableSet<String> = mutableSetOf()

    fun registerDiseaseSpecificUniProtId(id: String): Boolean {
        if (!uniProtIdQueue.contains(id) && !processedSet.contains(id)) {
            diseaseSpecificIdSet.add(id)
            uniProtIdQueue.add(id)
            return true
        }
        return false
    }

    fun registerDiseaseRelatedUniProtId(id: String): Boolean {
        if (!uniProtIdQueue.contains(id) && !processedSet.contains(id)) {
            uniProtIdQueue.add(id)
            return true
        }
        return false
    }

    // retrieve the next UniProt id and mark as processed
    fun retreiveNextUniProtId(): Either<String, Pair<String, Boolean>> {
        if (uniProtIdQueue.isEmpty()) {
            return Either.left("UniProt ID queue is empty")
        }
        val id = uniProtIdQueue.first
        processedSet.add(id)
        if (diseaseSpecificIdSet.contains(id)) {
            return Either.right(Pair<String, Boolean>(id, true))
        }
        // this is not a disease specific protein, signal a limit fetch is required
        return Either.right(Pair<String, Boolean>(id, false))
    }


}