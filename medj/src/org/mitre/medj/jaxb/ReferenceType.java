//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.11 at 04:03:45 PM EDT 
//


package org.mitre.medj.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReferenceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReferenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReferenceObjectID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DateTime" type="{urn:astm-org:CCR}DateTimeType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{urn:astm-org:CCR}Type" minOccurs="0"/>
 *         &lt;element ref="{urn:astm-org:CCR}Description" minOccurs="0"/>
 *         &lt;element name="Source" type="{urn:astm-org:CCR}ActorReferenceType" maxOccurs="unbounded"/>
 *         &lt;element ref="{urn:astm-org:CCR}Locations" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferenceType", propOrder = {
    "referenceObjectID",
    "dateTime",
    "type",
    "description",
    "source",
    "locations"
})
public class ReferenceType {

    @XmlElement(name = "ReferenceObjectID", required = true)
    protected String referenceObjectID;
    @XmlElement(name = "DateTime")
    protected List<DateTimeType> dateTime;
    @XmlElement(name = "Type")
    protected CodedDescriptionType type;
    @XmlElement(name = "Description")
    protected CodedDescriptionType description;
    @XmlElement(name = "Source", required = true)
    protected List<ActorReferenceType> source;
    @XmlElement(name = "Locations")
    protected Locations locations;

    /**
     * Gets the value of the referenceObjectID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceObjectID() {
        return referenceObjectID;
    }

    /**
     * Sets the value of the referenceObjectID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceObjectID(String value) {
        this.referenceObjectID = value;
    }

    /**
     * Gets the value of the dateTime property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dateTime property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDateTime().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DateTimeType }
     * 
     * 
     */
    public List<DateTimeType> getDateTime() {
        if (dateTime == null) {
            dateTime = new ArrayList<DateTimeType>();
        }
        return this.dateTime;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link CodedDescriptionType }
     *     
     */
    public CodedDescriptionType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodedDescriptionType }
     *     
     */
    public void setType(CodedDescriptionType value) {
        this.type = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link CodedDescriptionType }
     *     
     */
    public CodedDescriptionType getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodedDescriptionType }
     *     
     */
    public void setDescription(CodedDescriptionType value) {
        this.description = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the source property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActorReferenceType }
     * 
     * 
     */
    public List<ActorReferenceType> getSource() {
        if (source == null) {
            source = new ArrayList<ActorReferenceType>();
        }
        return this.source;
    }

    /**
     * Gets the value of the locations property.
     * 
     * @return
     *     possible object is
     *     {@link Locations }
     *     
     */
    public Locations getLocations() {
        return locations;
    }

    /**
     * Sets the value of the locations property.
     * 
     * @param value
     *     allowed object is
     *     {@link Locations }
     *     
     */
    public void setLocations(Locations value) {
        this.locations = value;
    }

}