var scotchCargo = angular.module('scotchCargo', []);

function mainController($scope, $http) {
	$scope.formData = {};
	$scope.config = {};

	// when landing on the page, get all todos and show them
	$http.get('/config')
		.success(function(data) {
      $scope.config = data;
      $http.get($scope.config.services.cargodata.host + '/cargo')
        .success(function(data) {
          $scope.cargo_list = data;
        });
		})
		.error(function(data) {
			console.log('Error: ' + data);
		});

	// when submitting the add form, send the text to the node API
	$scope.createCargo = function() {
		$http.post($scope.config.services.cargodata.host + '/cargo', $scope.formData)
			.success(function(data) {
				$scope.formData = {}; // clear the form so our user is ready to enter another
				$scope.cargo_list = data;
				console.log(data);
			})
			.error(function(data) {
				console.log('Error: ' + data);
			});
	};

	// delete a todo after checking it
	$scope.deleteCargo = function(id) {
		$http.delete($scope.config.services.cargodata.host + '/cargo/' + id)
			.success(function(data) {
				$scope.cargo_list = data;
			})
			.error(function(data) {
				console.log('Error: ' + data);
			});
	};

}
