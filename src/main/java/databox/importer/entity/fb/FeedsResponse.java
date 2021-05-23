
package databox.importer.entity.fb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "data",
    "paging"
})
@Generated("jsonschema2pojo")
public class FeedsResponse implements Serializable
{

    @JsonProperty("data")
    private List<FeedData> data = null;
    @JsonProperty("paging")
    private FeedPaging paging;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -9041899462725727119L;

    @JsonProperty("data")
    public List<FeedData> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<FeedData> data) {
        this.data = data;
    }

    @JsonProperty("paging")
    public FeedPaging getPaging() {
        return paging;
    }

    @JsonProperty("paging")
    public void setPaging(FeedPaging paging) {
        this.paging = paging;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
