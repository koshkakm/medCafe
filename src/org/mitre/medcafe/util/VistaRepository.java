package org.mitre.medcafe.util;

// import org.mitre.hdata.hrf.core.*;
import com.medsphere.fileman.*;
import com.medsphere.fmdomain.*;
import com.medsphere.ovid.domain.ov.*;
import com.medsphere.ovid.model.domain.patient.*;
import com.medsphere.vistalink.*;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.*;
import org.json.*;
import org.projecthdata.hdata.schemas._2009._06.allergy.*;
import org.projecthdata.hdata.schemas._2009._06.core.*;
import org.projecthdata.hdata.schemas._2009._06.patient_information.*;
import org.projecthdata.hdata.schemas._2009._06.medication.*;
import org.projecthdata.hdata.schemas._2009._06.condition.*;
import com.medsphere.vistarpc.RPCConnection;
import com.medsphere.vistarpc.RPCException;

import java.util.Collection;
import java.util.ArrayList;

/**
 *  This class implements an interface to a back-end Vista repostory.  The Medsphere (http://www.medsphere.com/) version of VistA, named OpenVista, is
 *  used via the medsphere ovid library
 */
public class VistaRepository extends Repository {

    public final static String KEY = VistaRepository.class.getName();
    public final static Logger log = Logger.getLogger(KEY);
    // static{log.setLevel(Level.FINER);}
    protected static VistaLinkPooledConnectionFactory factory = null;
    protected RPCConnection conn = null;

    public VistaRepository() {
        type = "VistA";
    }

    /**
     *  Given a patient id, get the patient info
     */
    public Patient getPatient(String id) {
        try {
            FMPatientContact filemanPat = null;
            FMPatientContact[] patList = null;
            PatientRepository patientRepository = null;
            if (setConnection()) {
                patientRepository = new PatientRepository(conn);
                Collection<String> iens = new ArrayList<String>();
                iens.add(id);
                Collection<FMPatientContact> patColl = patientRepository.getContacts(iens);
                if (patColl.size() == 0) {
                    return null;
                }
                patList = patColl.toArray(new FMPatientContact[0]);
                filemanPat = patList[0];
                patientRepository.fetchNameComponents(filemanPat);
                log.finer("\t" + patList[0].getIEN());
                /*            for (FMField field : filemanPat.getFields()) {
                if (filemanPat.getValue(field.getName()) != null) {
                log.finer("\t" +field.getName() + ": " + filemanPat.getValue(field.getName()));
                }
                }   */
            }


            if (filemanPat == null) {
                return null;
            }
            log.finer(filemanPat.getName());
            Patient ret = new Patient();

            //set  name
            Name name = new Name();
            List<String> given = name.getGiven();
            given.add(filemanPat.getGivenName());
            given.add(filemanPat.getMiddleName());
            name.setSuffix(filemanPat.getSuffix());
            name.setLastname(filemanPat.getFamilyName());
            name.setTitle(filemanPat.getPrefix());
            ret.setName(name);

            // set address
            List<Address> addressList = ret.getAddress();
            Address address = new Address();
            address.setCity(filemanPat.getCity());
            address.setStateOrProvince(filemanPat.getStateValue());
            address.setZip(filemanPat.getZip4());
            List<String> streetAddresses = address.getStreetAddress();
            streetAddresses.add(filemanPat.getStreetAddressLine1());
            String streetAddress = filemanPat.getStreetAddressLine2();
            if (stringExists(streetAddress)) {
                streetAddresses.add(streetAddress);
                streetAddress = filemanPat.getStreetAddressLine3();
                if (stringExists(streetAddress)) {
                    streetAddresses.add(streetAddress);
                }
            }

            addressList.add(address);


            //phone numbers
            List<Telecom> teleList = ret.getTelecom();

            setPhoneNumber(teleList, filemanPat.getPhoneNumberResidence(), "phone-landline", "home");
            setPhoneNumber(teleList, filemanPat.getPhoneNumberWork(), "phone-landline", "work");
            setPhoneNumber(teleList, filemanPat.getPhoneNumberCellular(), "phone-cell", "other");
            setPhoneNumber(teleList, filemanPat.getPagerNumber(), "phone-pager", "other");
            setPhoneNumber(teleList, filemanPat.getEmailAddress(), "email", "other");

            ret.setId(filemanPat.getIEN());


            //birthplace
            if (stringExists(filemanPat.getBirthCity())) {
                Address birthPlace = new Address();
                birthPlace.setCity(filemanPat.getBirthCity());
                birthPlace.setStateOrProvince(filemanPat.getBirthStateValue().toString());
                ret.setBirthPlace(birthPlace);
            }

            // marital status
            String marStatusDesc = filemanPat.getMaritalStatusValue();
            if (stringExists(marStatusDesc)) {
                MaritalStatus marStatus = new MaritalStatus();
                marStatus.setValue(marStatusDesc);
                ret.setMaritialStatus(marStatus);
            }

            //Race
            Collection<FMRaceInformation> patRaceList = patientRepository.getRaceInformation(filemanPat);

            List<Race> raceList = ret.getRace();
            for (FMRaceInformation raceInfo : patRaceList) {
                Race race = new Race();
                race.setValue(raceInfo.getRaceInformationValue());
                raceList.add(race);
            }

            //Guardian info
            if (filemanPat.getAge() < 18 || filemanPat.getCivilGuardianDateRuledIncompetent() != null
                    || filemanPat.getIncompVADate() != null) {

                Person guardian = new Person();

                Name guardName = guardian.getName();
                String civilGuardName = filemanPat.getGuardianCivil();
                String vaGuardName = filemanPat.getVaGuardian();
                if (stringExists(civilGuardName)) {
                    String[] nameParts = civilGuardName.split(",");
                    guardName.setLastname(nameParts[0]);
                    given = guardName.getGiven();
                    for (int i = 1; i < nameParts.length; i++) {
                        given.add(nameParts[i]);
                    }
                    guardian.setName(guardName);
                    addressList = guardian.getAddress();
                    address = new Address();
                    address.setCity(filemanPat.getCivilGuardianCity());
                    address.setStateOrProvince(filemanPat.getCivilGuardianStateValue());
                    address.setZip(filemanPat.getCivilGuardianZip4());
                    streetAddresses = address.getStreetAddress();
                    streetAddresses.add(filemanPat.getCivilGuardianStreetAddress1());
                    streetAddress = filemanPat.getCivilGuardianStreetAddress2();
                    if (stringExists(streetAddress)) {
                        streetAddresses.add(streetAddress);
                    }
                    addressList.add(address);
                    teleList = guardian.getTelecom();
                    setPhoneNumber(teleList, filemanPat.getCivilGuardianPhone(), "other", "other");

                } // if no civil guardian listed assume next of kin is guardian for
                // patient who is underage
                else if (filemanPat.getAge() < 18) {
                    if (stringExists(filemanPat.getNokNameOfPrimary())) {
                        String[] nameParts = filemanPat.getNokNameOfPrimary().split(",");
                        guardName.setLastname(nameParts[0]);
                        given = guardName.getGiven();
                        for (int i = 1; i < nameParts.length; i++) {
                            given.add(nameParts[i]);
                        }
                        guardian.setName(guardName);
                        addressList = guardian.getAddress();
                        address = new Address();
                        address.setCity(filemanPat.getNokCity());
                        address.setStateOrProvince(filemanPat.getNokStateValue());
                        address.setZip(filemanPat.getNokZip4());
                        streetAddresses = address.getStreetAddress();
                        streetAddresses.add(filemanPat.getNokStreetAddressLine1());
                        streetAddress = filemanPat.getNokStreetAddressLine2();
                        if (stringExists(streetAddress)) {
                            streetAddresses.add(streetAddress);
                        }
                        addressList.add(address);
                        teleList = guardian.getTelecom();
                        setPhoneNumber(teleList, filemanPat.getNokPhoneNumber(), "phone-landline","home");
                        setPhoneNumber(teleList, filemanPat.getNokWorkPhoneNumber(), "phone-landline", "work");

                    }
                }
                        else if (stringExists(vaGuardName)) {

                        String[] nameParts = vaGuardName.split(",");
                        guardName.setLastname(nameParts[0]);
                        given = guardName.getGiven();
                        for (int i = 1; i < nameParts.length; i++) {
                            given.add(nameParts[i]);
                        }
                        guardian.setName(guardName);
                        addressList = guardian.getAddress();
                        address = new Address();
                        address.setCity(filemanPat.getVaGuardianCity());
                        address.setStateOrProvince(filemanPat.getVaGuardianStateValue());
                        address.setZip(filemanPat.getVaGuardianZip4());
                        streetAddresses = address.getStreetAddress();
                        streetAddresses.add(filemanPat.getVaGuardianStreetAddress1());
                        streetAddress = filemanPat.getVaGuardianStreetAddress2();
                        if (stringExists(streetAddress)) {
                            streetAddresses.add(streetAddress);
                        }
                        addressList.add(address);
                        teleList = guardian.getTelecom();
                        setPhoneNumber(teleList, filemanPat.getVaGuardianPhone(), "other", "other");
                }
                ret.setGuardian(guardian);
            }

            //set gender
            Gender g = new Gender();
            g.setDisplayName(filemanPat.getSex());
            ret.setGender(g);
            log.finer(String.valueOf(filemanPat.getDob()));

            //set DOB
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(filemanPat.getDob());
            DatatypeFactory factory = DatatypeFactory.newInstance();
            ret.setBirthtime(factory.newXMLGregorianCalendar(cal));
            return ret;
        } catch (OvidDomainException e) {
            log.log(Level.SEVERE, "Error retrieving patient " + id, e);
            return null;
        } catch (DatatypeConfigurationException e) {
            log.throwing(KEY, "Error assembling return object", e);
            return null;
        } finally {
            closeConnection();
        }

    }

    /**
     *  Get a list of patient identifiers
     */
    public Map<String, String> getPatients() {
        Map<String, String> ret = new HashMap<String, String>();
        try {
            if (setConnection()) {
                for (FMPatient lpatient : new PatientRepository(conn).getAllPatients()) {
                    // ret.add( lpatient.getEnterprisePatientIdentifier() );
                    // log.finer(lpatient.getIEN());
                    //ret.put( new String[]{"id",lpatient.getIEN()}, new String[]{"name", lpatient.getName()} );
                    ret.put(lpatient.getIEN(), lpatient.getName());
                }
            } else {
                return null;
            }
            return ret;
        } catch (OvidDomainException e) {
            log.log(Level.SEVERE, "Error retrieving patient list", e);
            return null;
        } finally {
            closeConnection();
        }
    }

    public static void factorySetUp(String[] creds) {
        try {
            factory = new VistaLinkPooledConnectionFactory(creds[0], creds[1], creds[2], creds[3]);
        } catch (Exception e) {
            log.severe("Connection to repository failed.  Credentials were " + Arrays.toString(creds));
        }
    }

    public void onShutdown() {
        if (factory != null) {
            factory.emptyPool();
        }
        factory = null;
    }

    /**
     *  sets the passed VistaLinkPooledConnection
     *  @return true if conneciton worked.  False otherwise.
     */
    protected boolean setConnection() throws OvidDomainException {
        if (factory == null) {
            factorySetUp(credentials);
        }
        //conn = OvidSecureRepository.getDirectConnection(credentials[0], credentials[1], credentials[2], credentials[3]);
        conn = factory.getConnection();
        if (conn == null) {
            log.severe("Connection to repository failed.  Credentials were " + Arrays.toString(credentials));
            return false;
        }
        return true;
    }

    /**
     *  closes the passed VistaLinkPooledConnection
     */
    protected void closeConnection() {
        if (conn != null) {
            try {
                // log.finer("closing connection");
                // conn.returnToPool();
                conn.close();
            } catch (RPCException e) {
                log.finer("Error closing connection");
            }
            conn = null;
        }
    }

    /**
     * Set credentials property.
     *
     *@param credentials New credentials property.
     */
    public void setCredentials(String... credentials) {
        if (credentials.length < 4) {
            throw new RuntimeException("Invalid number of credentials.  You must proivide <host> <port> <ovid-access-code> <ovid-verify-code>");
        }
        this.credentials = credentials;
    }

    public List<Allergy> getAllergies(String id) {
        List<Allergy> list = new ArrayList<Allergy>();
        try {
            if (setConnection()) {
                log.finer("Connection made...");
                PatientItemRepository r = new PatientItemRepository(conn, conn, "MSC PATIENT DASHBOARD");
                log.finer("HERE!! " + r.toString());
                Collection<IsAPatientItem> vista_list = r.getAllergies(id, false);
                // an ArrayList of PatientAllergies objects is returned -  converty to hData Allergy type
                for (IsAPatientItem a : vista_list) {
                    Allergy allergy = new Allergy();  //hData type
                    PatientAllergy pa = (PatientAllergy) a;  //vista (ovid) type
                    //populate

                    Product c = new Product();
                    c.setValue(pa.getMessage());
                    allergy.setProduct(c);
                    //set time for adverse reaction
                    if (pa.getDateTime() != null) {
                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setTime(pa.getDateTime());
                        DatatypeFactory factory = DatatypeFactory.newInstance();
                        DateRange d = new DateRange();
                        d.setLow(factory.newXMLGregorianCalendar(cal));
                        allergy.setAdverseEventDate(d);
                    }

                    Reaction re = new Reaction();
                    re.setValue(pa.getSigns());
                    allergy.setReaction(re);

                    // System.out.println(pa.toString());
                    //add to the list
                    list.add(allergy);
                }
            } else {
                log.warning("BAD CONNECTION");
            }

        } catch (Throwable e) {
            log.throwing(KEY, "Error retreiving PatientItems", e);
        } finally {
            closeConnection();
            return list;
        }
    }

    public List<Medication> getMedications(String id) {
        List<Medication> list = new ArrayList<Medication>();
        try {
            if (setConnection()) {
                PatientItemRepository r = new PatientItemRepository(conn, conn, "MSC PATIENT DASHBOARD");
                Collection<IsAPatientItem> vista_list = r.getMedications(id);
                // an ArrayList of PatientAllergies objects is returned -  converty to hData Medication type
                for (IsAPatientItem a : vista_list) {
                    Medication medication = new Medication();  //hData type
                    PatientMedication pa = (PatientMedication) a;  //vista (ovid) type
                    //populate
                    //message -> narrative
                    medication.setNarrative(pa.getMessage());
                    //set time for adverse reaction
                    if (pa.getDateTime() != null) {
                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setTime(pa.getDateTime());
                        DatatypeFactory factory = DatatypeFactory.newInstance();
                        medication.getEffectiveTime().add(factory.newXMLGregorianCalendar(cal));
                    }

                    String medname = pa.getMedName();
                    MedicationInformation m = new MedicationInformation();
                    MedicationInformation.ManufacturedMaterial mm = new MedicationInformation.ManufacturedMaterial();
                    m.setManufacturedMaterial(mm);
                    mm.setFreeTextBrandName(medname);
                    medication.setMedicationInformation(m);

                    Dose d = new Dose();
                    d.setValue(pa.getDose());
                    medication.setDose(d);

                    CodedValue c = new CodedValue();
                    c.setValue(pa.getDelivery());
                    medication.setDeliveryMethod(c);

                    medication.setPatientInstructions(pa.getFrequency());
                    //add to the list
                    list.add(medication);
                }
                log.finer("Number of medications for patient " + id + " is " + list.size());
            } else {
                log.warning("BAD CONNECTION");
            }

        } catch (Throwable e) {
            log.throwing(KEY, "Error retreiving PatientItems", e);
        } finally {
            closeConnection();
            return list;
        }
    }

    public List<org.projecthdata.hdata.schemas._2009._06.condition.Condition> getProblems(String id) {
        List<org.projecthdata.hdata.schemas._2009._06.condition.Condition> list = new ArrayList<org.projecthdata.hdata.schemas._2009._06.condition.Condition>();
        try {
            if (setConnection()) {
                log.finer("Connection made...");
                PatientItemRepository r = new PatientItemRepository(conn, conn, "MSC PATIENT DASHBOARD");
                log.finer("HERE!! " + r.toString());
                Collection<PatientProblem> vista_list = r.getProblems(id);
                // an ArrayList of PatientProblem objects is returned -  converty to hData Condition type
                for (PatientProblem p : vista_list) {
                    Condition problem = new Condition();  //hData type

                    //populate


                    if (p.getDateTime() != null) {
                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setTime(p.getDateTime());
                        DatatypeFactory factory = DatatypeFactory.newInstance();
                        DateRange d = new DateRange();
                        d.setLow(factory.newXMLGregorianCalendar(cal));
                        problem.setProblemDate(d);
                    }
                    ProblemCode pcode = new ProblemCode();
                    pcode.setCode(p.getIcd());
                    problem.setProblemCode(pcode);
                    problem.setProblemName(p.getMessage());
                    problem.setNarrative(p.getStatus());

                    //System.out.println(problem.toString());
                    // System.out.println(pa.toString());
                    //add to the list
                    list.add(problem);
                }
            } else {
                log.warning("BAD CONNECTION");
            }

        } catch (Throwable e) {
            log.throwing(KEY, "Error retreiving PatientItems", e);
        } finally {
            closeConnection();
            return list;
        }
    }

    private boolean stringExists(String testString) {
        return ((testString != null) && (!testString.equals("")));
    }

    private void setPhoneNumber(List<Telecom> teleList, String telephone, String type, String use) {
        if (stringExists(telephone)) {
            Telecom telecom = new Telecom();
            telecom.setValue(telephone);
            telecom.setType(type);
            telecom.setUse(use);
            teleList.add(telecom);
        }
    }
}
