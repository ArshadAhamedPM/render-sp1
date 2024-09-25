package com.quran.api.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Aya {

    @XmlAttribute(name = "index")
    private int index;

    @XmlAttribute(name = "text")
    private String text;

    

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

  
}

