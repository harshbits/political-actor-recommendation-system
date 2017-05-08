package com.par.system.beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gson.annotations.SerializedName;

@JsonInclude(Include.NON_NULL)
public class Results implements Serializable {

	private static final long serialVersionUID = 1L;

	private String published_date;

	@SerializedName("abstract")
	private String content;

	private String subsection;

	private String section;

	private String title;

	private String source;

	private String subheadline;

	private String caption;

	private String suggested_link_text;

	public String getPublished_date() {
		return published_date;
	}

	public void setPublished_date(String published_date) {
		this.published_date = published_date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubsection() {
		return subsection;
	}

	public void setSubsection(String subsection) {
		this.subsection = subsection;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSubheadline() {
		return subheadline;
	}

	public void setSubheadline(String subheadline) {
		this.subheadline = subheadline;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getSuggested_link_text() {
		return suggested_link_text;
	}

	public void setSuggested_link_text(String suggested_link_text) {
		this.suggested_link_text = suggested_link_text;
	}

	@Override
	public String toString() {
		String str = "";
		if(title !=null){
			str = str + " " +title.trim();
		}
		if(caption !=null){
			str = str +" " + caption.trim();
		}
		if(content !=null){
			str = str + " " +content.trim();
		}
		if(suggested_link_text !=null){
			str = str + " " +suggested_link_text.trim();
		}
		return str.trim();
	}


}
