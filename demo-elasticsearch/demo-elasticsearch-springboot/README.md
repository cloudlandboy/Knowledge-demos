## 版本
- springboot：2.3.8
- elasticsearch：docker 7.6.2

## docker安装elasticsearch
1. 下载镜像
   ```shell
    docker pull elasticsearch:7.6.2
   ```
2. 运行
   ```shell
    docker run -d --name my_elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.6.2
   ```
3. 安装ik分词器
   ```shell
    docker cp 本机ik分词器路径 my_elasticsearch:/usr/share/elasticsearch/plugins
    # 进入容器
    docker exec -it my_elasticsearch /bin/bash
    # 解压
    unzip ik压缩包名称 -d analysis-ik
   ```
4. 重启容器