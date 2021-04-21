package cn.clboy.demo.elasticsearch.springboot.repository;


import cn.clboy.demo.elasticsearch.springboot.pojo.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PersonRepository extends ElasticsearchRepository<Person, String> {

    List<Person> findByInfo(String info);
}