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
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "bug" })
@XmlRootElement(name = "bugzilla")
public class Bugzilla {

    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String version;
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String urlbase;
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String maintainer;
    @XmlAttribute
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String exporter;
    @XmlElement(required = true)
    protected List<Bug> bug;

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
     * Gets the value of the urlbase property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getUrlbase() {
        return urlbase;
    }

    /**
     * Sets the value of the urlbase property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setUrlbase(final String value) {
        this.urlbase = value;
    }

    /**
     * Gets the value of the maintainer property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMaintainer() {
        return maintainer;
    }

    /**
     * Sets the value of the maintainer property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setMaintainer(final String value) {
        this.maintainer = value;
    }

    /**
     * Gets the value of the exporter property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getExporter() {
        return exporter;
    }

    /**
     * Sets the value of the exporter property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setExporter(final String value) {
        this.exporter = value;
    }

    /**
     * Gets the value of the bug property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the bug property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getBug().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Bug }
     * 
     * 
     */
    public List<Bug> getBug() {
        if (bug == null) {
            bug = new ArrayList<Bug>();
        }
        return this.bug;
    }

}
