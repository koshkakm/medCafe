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
 * <p>Java class for GoalType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GoalType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:astm-org:CCR}EncounterType">
 *       &lt;sequence>
 *         &lt;element name="Milestones" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Outcome" type="{urn:astm-org:CCR}CCRCodedDataObjectType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GoalType", propOrder = {
    "milestones"
})
public class GoalType
    extends EncounterType
{

    @XmlElement(name = "Milestones")
    protected GoalType.Milestones milestones;

    /**
     * Gets the value of the milestones property.
     * 
     * @return
     *     possible object is
     *     {@link GoalType.Milestones }
     *     
     */
    public GoalType.Milestones getMilestones() {
        return milestones;
    }

    /**
     * Sets the value of the milestones property.
     * 
     * @param value
     *     allowed object is
     *     {@link GoalType.Milestones }
     *     
     */
    public void setMilestones(GoalType.Milestones value) {
        this.milestones = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Outcome" type="{urn:astm-org:CCR}CCRCodedDataObjectType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "outcome"
    })
    public static class Milestones {

        @XmlElement(name = "Outcome", required = true)
        protected List<CCRCodedDataObjectType> outcome;

        /**
         * Gets the value of the outcome property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the outcome property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOutcome().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CCRCodedDataObjectType }
         * 
         * 
         */
        public List<CCRCodedDataObjectType> getOutcome() {
            if (outcome == null) {
                outcome = new ArrayList<CCRCodedDataObjectType>();
            }
            return this.outcome;
        }

    }

}