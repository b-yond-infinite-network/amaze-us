# <p>Mo Rawi Mission to Mars</p>
<p>This software a conceptualization of a Mars space launch vehicle.</p>
<p>It has 3 stages launch vehicle described, each described within a package, the total number of stages are 3.</p>
<p>Dependencies for packages are in the form of databases, mysql/mariadb for the booster package. mongodb for the cargo package.   
<p>Each package was given is based upon a different programming language</p> 
<p>Each stage package is to be run from a within a docker based container, locally tested using docker file, then docker-compose to connect together</p>
<p>Kompose the kubernetes based is used to translate docker compose based YAML files into the Kubernetes alternative services and deployments.</p>


##### Reasearch into the programming languages forming individual components was conducted to understand the functions,(Golang && NodeJS) in specific.

```
-----------------------------------------------------------------------
First Stage (Booster)                                   | Description |
------------------------------------------------------- | -------------
App                                                     |             |
  Handler :common,'HTTP functions handle input and error|             | 
          :fuelParts.go:                                |             |              
          :tanks.go:                                    |             |
  Module                                                |             |
          :module.go                                    |             |
  App     :app.go                                       |             |
config                                                  |             |
  config  :config.go                                    |             |
main.go                                                 |             |
--------------------------------------------------------|-------------|
Second Stage (Check Booster statue)                     |             |
--------------------------------------------------------|-------------|
stage2.py                                               |             |
--------------------------------------------------------|-------------|
Third Stage (Cargo)                                     |             |
--------------------------------------------------------|-------------|
App                                                     |             |
   Modules: cargo.js                                    |             |
   routes.js                                            |             |
Config                                                  |             |
   database.js                                          |             |                                          
Public                                                  |             |
   core.js                                              |             |
   index.html                                           |             |
README.md	                                        |             |
package-lock.json	                                |             |
package.json	                                        |             |
server.js                                               |             |
-----------------------------------------------------------------------

```
<p>Diagrams provided to visualize concepts and software flows, used draw.io.</p>
<p>[This is a general diagram with focus on the booster stage]<img src=Mars-Stages-Diagram-Booster.jpeg></p>
<p>The Image above detailed the booster stage, given as an example of the thinking process.</p> 


## Software activation stages:
- [1] Copy this folder into 'amaze-us/microservice/challenge-3'.
- [2] Initiate the 'CodeSourceDestMover.sh' script, this will copy relevent component source to desired location for the docker-compose to activate build.
- [3] initiate 'docker-compose up' this will process the 'docker-compose.yaml' file, it will complete steps below.
- [4] Build the container for the 'todoapp-db'. Always DB first then the app component that needs it.
- [5] Build  'booseter Go container', located in __MoRawi-MoissionToMars__/Booster.
- [6] Build stage2 container,(based upon python2.7).
- [7] Build mongo-db based container, (using docker-compose), named the folder cargo-db.
- [8] Build cargo,(node-js), based container, test dependencies,(using docker-compose).
- [9] A each components (container can be build individualy with docker-compose file or the Docker file kept in the spcecific container folder.
- [10] After test/validation is complete with success, try to translate to kuberneted based files using kompose, assuming Kubernetes is the target choice.
- [11] Ensure you have selected the version of kompose suitable for the Operating system you will use to process this code, defacto here is Linux.
- [12] Obtain kompose by 'curl -L https://github.com/kubernetes/kompose/releases/download/v1.20.0/kompose-linux-amd64 -o kompose'
- [13] Run kompose 'convert -f docker-compose.yaml'
- [14] Translate the successful services to Kubernetes portable units using Kompose..
- [15] Test all to ensure they work as required.
- [16] Created 'kubernetes-based-services' folder which hosts the translated services I made from running kompose 


### Test & Debug, tips and ideas: [Booster]
A tool called delve came handy, a link is left below:
```
go get -u github.com/go-delve/delve/cmd/dlv
```
Trying to test with dlv will reveal issues with code via the debug flag
testing the binary 'booster' done via the link below:
```
curl 127.0.0.1:3000/?users=booster
```

### Mysql [Booster dependency]

A good way to pass the db-create script through mysql-prompt:
```
   mysql -u root -e"set @temp=1; `cat $PATHTOMYSQLSCRIPT/mysql_config_linux.sql`"

```


<p>[The output of the test for port binding]<img src=BoosterTest.png></p>


###  Testing & Dependencies processing:
Early testing was implemented on virtual machines, this was to understand the software modules/components, solve dependency & better understanding the way in which porting to containers should be done.

Packages such as Node-JS based ones & Golang based ones , needed extas dependencies installed on the hosting vms, this was handled through the discovery & teting process, aimed at the software functionality:

```
└─┬ mongoose@5.8.9 
  ├── bson@1.1.3 
  ├── kareem@2.3.1 
  ├─┬ mongodb@3.4.1 
  │ ├─┬ require_optional@1.0.1 
  │ │ ├── resolve-from@2.0.0 
  │ │ └── semver@5.7.1 
  │ └─┬ saslprep@1.0.3 
  │   └─┬ sparse-bitfield@3.0.3 
  	│     └── memory-pager@1.5.0 
  ├── mongoose-legacy-pluralize@1.0.2 
  ├── mpath@0.6.0 
  ├─┬ mquery@3.2.2 
  │ ├── bluebird@3.5.1 
  │ └─┬ debug@3.1.0 
  │   └── ms@2.0.0 
  ├── ms@2.1.2 
  ├── regexp-clone@1.0.0 
  ├── safe-buffer@5.1.2 
  ├── sift@7.0.1 
  └── sliced@1.0.1 


```

### Docker compose 
To bring up the components using a docker compose command. Each folder for each stage unit had a docker compose file to allow for unit test. A central docker compose file is available to run the entire collection of services.

```

 docker-compose up    # -d flag might follow the up to make it run in the background

```
To stop

``` 
 docker-compose stop <service name> 
 # The specific service name would be arrived at from the docker-compose file 

```
To start specific service 

```
 docker-compose start <service name>

```

To delete service 

```
 docker-compose rm -f <service name>

```


To run a command on a container, used for testing

```
 docker-compose run <service name > command # command could be ls

```
To scale

```
 docker-compose scale <service name>=<number> 

```

## Current status:

* Current chosen topic microservices:challenge-3

* Containers built with success 
   *todoapp-db
   *booster
   *stage2
   *cargodb
* Software Tested
   * virtual-machine-test-scripts
   * CodeSourceDestMover.sh
   * docker-compose.yaml
   * individual Dockerfile units

* ToDo
   * Fix bugs with the cargo container code.
   * Run integration testing 
   * Run full function testin

#### Note 

Expecting to proggress with the remaining taks within this weekend, once finished with this stage.
I shall move to another component witin the amaze us library of scenarios witin microservices.
 
