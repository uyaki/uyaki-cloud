# elasticsearch环境

<!-- @import "[TOC]" {cmd="toc" depthFrom=2 depthTo=6 orderedList=false} -->
<!-- code_chunk_output -->

* [安装](#安装)
* [路由](#路由)
* [查看健康状态](#查看健康状态)

<!-- /code_chunk_output -->

## 安装

```bash
brew install elasticsearch
brew install kibana
brew services start elasticsearch
brew install kibana
```

## 路由

- elasticsearch:**http://localhost:9200**
- Kibana:**http://localhost:5601**

## 查看健康状态

**http://localhost:9200/_cat/health?v**

![image-20190816162659971](assets/image-20190816162659971.png)

- green:一切都很好（集群功能齐全）
- yellow:所有数据都可用，但尚未分配一些副本（群集功能齐全）
- Red:某些数据由于某种原因不可用（群集部分功能）
