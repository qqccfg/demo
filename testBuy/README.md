# 商城

1. 使用springboot+mybatis +logback+zookeeper+ redis+elasticsearch+elasticJob+shardingJdbc+springcloud（ 服务发现 -eureka，配置中心，日志侦探-zipkin，熔断-hystrix，服务调用-feign，网关-zuul）。
2. 业务
   - 登陆，注册（注册使用了分布式锁-互斥锁）。
   - 列出分类（做了缓存）、分类下的产品、检索商品（使用elasticsearch完成）、产品详细（做了缓存）。
   - 下单。
   - 扣减库存、查询库存。
   - key的生成。
   - 任务调度（主要用于判断订单是否过期）
   - 支付（支付包沙箱环境）
3. 特点
   - 模拟高并发场景，加入了锁，缓存，数据库分表。
   - elasticsearch做检索商品，可以分词，更精确，智能。
   - 产品以spu的概率来进行描述。