mvn clean install -DskipTests
scp example-springboot/target/example-springboot-0.9.3.jar root@87.106.171.45:elasticobjects2.jar
export pid = `ssh root@87.106.171.45 ps -ef| grep java`
nohup java -jar elasticobjects2.jar --server.port=80 2>error.log 1>test.log &