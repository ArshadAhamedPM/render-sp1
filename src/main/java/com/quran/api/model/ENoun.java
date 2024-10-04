package com.quran.api.model;

public class ENoun {
	private String noun;
	private String meaning;
	private String ayah;
	private String no;
	
	
	public ENoun(String noun, String meaning, String ayah,String no) {
		super();
		this.noun = noun;
		this.meaning = meaning;
		this.no = no;
		this.ayah = ayah;
	}
	public String getNoun() {
		return noun;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public void setNoun(String noun) {
		this.noun = noun;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	public String getAyah() {
		return ayah;
	}
	public void setAyah(String ayah) {
		this.ayah = ayah;
	}
	
	
}
