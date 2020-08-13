package org.biographdb.alsdb.domain


import org.biographdb.alsdb.model.uniprot.*

/**
 * Created by fcriscuo on 7/10/20.
 */

fun EvidenceType.isValid(): Boolean =
    !type.isNullOrBlank() && source != null && source!!.isValid()

fun DbReferenceType.isValid(): Boolean = !type.isNullOrBlank() && !id.isNullOrBlank()

fun SourceType.isValid(): Boolean =
    dbReference != null && dbReference!!.isValid()

fun EvidencedStringType.isValid(): Boolean = value.isNullOrEmpty()
        && getEvidenceList()!!.isEmpty()

fun ProteinType.isValid(): Boolean =
    !(recommendedName == null || recommendedName!!.fullName == null &&
            !recommendedName!!.fullName!!.isValid()) &&
            recommendedName!!.getShortNameList() != null && !recommendedName!!.getShortNameList()!!.isEmpty()

fun Entry.isValid(): Boolean =
    getAccessionList() != null && getAccessionList()!!.size > 0
            && protein != null && protein!!.isValid()


