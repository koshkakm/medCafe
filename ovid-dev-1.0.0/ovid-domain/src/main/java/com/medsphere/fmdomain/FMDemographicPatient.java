package com.medsphere.fmdomain;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.medsphere.fileman.FMAnnotateFieldInfo;
import com.medsphere.fileman.FMField;
import com.medsphere.fileman.FMFile;
import com.medsphere.fileman.FMResultSet;

public class FMDemographicPatient extends FMPatient {

    private static Set<FMField> domainFields;
    private static FMFile fileInfo;
    private static Map<String, AnnotatedElement> domainJavaFields;
    private static Map<String, String> domainNumbers;

    static {
        domainJavaFields = getDomainJavaFields(FMDemographicPatient.class);
        domainFields = getFieldsInDomain(domainJavaFields);
        domainNumbers = getNumericMapping(FMDemographicPatient.class);
        fileInfo = new FMFile("2") { // need to use number because RPMS names file 2 'VA PATIENT'

            @Override
            public Collection<FMField> getFields() {
                return domainFields;
            }
        };
        fileInfo.setPack(true);

    }

    public static FMFile getFileInfoForClass() {
        return fileInfo;
    }

    @Override
    protected Set<FMField> getDomainFields() {
        return domainFields;
    }

    @Override
    protected Map<String, AnnotatedElement> getDomainJavaFields() {
        return domainJavaFields;
    }

    @Override
    protected Map<String, String> getNumericMapping() {
        return domainNumbers;
    }

    /*-------------------------------------------------------------
     * end static initialization
     *-------------------------------------------------------------*/

    public FMDemographicPatient() {
        super();
    }

    public FMDemographicPatient(FMResultSet results) {
        processResults(results);
    }

    protected String streetAddressLine1;
    @FMAnnotateFieldInfo(name = "STREET ADDRESS [LINE 2]", number = ".112", fieldType = FMField.FIELDTYPE.FREE_TEXT)
    protected String streetAddressLine2;
    @FMAnnotateFieldInfo(name = "STREET ADDRESS [LINE 3]", number = ".113", fieldType = FMField.FIELDTYPE.FREE_TEXT)
    protected String streetAddressLine3;
    @FMAnnotateFieldInfo(name = "CITY", number = ".114", fieldType = FMField.FIELDTYPE.FREE_TEXT)
    protected String city;
    protected Integer state;
    @FMAnnotateFieldInfo(name = "ZIP CODE", number = ".116", fieldType = FMField.FIELDTYPE.FREE_TEXT)
    protected String zipCode;
    @FMAnnotateFieldInfo(name = "MOTHER'S MAIDEN NAME", number = ".2403", fieldType = FMField.FIELDTYPE.FREE_TEXT)
    protected String mothersMaidenName;
    @FMAnnotateFieldInfo(name = "PHONE NUMBER [RESIDENCE]", number = ".131", fieldType = FMField.FIELDTYPE.FREE_TEXT)
    protected String phoneNumberResidence;
    @FMAnnotateFieldInfo(name = "PHONE NUMBER [WORK]", number = ".132", fieldType = FMField.FIELDTYPE.FREE_TEXT)
    protected String phoneNumberWork;
    @FMAnnotateFieldInfo(name = "EMAIL ADDRESS", number = ".133", fieldType = FMField.FIELDTYPE.FREE_TEXT)
    protected String emailAddress;
    @FMAnnotateFieldInfo(name = "PHONE NUMBER [CELLULAR]", number = ".134", fieldType = FMField.FIELDTYPE.FREE_TEXT)
    protected String phoneNumberCellular;
    @FMAnnotateFieldInfo(name = "PAGER NUMBER", number = ".135", fieldType = FMField.FIELDTYPE.FREE_TEXT)
    protected String pagerNumber;

    @FMAnnotateFieldInfo(name = "MARITAL STATUS", number = ".05", fieldType = FMField.FIELDTYPE.POINTER_TO_FILE)
    protected Integer maritalStatus;
    @FMAnnotateFieldInfo(name = "RACE", number = ".06", fieldType = FMField.FIELDTYPE.SUBFILE)
    protected FMRaceInformation raceInformation;
    @FMAnnotateFieldInfo(name = "ETHNICITY INFORMATION", number = "6", fieldType = FMField.FIELDTYPE.SUBFILE)
    protected FMEthnicity ethnicity;
    @FMAnnotateFieldInfo(name = "RELIGIOUS PREFERENCE", number = ".08", fieldType = FMField.FIELDTYPE.SUBFILE)
    protected Integer religiousPreference;

    @FMAnnotateFieldInfo(name = "STREET ADDRESS [LINE 1]", number = ".111", fieldType = FMField.FIELDTYPE.FREE_TEXT)
    public String getStreetAddressLine1() {
        return streetAddressLine1;
    }

    public void setStreetAddressLine1(String streetAddressLine1) {
        this.streetAddressLine1 = streetAddressLine1;
    }

    public String getStreetAddressLine2() {
        return streetAddressLine2;
    }

    public String getStreetAddressLine3() {
        return streetAddressLine3;
    }

    public String getCity() {
        return city;
    }

    @FMAnnotateFieldInfo(name = "STATE", number = ".115", fieldType = FMField.FIELDTYPE.POINTER_TO_FILE)
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        setDomainValue("state", state, ".115");
    }

    public String getStateValue() {
        return getValue(".115");
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getMothersMaidenName() {
        return mothersMaidenName;
    }

    public String getPhoneNumberResidence() {
        return phoneNumberResidence;
    }

    public String getPhoneNumberWork() {
        return phoneNumberWork;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumberCellular() {
        return phoneNumberCellular;
    }

    public String getPagerNumber() {
        return pagerNumber;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public String getMaritalStatusValue() {
        return getValue("MARITAL STATUS");
    }

    public FMRaceInformation getRaceInformation() {
        if (raceInformation==null) {
            raceInformation = new FMRaceInformation();
            raceInformation.setParent( this );
        }
        return raceInformation;
    }

    public FMEthnicity getEthnicity() {
        if (ethnicity==null) {
            ethnicity = new FMEthnicity();
            ethnicity.setParent( this );
        }
        return ethnicity;
    }

    public Integer getReligiousPreference() {
        return religiousPreference;
    }

    public String getReligiousPreferenceValue() {
        return getValue(".08");
    }

    @Override
    public String toString() {
        return super.toString()
        + " streetAddressLine1=["+getStreetAddressLine1()+"]"
        + " streetAddressLine2=["+getStreetAddressLine2()+"]"
        + " streetAddressLine3=["+getStreetAddressLine3()+"]"
        + " city=["+getCity()+"]"
        + " state=["+getStateValue()+"]"
        + " zipCode=["+getZipCode()+"]"
        + " phoneNumberResidence=["+getPhoneNumberResidence()+"]"
        + " phoneNumberWork=["+getPhoneNumberWork()+"]"
        + " emailAddress=["+getEmailAddress()+"]"
        + " phoneNumberCellular=["+getPhoneNumberCellular()+"]"
        + " phoneNumberPagerNumber=["+getPagerNumber()+"]"
        + " maritalStatus=["+getMaritalStatus()+"]"
        + " race=["+getRaceInformation()+"]"
        + " religiousPreference=["+getReligiousPreference()+"]"
        ;


    }
}