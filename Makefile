build:
	docker-compose -f ./compose-base.yml build
run:
	docker-compose -f ./compose.yml up -d
clean:
	rm -rf /opt/helios/hadoop/
