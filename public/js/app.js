// js/app.js

angular.module('movies', ['ngRoute','movies.services', 'movies.controllers'], function(){
  //I can use controllers from 'movies.controllers'

}).config(['$routeProvider',
  function($routeProvider) {
    $routeProvider
      .when('/movies', {
        templateUrl: 'pages/movies.html',
        controller: 'MoviesCtrl'
      })
      .when('/genres', {
        templateUrl: 'pages/genre.html',
        controller: 'GenresCtrl'
      })
      .when('/genres/:id', {
        templateUrl: 'pages/genre-details.html',
        controller: 'GenreDetailsCtrl'
      })
      .otherwise({
        redirectTo: '/movies'
      });
  }]);
