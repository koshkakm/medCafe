//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.22 at 04:05:04 PM EST 
//


package org.hl7.greencda.c32;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for encounter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="encounter">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:greencda:c32}entry">
 *       &lt;sequence>
 *         &lt;element name="dischargeDisposition">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="admissionType" type="{urn:hl7-org:greencda:c32}simpleCode"/>
 *         &lt;element name="facility" type="{urn:hl7-org:greencda:c32}organization"/>
 *         &lt;element name="reasonForVisit" type="{urn:hl7-org:greencda:c32}code" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "encounter", propOrder = {
    "dischargeDisposition",
    "admissionType",
    "facility",
    "reasonForVisit"
})
public class Encounter
    extends Entry
{

    @XmlElement(required = true)
    protected String dischargeDisposition;
    @XmlElement(required = true)
    protected SimpleCode admissionType;
    @XmlElement(required = true)
    protected Organization facility;
    protected Code reasonForVisit;

    /**
     * Gets the value of the dischargeDisposition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDischargeDisposition() {
        return dischargeDisposition;
    }

    /**
     * Sets the value of the dischargeDisposition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDischargeDisposition(String value) {
        this.dischargeDisposition = value;
    }

    /**
     * Gets the value of the admissionType property.
     * 
     * @return
     *     possible object is
     *     {@link SimpleCode }
     *     
     */
    public SimpleCode getAdmissionType() {
        return admissionType;
    }

    /**
     * Sets the value of the admissionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimpleCode }
     *     
     */
    public void setAdmissionType(SimpleCode value) {
        this.admissionType = value;
    }

    /**
     * Gets the value of the facility property.
     * 
     * @return
     *     possible object is
     *     {@link Organization }
     *     
     */
    public Organization getFacility() {
        return facility;
    }

    /**
     * Sets the value of the facility property.
     * 
     * @param value
     *     allowed object is
     *     {@link Organization }
     *     
     */
    public void setFacility(Organization value) {
        this.facility = value;
    }

    /**
     * Gets the value of the reasonForVisit property.
     * 
     * @return
     *     possible object is
     *     {@link Code }
     *     
     */
    public Code getReasonForVisit() {
        return reasonForVisit;
    }

    /**
     * Sets the value of the reasonForVisit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Code }
     *     
     */
    public void setReasonForVisit(Code value) {
        this.reasonForVisit = value;
    }

}