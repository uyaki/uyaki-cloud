
<!-- @import "[TOC]" {cmd="toc" depthFrom=2 depthTo=6 orderedList=false} -->
<!-- code_chunk_output -->

* [描述](#描述)
* [修复](#修复)
* [验证](#验证)

<!-- /code_chunk_output -->


## 描述

curl 命令定向到了已经没有续期的代理上了。

![image-20190815150359578](assets/image-20190815150359578.png)

## 修复

```bash
vim ~/.curlrc
# 修改配置内容如下,与ss设置保持一致
socks5=127.0.0.1:1086
```

![image-20190815151014948](assets/image-20190815151014948.png)

## 验证

![image-20190815150716028](assets/image-20190815150716028.png)
