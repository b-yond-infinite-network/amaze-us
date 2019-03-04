DOCKER_IMAGE=booster
VERSION=1.0.0
BUILD_DATE=$(shell date +%Y-%m-%dT%H:%M:%S%z)

PKGGOFILES=$(shell go list ./... | grep -v /vendor/)

BUILD_ARGS=--build-arg VERSION=$(VERSION) --build-arg BUILD_DATE="$(BUILD_DATE)"

# https://gist.github.com/sjeandeaux/e804578f9fd68d7ba2a5d695bf14f0bc
help: ## prints help.
	@grep -hE '^[a-zA-Z_-]+.*?:.*?## .*$$' ${MAKEFILE_LIST} | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

.PHONY: tools
tools: ## install tools to develop
	go get -u github.com/golang/lint/golint
	go get github.com/axw/gocov/gocov
	go get github.com/AlekSi/gocov-xml
	go get github.com/axw/gocov/gocov
	go get github.com/mattn/goveralls
	go get golang.org/x/oauth2
	go get github.com/stretchr/testify/assert

.PHONY: build
build: 	##Build the binaru
	CGO_ENABLED=0 go build -installsuffix 'static' -o ./target/booster .

.PHONY: fmt
fmt: ## go fmt on packages
	go fmt $(PKGGOFILES)

.PHONY: vet
vet: ## go vet on packages
	go vet $(PKGGOFILES)

.PHONY: lint
lint: ## go vet on packages
	golint -set_exit_status=true $(PKGGOFILES)

.PHONY: test-coverage
test-coverage: clean fmt vet ## for jenkins TODO include lint
	gocov test $(PKGGOFILES) -cpu=2 -p=2 -v $(LDFLAGS) | gocov-xml > ./target/coverage-test-it-test.xml

docker-compose-build: ## builds the application image with docker-compose.
	docker-compose build $(BUILD_ARGS)

docker-compose-up: ## spawns the containers.
	VERSION=$(VERSION) BUILD_DATE=$(BUILD_DATE) docker-compose up --build --detach

clean: ## clean the target folder
	@rm -fr target 
	@mkdir target

it-test: ## run integration test
	VERSION=$(VERSION) BUILD_DATE=$(BUILD_DATE) docker-compose -f docker-compose.yml -f docker-compose-integration.yml run postman