# https://gist.github.com/sjeandeaux/e804578f9fd68d7ba2a5d695bf14f0bc
help: ## prints help.
	@grep -hE '^[a-zA-Z_-]+.*?:.*?## .*$$' ${MAKEFILE_LIST} | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

up: #spawn all TODO use releases 
	docker-compose -f ./docker-compose.yml -f ./booster/docker-compose.yml -f ./cargo/docker-compose.yml -f ./docker-compose-dev.yml up --detach --build