java -jar target/runbud-1.0-SNAPSHOT.jar db dump config-local.yml > src/main/resources/migrations.xml
sed 's/BIGINT(19)/BIGINT/' src/main/resources/migrations.xml > src/main/resources/migrations2.xml
sed 's/BOOLEAN(1)/BOOLEAN/' src/main/resources/migrations2.xml > src/main/resources/migrations.xml
sed 's/DOUBLE(17)/NUMERIC(10,10)/' src/main/resources/migrations.xml > src/main/resources/migrations2.xml
sed 's/schemaName="PUBLIC" //' src/main/resources/migrations2.xml > src/main/resources/migrations.xml
rm src/main/resources/migrations2.xml
#mv src/main/resources/migrations2.xml src/main/resources/migrations.xml
