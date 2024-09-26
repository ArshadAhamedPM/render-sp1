package com.quran.api.model;

public class VerbInfo {
    private String verb;
    private String root;
    private String form;
    private String translation;
    private int index;
    
    public VerbInfo() {
    }

    public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	// Constructors, getters, and setters
    public VerbInfo(String verb, String root, String form, String translation,int index) {
        this.verb = verb;
        this.root = root;
        this.form = form;
        this.translation = translation;
        this.index = index;
    }

    // Getters and setters
    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return "VerbInfo [verb=" + verb + ", root=" + root + ", form=" + form + ", translation=" + translation + "]";
    }
}

