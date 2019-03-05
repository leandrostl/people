angular.module('App', [])
.controller('PeopleCtrl', function($scope, $http) {
    $http.get('http://localhost:8080/rest/pessoas').
        then(function(response) {
            $scope.people = response.data;
            $scope.headers = [
            	{ name: 'id', label: 'Id', width: '70px'},
            	{ name: 'name', label: 'Name', width: '300px'},
            	{ name: 'street', label: 'Street', width: '300px'},
            	{ name: 'number', label: 'Num', width: '120px'},
            	{ name: 'neighborhood', label: 'Neighborhood', width: '200px'},
				{ name: 'city', label: 'City', width: '200px'},
            	{ name: 'state', label: 'State', width: '200px'},
            	{ name: 'cellphone', label: 'Cellphone', width: '200px'},
            	{ name: 'phone', label: 'Phone', width: '200px'},
            ];
        });
});
