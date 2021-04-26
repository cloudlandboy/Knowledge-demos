package cn.clboy.demo.elasticsearch.hl.rest.client.utils;


import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.range.ParsedRange;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedMax;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighLevelClientTest {

    RestHighLevelClient restHighLevelClient = ElasticSearchUtil.getRestHighLevelClient();

    final String INDEX_NAME = "test_high_client";

    /**
     * 创建索引
     */
    @Test
    public void CreateIndexTest() throws Exception {
        //创建索引请求对象，并设置索引名称
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX_NAME);

        //设置索引参数
        Settings settings = Settings.builder().put("number_of_shards", 1)
                .put("number_of_replicas", 0)
                .build();
        createIndexRequest.settings(settings);

        //设置映射
        String json = "{\n" +
                "  \"properties\": {\n" +
                "    \"name\": {\n" +
                "      \"type\": \"keyword\",\n" +
                "      \"index\": true\n" +
                "    },\n" +
                "    \"info\": {\n" +
                "      \"type\": \"text\",\n" +
                "      \"index\": true,\n" +
                "      \"analyzer\": \"ik_smart\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        createIndexRequest.mapping(json, XContentType.JSON);

        //创建索引操作客户端
        IndicesClient indexClient = restHighLevelClient.indices();

        //创建，获取响应
        CreateIndexResponse createIndexResponse = indexClient.create(createIndexRequest, RequestOptions.DEFAULT);

        boolean acknowledged = createIndexResponse.isAcknowledged();

        System.out.println(acknowledged);
    }


    /**
     * 添加文档
     */
    @Test
    public void addDocTest() throws Exception {
        HashMap<String, Object> document = new HashMap<>(4);
        document.put("name", "张三");
        document.put("info", "22岁，男，未婚，长的很帅");

        IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
        indexRequest.source(document);

        IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        System.out.println(response);
    }

    /**
     * 根据id获取文档
     */
    @Test
    public void getDocByIdTest() throws Exception {
        GetRequest getRequest = new GetRequest(INDEX_NAME, "h40P3HgBcWSISYUq4Xp0");

        GetResponse response = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

        if (response.isExists()) {
            System.out.println(response);
        } else {
            System.out.println("不存在！");
        }
    }

    /**
     * 更新文档,只会更新设置的字段
     */

    @Test
    public void updateTest() throws Exception {
        UpdateRequest request = new UpdateRequest(INDEX_NAME, "h40P3HgBcWSISYUq4Xp0");
        HashMap<String, Object> document = new HashMap<>(2);
        document.put("name", "李四");
        request.doc(document);

        UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * 删除文档
     */
    @Test
    public void delDocTest() throws Exception {
        DeleteRequest request = new DeleteRequest(INDEX_NAME, "h40P3HgBcWSISYUq4Xp0");
        DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }


    /**
     * match_all 查询全部
     */
    @Test
    public void matchAllQueryTest() throws Exception {
        //搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        SearchHits hits = this.search(searchSourceBuilder).getHits();
        this.printHits(hits);
    }

    /**
     * 分页查询
     * <p>
     * 默认是每页显示10条
     */
    @Test
    public void pageQueryTest() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        int page = 2;
        int size = 3;
        int from = (page - 1) * size;

        // 起始记录下标
        searchSourceBuilder.from(from);

        //每页显示个数
        searchSourceBuilder.size(size);
        SearchResponse response = this.search(searchSourceBuilder);
        this.printHits(response.getHits());
    }


    /**
     * 词条匹配查询
     */
    @Test
    public void termQueryTest() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("name", "王二麻子"));
        this.printHits(this.search(searchSourceBuilder).getHits());
    }

    /**
     * match匹配查询
     */
    @Test
    public void matchQueryTest() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("info", "油性头发"));
        this.printHits(this.search(searchSourceBuilder).getHits());
    }

    /**
     * multi match匹配查询
     * 多字段匹配
     */
    @Test
    public void multiMatchQueryTest() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("沐朗然", "name", "info"));
        this.printHits(this.search(searchSourceBuilder).getHits());
    }

    /**
     * 布尔查询
     * <p>
     * 组合查询
     */
    @Test
    public void booleanQueryTest() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //构建布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("info", "油性头发"));
        boolQueryBuilder.mustNot(QueryBuilders.termQuery("name", "王二麻子"));

        searchSourceBuilder.query(boolQueryBuilder);
        this.printHits(this.search(searchSourceBuilder).getHits());
    }

    /**
     * range范围查询
     */
    @Test
    public void rangeQueryTest() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.rangeQuery("age").gte(30));
        this.printHits(this.search(searchSourceBuilder).getHits());
    }

    /**
     * fuzzy 模糊查询
     */
    @Test
    public void fuzzyQueryTest() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery("name", "冷星月"));
        this.printHits(this.search(searchSourceBuilder).getHits());
    }

    /**
     * filter 查询过滤
     */
    @Test
    public void filterQueryTest() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder()
                .must(QueryBuilders.matchQuery("info", "油性头发"))
                .filter(QueryBuilders.rangeQuery("age").lte(28));
        searchSourceBuilder.query(boolQueryBuilder);
        this.printHits(this.search(searchSourceBuilder).getHits());
    }

    /**
     * sort 查询排序
     */
    @Test
    public void sortQueryTest() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.sort("age", SortOrder.ASC);
        this.printHits(this.search(searchSourceBuilder).getHits());
    }

    /**
     * 高亮显示
     */
    @Test
    public void highlightQueryTest() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("info", "油性头发"));

        //构建高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("info");
        highlightBuilder.preTags("===>").postTags("<===");
        searchSourceBuilder.highlighter(highlightBuilder);

        SearchResponse response = this.search(searchSourceBuilder);
        this.printHits(response.getHits(), true);
    }

    /**
     * 聚合查询
     */
    @Test
    public void aggQueryTest() throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        RangeAggregationBuilder ageRangeAgg = AggregationBuilders.range("ageRangeAgg")
                .field("age")
                .addRange("15-19岁", 15, 20)
                .addRange("20-24岁", 20, 25)
                .addRange("25-29岁", 25, 30)
                .addUnboundedFrom("30岁以上", 30);

        MaxAggregationBuilder subMaxAgeAgg = AggregationBuilders.max("maxAgeAgg").field("age");
        ageRangeAgg.subAggregation(subMaxAgeAgg);

        searchSourceBuilder.aggregation(ageRangeAgg);

        searchSourceBuilder.size(0);
        Aggregations aggregations = this.search(searchSourceBuilder).getAggregations();

        ParsedRange ageRange = aggregations.get("ageRangeAgg");

        List<? extends Range.Bucket> buckets = ageRange.getBuckets();

        buckets.forEach(bucket -> {
            ParsedMax maxAgeAgg = bucket.getAggregations().get("maxAgeAgg");
            int maxAge = (int) maxAgeAgg.getValue();

            System.out.println(maxAgeAgg.getType());
            System.out.format("%s：%d人，最大年龄：%d%n", bucket.getKey(), bucket.getDocCount(), maxAge);
        });
    }


    public void printHits(SearchHits hits) {
        this.printHits(hits, false);
    }

    public void printHits(SearchHits hits, boolean highlight) {
        System.out.println("总记录数：" + hits.getTotalHits());

        //匹配到的文档
        SearchHit[] searchHits = hits.getHits();

        for (SearchHit searchHit : searchHits) {
            //源文档内容
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            System.out.println(sourceAsMap);

            if (highlight) {
                Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                System.out.println(highlightFields);
                System.out.println();
            }
        }

    }

    public SearchResponse search(SearchSourceBuilder searchSourceBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.source(searchSourceBuilder);
        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }

    /**
     * 插入一些测试文档
     */
    @Test
    public void initAddData() throws Exception {
        String[] datas = {
                "张三:他的头发有两寸来长，乱蓬蓬的，活像一个喜鹊窝。:31",
                "李四:他的两只耳朵出奇的大，像两把小蒲扇:23",
                "王二麻子:他那半寸长的短发像秋天的芦草一样又干又硬，没有一点儿油性:22",
                "冷希月:他的一对耳朵啊，活像两片神气活现地撑开着的河蚌壳儿！:30",
                "沐朗然:这姑娘的腰肢像杨柳枝儿似的又细又柔。:18",
                "微生辉:她的头发颜色漆黑，带有反光，像乌鸦的翅膀一样，又黑又亮。:34",
                "贵志诚:他长得是胸阔膀又宽，论劲，气死一头牛。:17",
                "隐冬卉:一丛稀疏而干枯的头发，像小鸭的绒毛点缀在头顶上。:31",
                "敖白易:他的头发、胡子全白了，如银丝一般，闪着晶莹的白光。:24",
                "粟玉轩:她又黄又瘦，脖子像鹅一样显得细长。:22",
                "滕倩丽:那女孩的脑后拖着一根猪尾巴似的小辫。:25",
                "冷星辰:翩翩少年，喜欢沐朗然:16"
        };

        for (String data : datas) {
            String[] nameAndInfo = data.split(":");
            HashMap<String, Object> document = new HashMap<>(4);
            document.put("name", nameAndInfo[0]);
            document.put("info", nameAndInfo[1]);
            document.put("age", Integer.parseInt(nameAndInfo[2]));
            IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
            indexRequest.source(document);
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

            System.out.format("%s：%d%n", nameAndInfo[0], response.status().getStatus());

        }
    }
}

