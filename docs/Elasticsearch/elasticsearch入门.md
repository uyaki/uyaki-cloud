# elasticsearch入门

## 新增/替换PUT（指定位置新增，需带id）

> 文档不能被修改，只能被替换覆盖

```sh
PUT /uyaki/employee/1
{
    "first_name" : "zhang",
    "last_name" : "san",
    "age" : "18",
    "about" : "I love li si",
    "interests" : [
      "li si",
      "music"
    ]
}
```

结果

```json
{
  "_index" : "uyaki",
  "_type" : "employee",
  "_id" : "1",
  "_version" : 1,
  "result" : "created",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 1,
  "_primary_term" : 1
}
```

## POST，主键新增（无需带ID）

> 生成的ID为UUID

```sh
POST /uyaki/employee
{
    "first_name" : "zhang",
    "last_name" : "san",
    "age" : "18",
    "about" : "I love li si",
    "interests" : [
      "li si",
      "music"
    ]
}
```



## 查询是否存在

```sh
HEAD /uyaki/employee/4
```

结果

```
200 - OK
```



## 查询GET

- 查询所有索引，及其状态

  ```sh
  GET /_cat/indices
  ```

- 查询单个

  ```sh
  GET /uyaki/employee/4
  ```

  结果

  ```json
  {
    "_index" : "uyaki",
    "_type" : "employee",
    "_id" : "4",
    "_version" : 1,
    "_seq_no" : 1,
    "_primary_term" : 1,
    "found" : true,
    "_source" : {
      "first_name" : "zhao",
      "last_name" : "liu",
      "age" : "18",
      "about" : "I love zhao liu",
      "interests" : [
        "zhaoliu",
        "music"
      ]
    }
  }
  ```

2. 查询所有`GET /_indes/_type/_search`

   > 默认返回前10个

   ```sh
   GET /uyaki/employee/_search
   ```

   结果

   ```json
   {
     "took" : 64,
     "timed_out" : false,
     "_shards" : {
       "total" : 5,
       "successful" : 5,
       "skipped" : 0,
       "failed" : 0
     },
     "hits" : {
       "total" : 4,
       "max_score" : 1.0,
       "hits" : [
         {
           "_index" : "uyaki",
           "_type" : "employee",
           "_id" : "2",
           "_score" : 1.0,
           "_source" : {
             "first_name" : "li",
             "last_name" : "si",
             "age" : "18",
             "about" : "I love wang wu",
             "interests" : [
               "wang wu",
               "music"
             ]
           }
         },
         {
           "_index" : "uyaki",
           "_type" : "employee",
           "_id" : "4",
           "_score" : 1.0,
           "_source" : {
             "first_name" : "zhao",
             "last_name" : "liu",
             "age" : "18",
             "about" : "I love zhao liu",
             "interests" : [
               "zhaoliu",
               "music"
             ]
           }
         },
         {
           "_index" : "uyaki",
           "_type" : "employee",
           "_id" : "1",
           "_score" : 1.0,
           "_source" : {
             "first_name" : "zhang",
             "last_name" : "san",
             "age" : "18",
             "about" : "I love li si",
             "interests" : [
               "li si",
               "music"
             ]
           }
         },
         {
           "_index" : "uyaki",
           "_type" : "employee",
           "_id" : "3",
           "_score" : 1.0,
           "_source" : {
             "first_name" : "wang",
             "last_name" : "wu",
             "age" : "18",
             "about" : "I love zhang san",
             "interests" : [
               "zhang san",
               "music"
             ]
           }
         }
       ]
     }
   }
   ```

3. 条件查询 `GET /_index/_type/_search?q=_field:keyword`

   ```sh
   GET /uyaki/employee/_search?q=first_name:zhang
   ```

   结果

   ```json
   {
     "took" : 74,
     "timed_out" : false,
     "_shards" : {
       "total" : 5,
       "successful" : 5,
       "skipped" : 0,
       "failed" : 0
     },
     "hits" : {
       "total" : 1,
       "max_score" : 0.2876821,
       "hits" : [
         {
           "_index" : "uyaki",
           "_type" : "employee",
           "_id" : "1",
           "_score" : 0.2876821,
           "_source" : {
             "first_name" : "zhang",
             "last_name" : "san",
             "age" : "18",
             "about" : "I love li si",
             "interests" : [
               "li si",
               "music"
             ]
           }
         }
       ]
     }
   }
   ```

   

## 使用Query DSL搜索

- 示例

  ```sh
  GET /uyaki/employee/_search
  {
    "query": {
      "match": {
        "last_name": "si"
      }
    }
  }
  ```

### match与match_phrase

- query -> match 模糊匹配

- query -> match_phrase 精确匹配

  > 如下结果中，`_score`表示匹配分数，匹配度

  ```json
  {
    "took" : 35,
    "timed_out" : false,
    "_shards" : {
      "total" : 5,
      "successful" : 5,
      "skipped" : 0,
      "failed" : 0
    },
    "hits" : {
      "total" : 1,
      "max_score" : 0.5753642,
      "hits" : [
        {
          "_index" : "uyaki",
          "_type" : "employee",
          "_id" : "1",
          "_score" : 0.5753642,
          "_source" : {
            "first_name" : "zhang",
            "last_name" : "san",
            "age" : "18",
            "about" : "I love li si",
            "interests" : [
              "li si",
              "music"
            ]
          }
        }
      ]
    }
  }
  
  ```

### highlight

- 语法

  ```sh
  GET /uyaki/employee/_search
  {
    "query": {
      "match_phrase": {
        "about": "love li"
      }
    },
    "highlight": {
      "fields": {
        "about":{}
      }
    }
  }
  ```

- 结果

  ```json
  {
    "took" : 252,
    "timed_out" : false,
    "_shards" : {
      "total" : 5,
      "successful" : 5,
      "skipped" : 0,
      "failed" : 0
    },
    "hits" : {
      "total" : 1,
      "max_score" : 0.5753642,
      "hits" : [
        {
          "_index" : "uyaki",
          "_type" : "employee",
          "_id" : "1",
          "_score" : 0.5753642,
          "_source" : {
            "first_name" : "zhang",
            "last_name" : "san",
            "age" : "18",
            "about" : "I love li si",
            "interests" : [
              "li si",
              "music"
            ]
          },
          "highlight" : {
            "about" : [
              "I <em>love</em> <em>li</em> si"
            ]
          }
        }
      ]
    }
  }
  ```

  

### 统计

```console
GET /uyaki/employee/_search
{
  "query": {
    "match": {
      "about": "love li"
    }
  },
  "aggs":{
    "all_interests":{
      "terms": {
        "field": "interests.keyword"
      }
    }
  }
}
```

- 结果：

  ```json
  {
    "took" : 90,
    "timed_out" : false,
    "_shards" : {
      "total" : 5,
      "successful" : 5,
      "skipped" : 0,
      "failed" : 0
    },
    "hits" : {
      "total" : 4,
      "max_score" : 0.5753642,
      "hits" : [
        {
          "_index" : "uyaki",
          "_type" : "employee",
          "_id" : "1",
          "_score" : 0.5753642,
          "_source" : {
            "first_name" : "zhang",
            "last_name" : "san",
            "age" : "18",
            "about" : "I love li si",
            "interests" : [
              "li si",
              "music"
            ]
          }
        },
        {
          "_index" : "uyaki",
          "_type" : "employee",
          "_id" : "3",
          "_score" : 0.2876821,
          "_source" : {
            "first_name" : "wang",
            "last_name" : "wu",
            "age" : "18",
            "about" : "I love zhang san",
            "interests" : [
              "zhang san",
              "music"
            ]
          }
        },
        {
          "_index" : "uyaki",
          "_type" : "employee",
          "_id" : "2",
          "_score" : 0.18232156,
          "_source" : {
            "first_name" : "li",
            "last_name" : "si",
            "age" : "18",
            "about" : "I love wang wu",
            "interests" : [
              "wang wu",
              "music"
            ]
          }
        },
        {
          "_index" : "uyaki",
          "_type" : "employee",
          "_id" : "4",
          "_score" : 0.18232156,
          "_source" : {
            "first_name" : "zhao",
            "last_name" : "liu",
            "age" : "18",
            "about" : "I love zhao liu",
            "interests" : [
              "zhaoliu",
              "music"
            ]
          }
        }
      ]
    },
    "aggregations" : {
      "all_interests" : {
        "doc_count_error_upper_bound" : 0,
        "sum_other_doc_count" : 0,
        "buckets" : [
          {
            "key" : "music",
            "doc_count" : 4
          },
          {
            "key" : "li si",
            "doc_count" : 1
          },
          {
            "key" : "wang wu",
            "doc_count" : 1
          },
          {
            "key" : "zhang san",
            "doc_count" : 1
          },
          {
            "key" : "zhaoliu",
            "doc_count" : 1
          }
        ]
      }
    }
  }
  ```

