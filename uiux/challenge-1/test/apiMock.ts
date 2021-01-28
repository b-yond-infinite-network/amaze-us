/**
 * Gets an artist chart response
 * @returns [page_size, responseBody]
 */
export function getArtistChartMock(): any {
  return [
    3,
    {
      message: {
        header: { status_code: 200, execute_time: 0.007850170135498 },
        body: {
          artist_list: [
            {
              artist: {
                artist_id: 24410130,
                artist_name: "BTS",
                artist_name_translation_list: [
                  {
                    artist_name_translation: {
                      language: "KO",
                      translation: "\ubc29\ud0c4\uc18c\ub144\ub2e8",
                    },
                  },
                ],
                artist_comment: "",
                artist_country: "KR",
                artist_alias_list: [
                  { artist_alias: "\ubc29\ud0c4\uc18c\ub144\ub2e8" },
                  { artist_alias: "Bulletproof Boy Scouts" },
                  { artist_alias: "Bangtan Boys" },
                  { artist_alias: "The Bangtan Boys" },
                  { artist_alias: "BTS" },
                  { artist_alias: "\u9632\u5f48\u5c11\u5e74\u5718" },
                  { artist_alias: "\u9632\u5f3e\u5c11\u5e74\u56e3" },
                ],
                artist_rating: 100,
                artist_twitter_url: "https://twitter.com/BTS_twt",
                artist_credits: { artist_list: [] },
                restricted: 0,
                updated_time: "2014-10-30T14:13:20Z",
                begin_date: "2013-06-13",
                end_date: "0000-00-00",
              },
            },
            {
              artist: {
                artist_id: 45643815,
                artist_name: "Jawsh 685 feat. Jason Derulo",
                artist_name_translation_list: [],
                artist_comment: "",
                artist_country: "",
                artist_alias_list: [],
                artist_rating: 72,
                artist_twitter_url: "",
                artist_credits: {
                  artist_list: [
                    {
                      artist: {
                        artist_id: 45130044,
                        artist_name: "Jawsh 685",
                        artist_name_translation_list: [],
                        artist_comment: "",
                        artist_country: "",
                        artist_alias_list: [],
                        artist_rating: 53,
                        artist_twitter_url: "",
                        artist_credits: { artist_list: [] },
                        restricted: 0,
                        updated_time: "2020-04-24T11:04:12Z",
                        begin_date: "0000-00-00",
                        end_date: "0000-00-00",
                      },
                    },
                    {
                      artist: {
                        artist_id: 40665287,
                        artist_name: "Jason Derulo",
                        artist_name_translation_list: [],
                        artist_comment: "",
                        artist_country: "",
                        artist_alias_list: [],
                        artist_rating: 34,
                        artist_twitter_url: "",
                        artist_credits: { artist_list: [] },
                        restricted: 0,
                        updated_time: "2019-10-18T23:50:36Z",
                        begin_date: "0000-00-00",
                        end_date: "0000-00-00",
                      },
                    },
                  ],
                },
                restricted: 0,
                updated_time: "2020-06-11T03:21:37Z",
                begin_date: "0000-00-00",
                end_date: "0000-00-00",
              },
            },
            {
              artist: {
                artist_id: 27658352,
                artist_name: "Luke Combs",
                artist_name_translation_list: [],
                artist_comment: "",
                artist_country: "",
                artist_alias_list: [],
                artist_rating: 65,
                artist_twitter_url: "",
                artist_credits: { artist_list: [] },
                restricted: 0,
                updated_time: "2014-03-15T20:45:39Z",
                begin_date: "0000-00-00",
                end_date: "0000-00-00",
              },
            },
          ],
        },
      },
    },
  ]
}

export function getArtistMock(): any {
  return [
    118,
    {
      message: {
        header: { status_code: 200, execute_time: 0.0064120292663574 },
        body: {
          artist: {
            artist_id: 118,
            artist_name: "Queen",
            artist_name_translation_list: [],
            artist_comment: "",
            artist_country: "",
            artist_alias_list: [
              { artist_alias: "\u30af\u30a4\u30fc\u30f3" },
              { artist_alias: "Queen + Adam Lambert" },
            ],
            artist_rating: 83,
            artist_twitter_url: "",
            artist_credits: { artist_list: [] },
            restricted: 0,
            updated_time: "2015-12-16T15:50:53Z",
            begin_date: "0000-00-00",
            end_date: "0000-00-00",
          },
        },
      },
    },
  ]
}

/**
 * Gets a artist.albums.get response
 *
 * @returns [artist_id, number of albums, body]
 */
export function getArtistAlbumsMock(): any {
  return [
    118,
    2,
    {
      message: {
        header: {
          status_code: 200,
          execute_time: 0.019369840621948,
          available: 202,
        },
        body: {
          album_list: [
            {
              album: {
                album_id: 13761314,
                album_mbid: "7c9f3145-8a79-3bf8-97b6-22e8b3cd5f19",
                album_name: "Jazz",
                album_rating: 100,
                album_release_date: "1978-01-01",
                artist_id: 118,
                artist_name: "Queen",
                primary_genres: {
                  music_genre_list: [
                    {
                      music_genre: {
                        music_genre_id: 21,
                        music_genre_parent_id: 34,
                        music_genre_name: "Rock",
                        music_genre_name_extended: "Rock",
                        music_genre_vanity: "Rock",
                      },
                    },
                  ],
                },
                album_pline:
                  "1978 Raincloud Productions Ltd. under exclusive licence to Universal International Music BV",
                album_copyright:
                  "1978 Raincloud Productions Ltd. under exclusive licence to Universal International Music BV",
                album_label: "Universal Music,Virgin EMI",
                restricted: 0,
                updated_time: "2019-07-02T08:30:18Z",
              },
            },
            {
              album: {
                album_id: 15605082,
                album_mbid: "9189ff0c-20bc-416e-a25a-93920c2a9211",
                album_name: "Jazz",
                album_rating: 100,
                album_release_date: "1994-10-03",
                artist_id: 118,
                artist_name: "Queen",
                primary_genres: {
                  music_genre_list: [
                    {
                      music_genre: {
                        music_genre_id: 21,
                        music_genre_parent_id: 34,
                        music_genre_name: "Rock",
                        music_genre_name_extended: "Rock",
                        music_genre_vanity: "Rock",
                      },
                    },
                  ],
                },
                album_pline:
                  "This Compilation \u2117 1991 Hollywood Records, Inc.",
                album_copyright: "1991 Hollywood Records, Inc.",
                album_label: "Hollywood Records",
                restricted: 0,
                updated_time: "2019-02-14T00:16:49Z",
              },
            },
          ],
        },
      },
    },
  ]
}

export function getAlbumTracksMock(): any {
  return [
    13761314,
    2,
    {
      message: {
        header: {
          status_code: 200,
          execute_time: 0.024422168731689,
          available: 12,
        },
        body: {
          track_list: [
            {
              track: {
                track_id: 30109766,
                track_name: "Mustapha",
                track_name_translation_list: [
                  {
                    track_name_translation: {
                      language: "JA",
                      translation: "\u30e0\u30b9\u30bf\u30fc\u30d5\u30a1",
                    },
                  },
                ],
                track_rating: 29,
                commontrack_id: 53468,
                instrumental: 0,
                explicit: 0,
                has_lyrics: 1,
                has_subtitles: 1,
                has_richsync: 1,
                num_favourite: 4,
                album_id: 13761314,
                album_name: "Jazz",
                artist_id: 118,
                artist_name: "Queen",
                track_share_url:
                  "https://www.musixmatch.com/lyrics/Queen/Mustapha-1980-12-08-London-England?utm_source=application&utm_campaign=api&utm_medium=RedBee%3A1409621005559",
                track_edit_url:
                  "https://www.musixmatch.com/lyrics/Queen/Mustapha-1980-12-08-London-England/edit?utm_source=application&utm_campaign=api&utm_medium=RedBee%3A1409621005559",
                restricted: 0,
                updated_time: "2011-06-10T15:01:41Z",
                primary_genres: {
                  music_genre_list: [
                    {
                      music_genre: {
                        music_genre_id: 21,
                        music_genre_parent_id: 34,
                        music_genre_name: "Rock",
                        music_genre_name_extended: "Rock",
                        music_genre_vanity: "Rock",
                      },
                    },
                  ],
                },
              },
            },
            {
              track: {
                track_id: 30109770,
                track_name: "Fat Bottomed Girls",
                track_name_translation_list: [
                  {
                    track_name_translation: {
                      language: "JA",
                      translation:
                        "\u30d5\u30a1\u30c3\u30c8\u30dc\u30c8\u30e0\u30c9\u30ac\u30fc\u30eb\u30ba",
                    },
                  },
                ],
                track_rating: 28,
                commontrack_id: 14658377,
                instrumental: 0,
                explicit: 0,
                has_lyrics: 1,
                has_subtitles: 1,
                has_richsync: 1,
                num_favourite: 32,
                album_id: 13761314,
                album_name: "Jazz",
                artist_id: 118,
                artist_name: "Queen",
                track_share_url:
                  "https://www.musixmatch.com/lyrics/Queen/Fat-Bottomed-Girls-Live?utm_source=application&utm_campaign=api&utm_medium=RedBee%3A1409621005559",
                track_edit_url:
                  "https://www.musixmatch.com/lyrics/Queen/Fat-Bottomed-Girls-Live/edit?utm_source=application&utm_campaign=api&utm_medium=RedBee%3A1409621005559",
                restricted: 0,
                updated_time: "2020-09-04T12:36:49Z",
                primary_genres: {
                  music_genre_list: [
                    {
                      music_genre: {
                        music_genre_id: 21,
                        music_genre_parent_id: 34,
                        music_genre_name: "Rock",
                        music_genre_name_extended: "Rock",
                        music_genre_vanity: "Rock",
                      },
                    },
                  ],
                },
              },
            },
          ],
        },
      },
    },
  ]
}

export function getTrackChartMock(): any {
  return [
    3,
    {
      message: {
        header: { status_code: 200, execute_time: 0.017720937728882 },
        body: {
          track_list: [
            {
              track: {
                track_id: 201234497,
                track_name: "WAP (feat. Megan Thee Stallion)",
                track_name_translation_list: [],
                track_rating: 99,
                commontrack_id: 114611205,
                instrumental: 0,
                explicit: 1,
                has_lyrics: 1,
                has_subtitles: 1,
                has_richsync: 1,
                num_favourite: 1431,
                album_id: 39576526,
                album_name: "WAP (feat. Megan Thee Stallion)",
                artist_id: 46196205,
                artist_name: "Cardi B feat. Megan Thee Stallion",
                track_share_url:
                  "https://www.musixmatch.com/lyrics/Cardi-B-Megan-Thee-Stallion/WAP-Megan-Thee-Stallion?utm_source=application&utm_campaign=api&utm_medium=RedBee%3A1409621005559",
                track_edit_url:
                  "https://www.musixmatch.com/lyrics/Cardi-B-Megan-Thee-Stallion/WAP-Megan-Thee-Stallion/edit?utm_source=application&utm_campaign=api&utm_medium=RedBee%3A1409621005559",
                restricted: 0,
                updated_time: "2020-10-10T10:41:53Z",
                primary_genres: {
                  music_genre_list: [
                    {
                      music_genre: {
                        music_genre_id: 34,
                        music_genre_parent_id: 0,
                        music_genre_name: "Music",
                        music_genre_name_extended: "Music",
                        music_genre_vanity: "Music",
                      },
                    },
                  ],
                },
              },
            },
            {
              track: {
                track_id: 201621042,
                track_name: "Dynamite",
                track_name_translation_list: [],
                track_rating: 99,
                commontrack_id: 114947355,
                instrumental: 0,
                explicit: 0,
                has_lyrics: 1,
                has_subtitles: 1,
                has_richsync: 1,
                num_favourite: 4706,
                album_id: 39721115,
                album_name: "Dynamite - Single",
                artist_id: 24410130,
                artist_name: "BTS",
                track_share_url:
                  "https://www.musixmatch.com/lyrics/BTS/Dynamite?utm_source=application&utm_campaign=api&utm_medium=RedBee%3A1409621005559",
                track_edit_url:
                  "https://www.musixmatch.com/lyrics/BTS/Dynamite/edit?utm_source=application&utm_campaign=api&utm_medium=RedBee%3A1409621005559",
                restricted: 0,
                updated_time: "2021-01-15T16:40:48Z",
                primary_genres: {
                  music_genre_list: [
                    {
                      music_genre: {
                        music_genre_id: 14,
                        music_genre_parent_id: 34,
                        music_genre_name: "Pop",
                        music_genre_name_extended: "Pop",
                        music_genre_vanity: "Pop",
                      },
                    },
                  ],
                },
              },
            },
            {
              track: {
                track_id: 200360817,
                track_name: "Mood (feat. iann dior)",
                track_name_translation_list: [],
                track_rating: 99,
                commontrack_id: 113838056,
                instrumental: 0,
                explicit: 1,
                has_lyrics: 1,
                has_subtitles: 1,
                has_richsync: 1,
                num_favourite: 519,
                album_id: 39278869,
                album_name: "Mood (feat. iann dior) - Single",
                artist_id: 46038964,
                artist_name: "24kGoldn feat. iann dior",
                track_share_url:
                  "https://www.musixmatch.com/lyrics/24kGoldn-iann-dior/Mood-Iann-Dior?utm_source=application&utm_campaign=api&utm_medium=RedBee%3A1409621005559",
                track_edit_url:
                  "https://www.musixmatch.com/lyrics/24kGoldn-iann-dior/Mood-Iann-Dior/edit?utm_source=application&utm_campaign=api&utm_medium=RedBee%3A1409621005559",
                restricted: 0,
                updated_time: "2021-01-21T11:33:33Z",
                primary_genres: { music_genre_list: [] },
              },
            },
          ],
        },
      },
    },
  ]
}
