VERSION=1.0.0
BUILD_DATE=$(shell date +%Y-%m-%dT%H:%M:%S%z)

BUILD_ARGS=--build-arg VERSION=$(VERSION) --build-arg BUILD_DATE="$(BUILD_DATE)"

# https://gist.github.com/sjeandeaux/e804578f9fd68d7ba2a5d695bf14f0bc
help: ## prints help.
	@grep -hE '^[a-zA-Z_-]+.*?:.*?## .*$$' ${MAKEFILE_LIST} | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

docker-compose-build: ## builds the application image with docker-compose.
	docker-compose build $(BUILD_ARGS)

docker-compose-up: ## spawns the containers.
	VERSION=$(VERSION) BUILD_DATE=$(BUILD_DATE) docker-compose -f docker-compose.yml -f docker-compose-dev.yml  up --build --detach

it-test: ## TODO run integration test
	VERSION=$(VERSION) BUILD_DATE=$(BUILD_DATE) docker-compose -f docker-compose.yml -f docker-compose-integration.yml run postman

ui-test: ## TODO run integration test
	echo TODO see casperjs