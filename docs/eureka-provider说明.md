# README
## 目录结构
1. 生成命令
```bash
tree -N -d  -L 2
tree -N -d  -L 2 > tree.md
```
2. 目录解析
```xml
.
├── gknoone-cloud-plus-common                   ------通用模块
│   ├── gknoone-cloud-plus-common-core          ------核心代码模块
│   └── gknoone-cloud-plus-common-util          ------通用工具类
├── gknoone-cloud-plus-eureka                   ------Eureka-server模块
├── gknoone-cloud-plus-provider
│   ├── gknoone-cloud-plus-provider-hello
│   └── gknoone-cloud-plus-provider-test
└── gknoone-cloud-plus-provider-api
    └── gknoone-cloud-plus-provider-hello-api
```
- 基础的底层代码，如`BaseController`放在`gknoone-cloud-plus-common-core`模块
-
