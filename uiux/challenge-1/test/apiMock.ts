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
