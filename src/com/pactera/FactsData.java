package com.pactera;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * Class for json
 * @author 
 *
 */
public class FactsData {
	
	@SerializedName("title")
    private String listTitle;
    @SerializedName("rows")
    private ArrayList<FactsListObject> facts;

    public FactsData(ArrayList<FactsListObject> topics, String title) 
    {
        this.listTitle = title;
        this.facts = topics;
    }

    public String getTitle() {
        return listTitle;
    }
    public void setTitle(String title) {
        this.listTitle = title;
    }
    
    public ArrayList<FactsListObject> getTopics() {
        return facts;
    }
    public void setBeanTopics(ArrayList<FactsListObject> topics) {
        this.facts = topics;
    }

}
