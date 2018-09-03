app.service("crudAJService", function ($http) {
    // get artist by name
    this.getArtist = function (artist_name) {
        var response = $http({
            method: "get",
            url: "../../Home/FetchArtist",
            params: {
                getname: JSON.stringify(artist_name)
            }
        });
        return response;
    }

    //Get Lyrics
    this.GetLyrics = function (track_id) {
        var response = $http({
            method: "post",
            url: "../../Home/FetchLyrics",
            params: {
                track_id: JSON.stringify(track_id)
            }
        });
        return response;
    }
});