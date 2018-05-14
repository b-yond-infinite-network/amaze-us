echo "Run Tests"
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
echo "Build containers for testing and run the tests" 
docker-compose -f docker-compose.test.yml up -d --build --force-recreate && docker attach test 