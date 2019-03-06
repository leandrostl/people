var app = angular.module('App', []);

app.controller('PeopleCtrl', function ($scope, $http) {
	$scope.headers = [
		{ name: 'id', label: 'Id', width: '70px', type: 'text', value: null },
		{ name: 'name', label: 'Name', width: '300px', type: 'text', value: null },
		{ name: 'street', label: 'Street', width: '300px', type: 'text', value: null },
		{ name: 'number', label: 'Num', width: '120px', type: 'number', value: null },
		{ name: 'neighborhood', label: 'Neighborhood', width: '200px', type: 'text', value: null },
		{ name: 'city', label: 'City', width: '200px', type: 'text', value: null },
		{ name: 'state', label: 'State', width: '200px', type: 'text', value: null },
		{ name: 'cellphone', label: 'Cellphone', width: '200px', type: 'text', value: null },
		{ name: 'phone', label: 'Phone', width: '200px', type: 'text', value: null },
	];

	$scope.refresh = function () {
		$http.get('/rest/pessoas')
			.then(function (response) {
				$scope.headers.forEach(header => header.value = null);
				$scope.people = response.data;
			})
	}

	$scope.refresh();

	$scope.remove = function (id) {
		$http.get('/rest/pessoa/' + id)
			.then(function (response) {
				if (window.confirm('Are you sure you want to delete ' + response.data.name + '?'))
					$http.delete('/rest/pessoa/remove/' + id)
						.then(function (data) {
							$scope.refresh();
						})
			})
	}

	$scope.save = function (headers) {
		var person = {
			name: headers[1].value,
			street: headers[2].value,
			number: headers[3].value,
			neighborhood: headers[4].value,
			city: headers[5].value,
			state: headers[6].value,
			cellphone: headers[7].value,
			phone: headers[8].value,
		}
		$http.post('rest/pessoa/save', data = JSON.stringify(person))
			.then(function (data) {
				$scope.refresh();
			})

	}
});
