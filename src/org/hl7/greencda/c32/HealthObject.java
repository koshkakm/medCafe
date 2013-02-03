//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.22 at 04:05:00 PM EST 
//


package org.hl7.greencda.c32;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:greencda:c32}entry">
 *       &lt;sequence>
 *         &lt;element name="administrationTiming">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="period" type="{urn:hl7-org:greencda:c32}quantity"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="institutionSpecified" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="route" type="{urn:hl7-org:greencda:c32}code" minOccurs="0"/>
 *         &lt;element name="dose" type="{urn:hl7-org:greencda:c32}quantity" minOccurs="0"/>
 *         &lt;element name="doseIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="site" type="{urn:hl7-org:greencda:c32}code" minOccurs="0"/>
 *         &lt;element name="doseRestriction" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="numerator" type="{urn:hl7-org:greencda:c32}quantity"/>
 *                   &lt;element name="denominator" type="{urn:hl7-org:greencda:c32}quantity"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="productForm" type="{urn:hl7-org:greencda:c32}code" minOccurs="0"/>
 *         &lt;element name="deliveryMethod" type="{urn:hl7-org:greencda:c32}code" minOccurs="0"/>
 *         &lt;element name="type" type="{urn:hl7-org:greencda:c32}code" minOccurs="0"/>
 *         &lt;element name="statusOfMedication" type="{urn:hl7-org:greencda:c32}code" minOccurs="0"/>
 *         &lt;element name="indication" type="{urn:hl7-org:greencda:c32}code" minOccurs="0"/>
 *         &lt;element name="vehicle" type="{urn:hl7-org:greencda:c32}code" minOccurs="0"/>
 *         &lt;element name="patientInstructions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fulfillmentInstructions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fulfillmentHistory" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="prescriptionNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="dispenseDate" type="{urn:hl7-org:greencda:c32}timestamp" minOccurs="0"/>
 *                   &lt;element name="quantityDispensed" type="{urn:hl7-org:greencda:c32}quantity" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="fillStatus">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;enumeration value="completed"/>
 *                       &lt;enumeration value="aborted"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="orderInformation" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="quantityOrdered" type="{urn:hl7-org:greencda:c32}quantity" minOccurs="0"/>
 *                   &lt;element name="orderedDateTime" type="{urn:hl7-org:greencda:c32}timestamp" minOccurs="0"/>
 *                   &lt;element name="expirationDateTime" type="{urn:hl7-org:greencda:c32}timestamp" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="orderNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="fills" type="{http://www.w3.org/2001/XMLSchema}integer" />
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
 * {"_id":"50d1e69dbd8009e351000244",
 * "codes":{"RxNorm":["314076"]},
 * "mood_code":"EVN",
 * "version":1,
 * "_type":"Medication",
 * "time":null,"start_time":1277438400,"end_time":null,"description":"ACE inhibitors","free_text":"ACE inhibitors","route":null,"dose":null,"site":null,"productForm":null,"deliveryMethod":null
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "codes",
    "mood_code",
    "version",
    "_type",
    "time",
    "start_time",
    "end_time",
    "description"
})
@XmlRootElement(name = "healthObject")
public class HealthObject
{

    @XmlElement(required = true)
    protected String id;
    //protected List<Code> codes;
    protected Codes codes;
    protected String mood_code;
    protected String version;
    protected String _type;
    protected String time;
    protected String start_time;
    protected String end_time;
    protected String description;
    
    public String getMood_code() {
		return mood_code;
	}

	public void setMood_code(String mood_code) {
		this.mood_code = mood_code;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	 
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String get_type() {
		return _type;
	}

	public void set_type(String _type) {
		this._type = _type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
		
	}

	public Codes getCodes() {
		return codes;
	}

	public void setCodes(Codes codes) {
		this.codes = codes;
	}

	
   

}
