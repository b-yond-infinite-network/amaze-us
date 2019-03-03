DOCKER_IMAGE=booster
DOCKER_VERSION=1.0.0

help: ## this help https://gist.github.com/sjeandeaux/e804578f9fd68d7ba2a5d695bf14f0bc
	@grep -hE '^[a-zA-Z_-]+.*?:.*?## .*$$' ${MAKEFILE_LIST} | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

docker-build: ## this command line build application image
	docker build --file Dockerfile --tag $(IMAGE):$(VERSION) .
