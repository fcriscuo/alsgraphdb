//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.04.03 at 08:09:53 AM PDT 
//
package org.biographdb.alsdb.model.uniprot

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType

/**
 *
 * Describes the evidence for the protein's existence. Equivalent to the flat file PE-line.
 *
 *
 *
 * Java class for proteinExistenceType complex type.
 *
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="proteinExistenceType">
 * &lt;complexContent>
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 * &lt;attribute name="type" use="required">
 * &lt;simpleType>
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 * &lt;enumeration value="evidence at protein level"/>
 * &lt;enumeration value="evidence at transcript level"/>
 * &lt;enumeration value="inferred from homology"/>
 * &lt;enumeration value="predicted"/>
 * &lt;enumeration value="uncertain"/>
 * &lt;/restriction>
 * &lt;/simpleType>
 * &lt;/attribute>
 * &lt;/restriction>
 * &lt;/complexContent>
 * &lt;/complexType>
</pre> *
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "proteinExistenceType")
class ProteinExistenceType {
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

}