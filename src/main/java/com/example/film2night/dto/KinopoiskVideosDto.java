package com.example.film2night.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KinopoiskVideosDto {
    private List<VideoItem> items;

    public List<VideoItem> getItems() { return items; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VideoItem {
        private String url;
        private String name;
        private String site;

        public String getUrl() { return url; }
        public void setName(String name) { this.name = name; }
        public String getSite() { return site; }
    }
}
