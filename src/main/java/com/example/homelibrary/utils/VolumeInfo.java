package com.example.homelibrary.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
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
