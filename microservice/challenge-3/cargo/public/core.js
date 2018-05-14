var scotchCargo = angular.module('scotchCargo', ['toaster', 'ngAnimate']);

scotchCargo.controller("mainController", function($scope, $http, toaster) {
	$scope.formData = {};

	// when landing on the page, get all todos and show them
	$scope.GetAllCargo = function() {
		$http.get('http://localhost:3000/api/cargos')
		.then(function(response) {
			$scope.cargo_list = response.data;
		},function(data) {
			console.log('Error: ' + response.data);
		});
	};
	// when submitting the add form, send the text to the node API
	$scope.createCargo = function() {
		$http.post('http://localhost:3000/api/cargos', $scope.formData)
			.then(function(response) {
				$scope.emptyForm();
				$scope.GetAllCargo();
			},function(data) {
				console.log('Error: ' + response.data);
			});
	};
	$scope.launch = function() {
		toaster.pop('info', "Launch in 3", "3 Secondes to launch");
		setTimeout(function(){ 
			toaster.pop('warning', "Mars X", "let's go");
		}, 3000);
		setTimeout(function(){ 
			toaster.pop('warning', "Mars X", "let's go");
		}, 6000);
		setTimeout(function(){ 
			$scope.checkbooster();
		}, 9000);
		setTimeout(function(){ 
			$scope.checkstage2();
		}, 12000);
		setTimeout(function(){ 
			$scope.releasestage2();
		}, 15000);
		
	};
	// delete a todo after checking it
	$scope.deleteCargo = function(id) {
		$http.delete('http://localhost:3000/api/cargos/' + id)
			.then(function(response) {
				$scope.GetAllCargo();
			},function(data) {
				console.log('Error: ' + response.data);
			});
	};
	$scope.checkstage2 = function() {
		$http.get('http://localhost:5000/checkstage2')
		.then(function(response) {
			toaster.pop('info', "Mars X", response.data.state);
		},function(data) {
			console.log('Error: ' + response.data);
		});
	};
	$scope.releasestage2 = function() {
		$http.get('http://localhost:5000/releasestage2')
		.then(function(response) {
			toaster.pop('info', "Mars X", response.data.state);
		},function(data) {
			console.log('Error: ' + response.data);
		});
	};
	$scope.checkbooster = function() {
		$http.get('http://localhost:5000/checkbooster')
		.then(function(response) {
			toaster.pop('info', "Mars X", response.data.state);
		},function(data) {
			console.log('Error: ' + response.data);
		});
	};
	// Empty data form
	$scope.emptyForm = function(id) {
		$scope.formData = {}; // clear the form so our user is ready to enter another
	};
	$scope.GetAllCargo();
});
