package cn.clboy.demo.springcloud.zuul.service.search.response;


import lombok.Data;

import java.util.List;

@Data
public class SearchResult {
    private int Total;
    private int page;
    private List<String> data;
}