package cn.clboy.demo.springcloud.zuul.service.search.controller;


import cn.clboy.demo.springcloud.zuul.service.search.response.SearchResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class SearchController {

    @GetMapping("/search")
    public SearchResult search(@RequestParam("q") String keyword) {
        ArrayList<String> res = new ArrayList<>(20);

        for (int i = 0; i < 20; i++) {
            res.add(i + "--bulabulabula<em>" + keyword + "</em>bulabulabula");
        }

        SearchResult searchResult = new SearchResult();
        searchResult.setTotal(2000);
        searchResult.setPage(2);
        searchResult.setData(res);

        return searchResult;
    }
}