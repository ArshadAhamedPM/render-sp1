package com.quran.api.model;

import java.util.List;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "quran")
@XmlAccessorType(XmlAccessType.FIELD)
public class Quran {

    @XmlElement(name = "sura")
    private List<Sura> suras;

    public List<Sura> getSuras() {
        return suras;
    }

    public void setSuras(List<Sura> suras) {
        this.suras = suras;
    }
}


