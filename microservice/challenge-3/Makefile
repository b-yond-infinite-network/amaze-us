VERSION=1.0.0
BUILD_DATE=$(shell date +%Y-%m-%dT%H:%M:%S%z)

# https://gist.github.com/sjeandeaux/e804578f9fd68d7ba2a5d695bf14f0bc
help: ## prints help.
	@grep -hE '^[a-zA-Z_-]+.*?:.*?## .*$$' ${MAKEFILE_LIST} | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

up-dev: ## spawn all the containers => http://localhost:8666/haproxy?stats
	VERSION=$(VERSION) BUILD_DATE=$(BUILD_DATE) docker-compose -f ./docker-compose.yml -f ./booster/docker-compose.yml -f ./cargo/docker-compose.yml -f ./docker-compose-dev.yml up --detach --build --scale stage2=0

run-stage2: ## run stage2
	VERSION=$(VERSION) BUILD_DATE=$(BUILD_DATE) docker-compose -f ./docker-compose.yml -f ./booster/docker-compose.yml -f ./cargo/docker-compose.yml -f ./docker-compose-dev.yml run stage2