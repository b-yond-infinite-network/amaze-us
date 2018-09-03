app.controller("mvcCRUDCtrl", function ($scope, crudAJService) {
    $scope.SortColumn = "track_name";
    $scope.One = false;
    $scope.loader = false;
    $scope.SearchDataBase = function () {
        $scope.loader = true;
        var User = {
            artist_name: $scope.artist_name
        };
        var getdata = crudAJService.getArtist(User.artist_name);
        getdata.then(function (book) {
            $scope.One = true;
            $scope.books = book.data;
        }, function () {
            alert('Error');
        });
        $scope.loader = false;
    }

    $scope.GetLyrics = function (book) {
        $scope.loader = true;
        var getBookData = crudAJService.GetLyrics(book.track_id);
        getBookData.then(function (lyrics) {
            $scope.One = false;
            $scope.lyrics = lyrics.data;
        }, function () {
            alert('Error in fetching data');
        });
        $scope.loader = false;
    }   
});