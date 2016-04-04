// js/controller.js

angular.module('movies.controllers', [])
  .controller('MoviesCtrl', function($scope, $location, moviesService){
    console.log('In Movies Controller');
    console.log($location.search());
    $scope.page = +($location.search().page || 1);
    if($scope.page <= 1){
      $scope.page = 1;
      $scope.isPrevDisabled = true;
    }

    moviesService.all(  $scope.page , function(data){
      $scope.movies = data;
    });
  })
  .controller('GenresCtrl', function($scope, genresService){
    console.log('In Genres Controller');

    genresService.all(function(data){
      $scope.genres = data;
    });

    $scope.onClick = function(){
      console.log(new Date());
    };
})
  .controller('GenreDetailsCtrl', function($scope, $routeParams, genresService){
    console.log('In Genre Details Controller');
    var movies;
    $scope.id = $routeParams.id;
    $scope.page = 1;
    var pageSize = 6;
    $scope.isPrevDisabled = true;
    $scope.isNextDisabled = false;

    function changePage(increment){
      var minPage = 1,
        maxPage = ((movies.length / pageSize) | 0) + 1;

      if(($scope.page === minPage && increment < 0 ) || ($scope.page === maxPage && increment > 0)){
        return;
      }

      $scope.page += increment;
      $scope.isPrevDisabled = false;
      $scope.isNextDisabled = false;

      if($scope.page === minPage && increment < 0 ){
        $scope.isPrevDisabled = true;
      }
      if($scope.page === maxPage && increment > 0){
        $scope.isNextDisabled = true;
      }

      $scope.movies = movies.slice(($scope.page -1 ) * pageSize, $scope.page * pageSize);
    }

    $scope.goToPrevPage = function(){
      changePage(-1);
    }

    $scope.goToNextPage = function(){
      changePage(+1);
    }

    $scope.rate = 7;
    $scope.max = 10;
    $scope.isReadonly = false;

    $scope.hoveringOver = function(value) {
      $scope.overStar = value;
      $scope.percent = 100 * (value / $scope.max);
    };

    $scope.ratingStates = [
      {stateOn: 'glyphicon-ok-sign', stateOff: 'glyphicon-ok-circle'},
      {stateOn: 'glyphicon-star', stateOff: 'glyphicon-star-empty'},
      {stateOn: 'glyphicon-heart', stateOff: 'glyphicon-ban-circle'},
      {stateOn: 'glyphicon-heart'},
      {stateOff: 'glyphicon-off'}
    ];


    genresService.byId($scope.id, function(data){
      movies = data.movies;
      $scope.name = data.name;
      $scope.id = data.id;
      $scope.movies = movies.slice(($scope.page - 1) * pageSize, $scope.page * pageSize);
    });
  });
