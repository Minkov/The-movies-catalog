// js/services.js


angular.module('movies.services', [])
  .factory('genresService', function($http){
    return {
      all: function(callbackFunc) {
        var url = "api/genres";
        $http.get(url)
          .success(function(data){
            callbackFunc(data);
          });
      },
      byId: function(id, callbackFunc){
        var url = "api/genres/" + id;
        $http.get(url)
          .success(function(data){
            callbackFunc(data);
          });
      }
    };
  })
  .factory('moviesService', function($http){
    return {
      all: function(page, callbackFunc) {
        var url = "api/movies?pageSize=9&page=" + page;
        $http.get(url)
          .success(function(data){
            callbackFunc(data);
          });
      },
      add: function(movie, callbackFunc){
        var url = 'api/movies';

        var req = {
           method: 'POST',
           url: url,
           headers: {
             'Content-Type': 'application/json'
           },
           data: movie
         };

          $http(req).then(function(response){
            callbackFunc(response.data);
          });
      }
    };
  });
