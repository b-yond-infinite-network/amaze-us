echo "Check Spaceship"
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
echo "Build containers for testing and run the tests" 
docker-compose -f docker-compose.test.yml up -d --build --force-recreate
echo "All test pass: Good to go"
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
echo "Build and launch the services" 
docker-compose  up -d --build
echo "Ready to flight"
echo " go to your favorite web browser and http://localhost"