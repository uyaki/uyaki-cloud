spring:
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848
        file-extension: yaml
        namespace: a2694131-cfb9-4b06-bdf8-d319d22aff48
        group: gknoone-cloud-plus
        # 共享配置
        ext-config:
          # postgresql
          - data-id: app-common-postgresql.yaml
            group: gknoone-cloud-plus
            namespace: a2694131-cfb9-4b06-bdf8-d319d22aff48
            refresh: true
          # mybatis
          - data-id: app-common-mybatis.yaml
            group: gknoone-cloud-plus
            namespace: a2694131-cfb9-4b06-bdf8-d319d22aff48
            refresh: true