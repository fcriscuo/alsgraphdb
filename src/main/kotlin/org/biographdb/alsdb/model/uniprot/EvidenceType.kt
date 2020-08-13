//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.04.03 at 08:09:53 AM PDT 
//
package org.biographdb.alsdb.model.uniprot

import java.math.BigInteger
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType

/**
 *
 * Describes the evidence for an annotation. No flat file equivalent.
 *
 *
 *
 * Java class for evidenceType complex type.
 *
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="evidenceType">
 * &lt;complexContent>
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 * &lt;sequence>
 * &lt;element name="source" type="{http://uniprot.org/uniprot}sourceType" minOccurs="0"/>
 * &lt;element name="importedFrom" type="{http://uniprot.org/uniprot}importedFromType" minOccurs="0"/>
 * &lt;/sequence>
 * &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 * &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 * &lt;/restriction>
 * &lt;/complexContent>
 * &lt;/complexType>
</pre> *
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "evidenceType", propOrder = ["source", "importedFrom"])
class EvidenceType {
    /**
     * Gets the value of the source property.
     *
     * @return
     * possible object is
     * [SourceType]
     */
    /**
     * Sets the value of the source property.
     *
     * @param value
     * allowed object is
     * [SourceType]
     */
    var source: SourceType? = null
    /**
     * Gets the value of the importedFrom property.
     *
     * @return
     * possible object is
     * [ImportedFromType]
     */
    /**
     * Sets the value of the importedFrom property.
     *
     * @param value
     * allowed object is
     * [ImportedFromType]
     */
    var importedFrom: ImportedFromType? = null

    /**
     * Gets the value of the type property.
     *
     * @return
     * possible object is
     * [String]
     */
    /**
     * Sets the value of the type property.
     *
     * @param value
     * allowed object is
     * [String]
     */
    @XmlAttribute(name = "type", required = true)
    var type: String? = null

    /**
     * Gets the value of the key property.
     *
     * @return
     * possible object is
     * [BigInteger]
     */
    /**
     * Sets the value of the key property.
     *
     * @param value
     * allowed object is
     * [BigInteger]
     */
    @XmlAttribute(name = "key", required = true)
    var key: BigInteger? = null

}