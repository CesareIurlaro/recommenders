//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.04.28 at 08:59:52 AM MESZ 
//

package org.eclipse.recommenders.server.stacktraces.crawler.bugzilla.generated;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "bugId", "alias", "creationTs", "shortDesc", "deltaTs", "reporterAccessible",
        "cclistAccessible", "classificationId", "classification", "product", "component", "version", "repPlatform",
        "opSys", "bugStatus", "resolution", "dupId", "bugFileLoc", "statusWhiteboard", "keywords", "priority",
        "bugSeverity", "targetMilestone", "dependson", "blocked", "votes", "everconfirmed", "reporter", "assignedTo",
        "qaContact", "cc", "estimatedTime", "remainingTime", "actualTime", "deadline", "group", "flag", "longDesc",
        "attachment" })
@XmlRootElement(name = "bug")
public class Bug {

    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String error;
    @XmlElement(name = "bug_id", required = true)
    protected String bugId;
    protected String alias;
    @XmlElement(name = "creation_ts")
    protected String creationTs;
    @XmlElement(name = "short_desc")
    protected String shortDesc;
    @XmlElement(name = "delta_ts")
    protected String deltaTs;
    @XmlElement(name = "reporter_accessible")
    protected String reporterAccessible;
    @XmlElement(name = "cclist_accessible")
    protected String cclistAccessible;
    @XmlElement(name = "classification_id")
    protected String classificationId;
    protected String classification;
    protected String product;
    protected String component;
    protected String version;
    @XmlElement(name = "rep_platform")
    protected String repPlatform;
    @XmlElement(name = "op_sys")
    protected String opSys;
    @XmlElement(name = "bug_status")
    protected String bugStatus;
    protected String resolution;
    @XmlElement(name = "dup_id")
    protected String dupId;
    @XmlElement(name = "bug_file_loc")
    protected String bugFileLoc;
    @XmlElement(name = "status_whiteboard")
    protected String statusWhiteboard;
    protected List<Keywords> keywords;
    protected String priority;
    @XmlElement(name = "bug_severity")
    protected String bugSeverity;
    @XmlElement(name = "target_milestone")
    protected String targetMilestone;
    protected List<Dependson> dependson;
    protected List<Blocked> blocked;
    protected String votes;
    protected String everconfirmed;
    protected String reporter;
    @XmlElement(name = "assigned_to")
    protected String assignedTo;
    @XmlElement(name = "qa_contact")
    protected String qaContact;
    protected List<Cc> cc;
    @XmlElement(name = "estimated_time")
    protected String estimatedTime;
    @XmlElement(name = "remaining_time")
    protected String remainingTime;
    @XmlElement(name = "actual_time")
    protected String actualTime;
    protected String deadline;
    protected List<Group> group;
    protected List<Flag> flag;
    @XmlElement(name = "long_desc")
    protected List<LongDesc> longDesc;
    protected List<Attachment> attachment;

    /**
     * Gets the value of the error property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setError(final String value) {
        this.error = value;
    }

    /**
     * Gets the value of the bugId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBugId() {
        return bugId;
    }

    /**
     * Sets the value of the bugId property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setBugId(final String value) {
        this.bugId = value;
    }

    /**
     * Gets the value of the alias property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the value of the alias property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setAlias(final String value) {
        this.alias = value;
    }

    /**
     * Gets the value of the creationTs property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCreationTs() {
        return creationTs;
    }

    /**
     * Sets the value of the creationTs property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCreationTs(final String value) {
        this.creationTs = value;
    }

    /**
     * Gets the value of the shortDesc property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getShortDesc() {
        return shortDesc;
    }

    /**
     * Sets the value of the shortDesc property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setShortDesc(final String value) {
        this.shortDesc = value;
    }

    /**
     * Gets the value of the deltaTs property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDeltaTs() {
        return deltaTs;
    }

    /**
     * Sets the value of the deltaTs property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDeltaTs(final String value) {
        this.deltaTs = value;
    }

    /**
     * Gets the value of the reporterAccessible property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getReporterAccessible() {
        return reporterAccessible;
    }

    /**
     * Sets the value of the reporterAccessible property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setReporterAccessible(final String value) {
        this.reporterAccessible = value;
    }

    /**
     * Gets the value of the cclistAccessible property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCclistAccessible() {
        return cclistAccessible;
    }

    /**
     * Sets the value of the cclistAccessible property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCclistAccessible(final String value) {
        this.cclistAccessible = value;
    }

    /**
     * Gets the value of the classificationId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getClassificationId() {
        return classificationId;
    }

    /**
     * Sets the value of the classificationId property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setClassificationId(final String value) {
        this.classificationId = value;
    }

    /**
     * Gets the value of the classification property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getClassification() {
        return classification;
    }

    /**
     * Sets the value of the classification property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setClassification(final String value) {
        this.classification = value;
    }

    /**
     * Gets the value of the product property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setProduct(final String value) {
        this.product = value;
    }

    /**
     * Gets the value of the component property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getComponent() {
        return component;
    }

    /**
     * Sets the value of the component property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setComponent(final String value) {
        this.component = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setVersion(final String value) {
        this.version = value;
    }

    /**
     * Gets the value of the repPlatform property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRepPlatform() {
        return repPlatform;
    }

    /**
     * Sets the value of the repPlatform property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setRepPlatform(final String value) {
        this.repPlatform = value;
    }

    /**
     * Gets the value of the opSys property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOpSys() {
        return opSys;
    }

    /**
     * Sets the value of the opSys property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setOpSys(final String value) {
        this.opSys = value;
    }

    /**
     * Gets the value of the bugStatus property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBugStatus() {
        return bugStatus;
    }

    /**
     * Sets the value of the bugStatus property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setBugStatus(final String value) {
        this.bugStatus = value;
    }

    /**
     * Gets the value of the resolution property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getResolution() {
        return resolution;
    }

    /**
     * Sets the value of the resolution property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setResolution(final String value) {
        this.resolution = value;
    }

    /**
     * Gets the value of the dupId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDupId() {
        return dupId;
    }

    /**
     * Sets the value of the dupId property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDupId(final String value) {
        this.dupId = value;
    }

    /**
     * Gets the value of the bugFileLoc property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBugFileLoc() {
        return bugFileLoc;
    }

    /**
     * Sets the value of the bugFileLoc property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setBugFileLoc(final String value) {
        this.bugFileLoc = value;
    }

    /**
     * Gets the value of the statusWhiteboard property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getStatusWhiteboard() {
        return statusWhiteboard;
    }

    /**
     * Sets the value of the statusWhiteboard property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setStatusWhiteboard(final String value) {
        this.statusWhiteboard = value;
    }

    /**
     * Gets the value of the keywords property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the keywords property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getKeywords().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Keywords }
     * 
     * 
     */
    public List<Keywords> getKeywords() {
        if (keywords == null) {
            keywords = new ArrayList<Keywords>();
        }
        return this.keywords;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setPriority(final String value) {
        this.priority = value;
    }

    /**
     * Gets the value of the bugSeverity property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBugSeverity() {
        return bugSeverity;
    }

    /**
     * Sets the value of the bugSeverity property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setBugSeverity(final String value) {
        this.bugSeverity = value;
    }

    /**
     * Gets the value of the targetMilestone property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTargetMilestone() {
        return targetMilestone;
    }

    /**
     * Sets the value of the targetMilestone property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTargetMilestone(final String value) {
        this.targetMilestone = value;
    }

    /**
     * Gets the value of the dependson property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the dependson property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getDependson().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Dependson }
     * 
     * 
     */
    public List<Dependson> getDependson() {
        if (dependson == null) {
            dependson = new ArrayList<Dependson>();
        }
        return this.dependson;
    }

    /**
     * Gets the value of the blocked property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the blocked property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getBlocked().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Blocked }
     * 
     * 
     */
    public List<Blocked> getBlocked() {
        if (blocked == null) {
            blocked = new ArrayList<Blocked>();
        }
        return this.blocked;
    }

    /**
     * Gets the value of the votes property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getVotes() {
        return votes;
    }

    /**
     * Sets the value of the votes property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setVotes(final String value) {
        this.votes = value;
    }

    /**
     * Gets the value of the everconfirmed property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getEverconfirmed() {
        return everconfirmed;
    }

    /**
     * Sets the value of the everconfirmed property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setEverconfirmed(final String value) {
        this.everconfirmed = value;
    }

    /**
     * Gets the value of the reporter property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getReporter() {
        return reporter;
    }

    /**
     * Sets the value of the reporter property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setReporter(final String value) {
        this.reporter = value;
    }

    /**
     * Gets the value of the assignedTo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAssignedTo() {
        return assignedTo;
    }

    /**
     * Sets the value of the assignedTo property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setAssignedTo(final String value) {
        this.assignedTo = value;
    }

    /**
     * Gets the value of the qaContact property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getQaContact() {
        return qaContact;
    }

    /**
     * Sets the value of the qaContact property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setQaContact(final String value) {
        this.qaContact = value;
    }

    /**
     * Gets the value of the cc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the cc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getCc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Cc }
     * 
     * 
     */
    public List<Cc> getCc() {
        if (cc == null) {
            cc = new ArrayList<Cc>();
        }
        return this.cc;
    }

    /**
     * Gets the value of the estimatedTime property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getEstimatedTime() {
        return estimatedTime;
    }

    /**
     * Sets the value of the estimatedTime property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setEstimatedTime(final String value) {
        this.estimatedTime = value;
    }

    /**
     * Gets the value of the remainingTime property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRemainingTime() {
        return remainingTime;
    }

    /**
     * Sets the value of the remainingTime property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setRemainingTime(final String value) {
        this.remainingTime = value;
    }

    /**
     * Gets the value of the actualTime property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getActualTime() {
        return actualTime;
    }

    /**
     * Sets the value of the actualTime property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setActualTime(final String value) {
        this.actualTime = value;
    }

    /**
     * Gets the value of the deadline property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Sets the value of the deadline property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDeadline(final String value) {
        this.deadline = value;
    }

    /**
     * Gets the value of the group property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the group property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Group }
     * 
     * 
     */
    public List<Group> getGroup() {
        if (group == null) {
            group = new ArrayList<Group>();
        }
        return this.group;
    }

    /**
     * Gets the value of the flag property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the flag property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getFlag().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Flag }
     * 
     * 
     */
    public List<Flag> getFlag() {
        if (flag == null) {
            flag = new ArrayList<Flag>();
        }
        return this.flag;
    }

    /**
     * Gets the value of the longDesc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the longDesc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getLongDesc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link LongDesc }
     * 
     * 
     */
    public List<LongDesc> getLongDesc() {
        if (longDesc == null) {
            longDesc = new ArrayList<LongDesc>();
        }
        return this.longDesc;
    }

    /**
     * Gets the value of the attachment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the attachment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getAttachment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Attachment }
     * 
     * 
     */
    public List<Attachment> getAttachment() {
        if (attachment == null) {
            attachment = new ArrayList<Attachment>();
        }
        return this.attachment;
    }

}
