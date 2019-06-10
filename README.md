# HIVE Playground

## Base Images
### Hadoop Base Image
```bash
docker-compose -f ./compose-base.yml build hadoop-base
```

## Service Images
### HIVE Server
```bash
docker-compose -f ./compose.yml build hive-server
```

## HIVE Client
```bash
cd JavaHIVEClient/
mvn package assembly:single
java -jar target/HiveJdbcClient-1.0-jar-with-dependencies.jar 1000000
```

## TODO
```
https://hub.docker.com/r/bde2020/hadoop-datanode
https://hub.docker.com/r/bde2020/hadoop-namenode
https://hub.docker.com/r/bde2020/hive-metastore-postgresql
https://github.com/big-data-europe/docker-hive-metastore-postgresql/tree/2.3.0
```
