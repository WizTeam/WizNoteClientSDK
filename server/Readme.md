为知笔记服务端部署需求

## 基础环境需求

|  服务 | 版本 |
--|--
| 操作系统 | CentOS 7 |
| Java | JDK 1.8 |
| MySQL| 5.6 |
| Tomcat |8.5 |
| Node.js|6.x |
| Redis|3.x |
| Elasticsearch| 5.x |


## 主要服务

|应用服务|用途|是否可以跨区域分布式部署| |
--|--|--|--
as|账号服务，用于管理为知笔记账号等相关信息|不能跨区域部署。|无状态服务，可以启用多个服务同时工作
ks|数据服务，以knowledge base（简称kb，知识库。一个用户的个人笔记为一个kb，一个群组的笔记也是一个kb）为单位，存储笔记/附件等数据。同时包含搜索服务。|可以跨区域部署，不同的kb可以保存在不同的区域。|无状态服务，可以启用多个服务同时工作


## 部署方式

| 部署方式|多机支持 | 所需应用环境|笔记数据保存
--|--|--|--
Docker|单机服务|全部内置|保存在宿主机磁盘上面
一键式部署|单机服务|全部内置|保存在单一机器内
手工部署|单机/多机/跨区域服务|可以使用外部提供的数据库，redis，elasticsearch，redis环境等，也可以自行安装。|可以保存在本地磁盘，对象存储（S3，OSS等），NAS等存储设备或者服务里面。


## 可靠性保障

as/ks服务可以，都是无状态服务，可以在同一台或者多台服务器上面，启动任意多个服务，并通过负载均衡等方式，共同对外提供服务。

其中ks每一个区域，也可以支持任意多个服务，统一对外提供服务。当其中任意一个节点出现故障，可以由剩余节点继续保证对外服务

## 故障检测

内置故障检测，可以通过第三方状态检测服务来检查故障并进行故障通知。



