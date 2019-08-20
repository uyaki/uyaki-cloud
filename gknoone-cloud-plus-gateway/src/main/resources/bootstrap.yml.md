spring:
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848
        file-extension: yaml
        namespace: 1241625f-714b-440d-8d71-32e58d531f3a
        group: gknoone-cloud-plus
        # 共享配置
        ext-config:
          # postgresql
          - data-id: app-common-postgresql.yaml
            group: gknoone-cloud-plus
            namespace: 1241625f-714b-440d-8d71-32e58d531f3a
            refresh: true
          # mybatis
          - data-id: app-common-mybatis.yaml
            group: gknoone-cloud-plus
            namespace: 1241625f-714b-440d-8d71-32e58d531f3a
            refresh: true