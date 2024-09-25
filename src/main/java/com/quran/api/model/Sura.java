package com.quran.api.model;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Sura {

    @XmlAttribute(name = "index")
    private int index;

    @XmlAttribute(name = "name")
    private String name;
    
    public List<Aya> getAyas() {
		return ayas;
	}

	public void setAyas(List<Aya> ayas) {
		this.ayas = ayas;
	}

	@XmlElement(name = "aya")
    private List<Aya> ayas;


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

