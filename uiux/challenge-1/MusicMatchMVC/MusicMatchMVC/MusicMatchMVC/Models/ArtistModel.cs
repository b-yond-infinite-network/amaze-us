using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MusicMatchMVC.Models
{
    public class ArtistModel
    {
        public int artist_id { get; set; }
        public string artist_mbid { get; set; }
        public string artist_name { get; set; }
        public string artist_country { get; set; }
        public int artist_rating { get; set; }
        public string artist_twitter_url { get; set; }
        public string artist_vanity_id { get; set; }//
        public string artist_edit_url { get; set; }//
        public string artist_share_url { get; set; }//
    }

    public class Header
    {
        public int status_code { get; set; }
        public double execute_time { get; set; }
        public int available { get; set; }
    }

    public class PrimaryGenres
    {
        public List<object> music_genre_list { get; set; }
    }

    public class SecondaryGenres
    {
        public List<object> music_genre_list { get; set; }
    }

    public class ArtistCredits
    {
        public List<object> artist_list { get; set; }
    }

    public class Artist
    {
        public int artist_id { get; set; }
        public string artist_mbid { get; set; }
        public string artist_name { get; set; }
        public List<object> artist_name_translation_list { get; set; }
        public string artist_comment { get; set; }
        public string artist_country { get; set; }
        public List<object> artist_alias_list { get; set; }
        public int artist_rating { get; set; }
        public PrimaryGenres primary_genres { get; set; }
        public SecondaryGenres secondary_genres { get; set; }
        public string artist_twitter_url { get; set; }
        public string artist_vanity_id { get; set; }
        public string artist_edit_url { get; set; }
        public string artist_share_url { get; set; }
        public ArtistCredits artist_credits { get; set; }
        public int restricted { get; set; }
        public int managed { get; set; }
        public DateTime updated_time { get; set; }
    }

    public class ArtistList
    {
        public Artist artist { get; set; }
    }

    public class Body
    {
        public List<ArtistList> artist_list { get; set; }
    }

    public class Message
    {
        public Header header { get; set; }
        public Body body { get; set; }
    }

    public class RootObject
    {
        public Message message { get; set; }
    }
}