# 操作

## GET

### - pertty 美化输出结果

- 示例

  ```sh
  GET /gknoone/employee/1?pretty
  ```

- 结果

  ```json
  {
    "_index" : "gknoone",
    "_type" : "employee",
    "_id" : "1",
    "_version" : 2,
    "_seq_no" : 1,
    "_primary_term" : 1,
    "found" : true,
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
  ```
  
  > 添加 -i 返回，反馈头文件
  
  ```sh
  curl -i GET /gknoone/employee/1?pretty
  ```

### - _source

#### 用法一：过滤field

- 示例

  ```sh
  # 多个field 用逗号过滤
  GET /gknoone/employee/1?_source=age,interests
  ```

- 结果

  ```json
  {
    "_index" : "gknoone",
    "_type" : "employee",
    "_id" : "1",
    "_version" : 2,
    "_seq_no" : 1,
    "_primary_term" : 1,
    "found" : true,
    "_source" : {
      "interests" : [
        "li si",
        "music"
      ],
      "age" : "18"
    }
  }
  ```


#### 用法二：只显示field，过滤其他元数据

- 示例

  ```sh
  GET /gknoone/employee/1/_source
  ```

- 结果

  ```json
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

## POST

### - ?op_type=create等价于/_create

> 如果存在同名文件，不会覆盖，会返回409HTTP状态码

- 示例

  ```sh
  # POST /gknoone/employee/5/_create
  POST /gknoone/employee/5?op_type=create
  {
    "first_name" : "张",
    "last_name" : "龙",
    "age" : "18",
    "about" : "I hate li si",
    "interests" : [
      "li si",
      "fly"
    ]
  }
  ```

- 结果

  ```json
  {
    "_index" : "gknoone",
    "_type" : "employee",
    "_id" : "5",
    "_version" : 1,
    "result" : "created",
    "_shards" : {
      "total" : 2,
      "successful" : 1,
      "failed" : 0
    },
    "_seq_no" : 0,
    "_primary_term" : 1
  }
  ```

  ```json
  {
    "error": {
      "root_cause": [
        {
          "type": "version_conflict_engine_exception",
          "reason": "[employee][5]: version conflict, document already exists (current version [1])",
          "index_uuid": "j-gD7jb6STq9hFVe8tlPFA",
          "shard": "1",
          "index": "gknoone"
        }
      ],
      "type": "version_conflict_engine_exception",
      "reason": "[employee][5]: version conflict, document already exists (current version [1])",
      "index_uuid": "j-gD7jb6STq9hFVe8tlPFA",
      "shard": "1",
      "index": "gknoone"
    },
    "status": 409
  }
  ```

### - /_update

```
POST /gknoone/employee/5/_update
{
  "first_name" : "张",
  "last_name" : "龙",
  "age" : "18",
  "about" : "I hate li si",
  "interests" : [
    "li si",
    "fly"
  ]
}
```



## DELETE

> 不会马上生效，只是状态标记为删除，之后添加更多索引的时候，es在后台删除

