package com.pactera;

import com.google.gson.annotations.SerializedName;

/**
 * Class for individual JSON-list row objects
 * Each row will have a title, description and image url
 * @author Ravi Bhai
 *
 */
public class FactsListObject {
	
	@SerializedName("title")
    private String title;
	@SerializedName("description")
    private String description;
	@SerializedName("imageHref")
    private String imageHref;

    public FactsListObject(String title, String description, String imageHref) {
        this.title = title;
        this.description = description;
        this.imageHref = imageHref;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getImageHref() {
        return imageHref;
    }
    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

}
