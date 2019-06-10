# HIVE Playground

## Base Images
### Hadoop Base Image
```bash
docker-compose -f ./compose-base.yml build hadoop-base
```

## Service Images

## HIVE Client
```bash
cd JavaHIVEClient/
mvn package assembly:single
java -jar target/HiveJdbcClient-1.0-jar-with-dependencies.jar 1000000
```
