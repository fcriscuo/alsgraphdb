/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.04.03 at 08:09:53 AM PDT 
//
package org.biographdb.alsdb.model.uniprot

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 *
 * Describes the source of the evidence, when it is not assigned by UniProt, but imported from an external database.
 *
 *
 *
 * Java class for importedFromType complex type.
 *
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="importedFromType">
 * &lt;complexContent>
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 * &lt;sequence>
 * &lt;element name="dbReference" type="{http://uniprot.org/uniprot}dbReferenceType"/>
 * &lt;/sequence>
 * &lt;/restriction>
 * &lt;/complexContent>
 * &lt;/complexType>
</pre> *
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "importedFromType", propOrder = ["dbReference"])
class ImportedFromType {
    /**
     * Gets the value of the dbReference property.
     *
     * @return
     * possible object is
     * [DbReferenceType]
     */
    /**
     * Sets the value of the dbReference property.
     *
     * @param value
     * allowed object is
     * [DbReferenceType]
     */
    @XmlElement(required = true)
    var dbReference: DbReferenceType? = null

}