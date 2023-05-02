package com.example.homelibrary.utils.utilsdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VolumeInfo {

    @JsonProperty("title")
    String title;
    @JsonProperty("subtitle")
    String subTitle;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<String> authors;
    @JsonProperty("publishedDate")
    int publishedYear;
    @JsonProperty("pageCount")
    int numOfPages;
    @JsonProperty("language")
    String language;
}
