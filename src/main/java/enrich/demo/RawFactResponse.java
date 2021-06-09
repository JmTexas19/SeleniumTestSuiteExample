package enrich.demo;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

/* Deserialize JSON from endpoint:
* `https://pharmatest.enrichconsulting.com/FactTableService/rawfact/proj/%s/%s/Revenue_yrs_base/`
*/

public class RawFactResponse {
    @SerializedName("name")
    public String name;

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("indexes")
    public Indexes indexes;

    @SerializedName("data")
    public ArrayList<String> data;

    @SerializedName("indexOrder")
    public ArrayList<String> indexOrder;

    @SerializedName("indexNames")
    public IndexNames indexNames;

    @SerializedName("indexFilters")
    public IndexFilters indexFilters;

    @SerializedName("isGlobal")
    public Boolean isGlobal;
}

class Indexes {
    @SerializedName("Years")
    public ArrayList<String> years;
}

class IndexNames {
    @SerializedName("Years")
    public String years;
}

class IndexFilters {
    //Nothing here :(
}

class MetaData {
    @SerializedName("lastPubDate")
    public String lastPubDate;
    
    @SerializedName("source")
    public String source;
}