package cn.clboy.demo.elasticsearch.springboot;


import cn.clboy.demo.elasticsearch.springboot.pojo.Person;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.range.ParsedRange;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedMax;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TemplateTest {


    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 索引库和映射在项目启动的时候就会创建，无需手动创建
     */
    @Test
    public void CreateMappingTest() throws Exception {
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(Person.class);
        Document mapping = indexOperations.createMapping();
        indexOperations.putMapping(mapping);
    }

    /**
     * 添加文档
     */
    @Test
    public void addDocTest() throws Exception {
        Person person = new Person(null, "张三", "他的头发有两寸来长，乱蓬蓬的，活像一个喜鹊窝", 31);
        elasticsearchRestTemplate.save(person);
    }


    /**
     * 根据id获取文档
     */
    @Test
    public void getDocByIdTest() throws Exception {
        Person person = elasticsearchRestTemplate.get("PQgJ9XgBYLKqIiuTwh_R", Person.class);
        System.out.println(person);
    }

    /**
     * 更新文档,只会更新设置的字段
     */

    @Test
    public void updateTest() throws Exception {
        //先查询
        Person person = elasticsearchRestTemplate.get("PQgJ9XgBYLKqIiuTwh_R", Person.class);
        //修改
        person.setInfo("修改后的......");
        elasticsearchRestTemplate.save(person);
    }

    /**
     * 删除文档
     */
    @Test
    public void delDocTest() throws Exception {
        String id = elasticsearchRestTemplate.delete("PQgJ9XgBYLKqIiuTwh_R", Person.class);
        System.out.println(id);
    }


    /**
     * match_all 查询全部
     */
    @Test
    public void matchAllQueryTest() throws Exception {
        Query query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery()).build();
        SearchHits<Person> searchHits = elasticsearchRestTemplate.search(query, Person.class);
        this.printHits(searchHits);
    }

    /**
     * 分页查询
     */
    @Test
    public void pageQueryTest() throws Exception {
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withPageable(PageRequest.of(1, 3))
                .build();
        SearchHits<Person> searchHits = elasticsearchRestTemplate.search(query, Person.class);
        this.printHits(searchHits);
    }


    /**
     * 词条匹配查询
     */
    @Test
    public void termQueryTest() throws Exception {
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("name", "王二麻子"))
                .build();
        SearchHits<Person> searchHits = elasticsearchRestTemplate.search(query, Person.class);
        this.printHits(searchHits);
    }

    /**
     * match匹配查询
     */
    @Test
    public void matchQueryTest() throws Exception {
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("info", "油性头发"))
                .build();
        SearchHits<Person> searchHits = elasticsearchRestTemplate.search(query, Person.class);
        this.printHits(searchHits);
    }

    /**
     * multi match匹配查询
     * 多字段匹配
     */
    @Test
    public void multiMatchQueryTest() throws Exception {
    }

    /**
     * 布尔查询
     * <p>
     * 组合查询
     */
    @Test
    public void booleanQueryTest() throws Exception {
    }

    /**
     * range范围查询
     */
    @Test
    public void rangeQueryTest() throws Exception {
    }

    /**
     * fuzzy 模糊查询
     */
    @Test
    public void fuzzyQueryTest() throws Exception {
    }

    /**
     * filter 查询过滤
     */
    @Test
    public void filterQueryTest() throws Exception {
    }

    /**
     * sort 查询排序
     */
    @Test
    public void sortQueryTest() throws Exception {
    }

    /**
     * 高亮显示
     */
    @Test
    public void highlightQueryTest() throws Exception {
        HighlightBuilder.Field highlightField = new HighlightBuilder.Field("info").preTags("===>").postTags("<===");
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("info", "油性头发"))
                .withHighlightFields(highlightField).build();

        SearchHits<Person> searchHits = elasticsearchRestTemplate.search(query, Person.class);

        this.printHits(searchHits, true);
    }

    /**
     * 聚合查询
     */
    @Test
    public void aggQueryTest() throws Exception {

        RangeAggregationBuilder ageRangeAgg = AggregationBuilders.range("ageRangeAgg")
                .field("age")
                .addRange("15-19岁", 15, 20)
                .addRange("20-24岁", 20, 25)
                .addRange("25-29岁", 25, 30)
                .addUnboundedFrom("30岁以上", 30);

        MaxAggregationBuilder subMaxAgeAgg = AggregationBuilders.max("maxAgeAgg").field("age");
        ageRangeAgg.subAggregation(subMaxAgeAgg);

        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .addAggregation(ageRangeAgg)
                .build();


        SearchHits<Person> searchHits = elasticsearchRestTemplate.search(query, Person.class);

        Aggregations aggregations = searchHits.getAggregations();

        ParsedRange ageRange = aggregations.get("ageRangeAgg");

        List<? extends Range.Bucket> buckets = ageRange.getBuckets();

        buckets.forEach(bucket -> {
            ParsedMax maxAgeAgg = bucket.getAggregations().get("maxAgeAgg");
            int maxAge = (int) maxAgeAgg.getValue();
            System.out.format("%s：%d人，最大年龄：%d%n", bucket.getKey(), bucket.getDocCount(), maxAge);
        });
    }


    public void printHits(SearchHits hits) {
        this.printHits(hits, false);
    }

    public void printHits(SearchHits hits, boolean highlight) {
        System.out.println("总记录数：" + hits.getTotalHits());

        //匹配到的文档
        List<SearchHit<Person>> searchHits = hits.getSearchHits();

        for (SearchHit<Person> searchHit : searchHits) {
            //源文档内容
            Person person = searchHit.getContent();
            System.out.println(person);
            if (highlight) {
                Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
                System.out.println(highlightFields);
                System.out.println();
            }
        }

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
            Person person = new Person(null, nameAndInfo[0], nameAndInfo[1], Integer.parseInt(nameAndInfo[2]));
            elasticsearchRestTemplate.save(person);
        }
    }
}
