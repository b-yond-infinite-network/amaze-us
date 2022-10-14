import {
  beforeEach,
  describe,
  it,
  expect,
} from 'vitest';
import { render } from '@testing-library/react';
import { LyricsView } from './LyricsView';

describe('LyricsView', () => {
  const lyricsFixture = {
    name: 'Never gonna give you up',
    artist: 'Rick Astley',
    lyrics: `Never gonna give you up
Never gonna let you down
Never gonna run around and desert you
Never gonna make you cry
Never gonna say goodbye
Never gonna tell a lie and hurt you`,
    copyright: '© All Boys Music Ltd., Sids Songs Ltd., Mike Stock Publishing Limited',
    trackingUrl: 'https://tracking.musixmatch.com/t1.0/m_js/e_1/sn_0/l_28192505/su_0/rs_0/tr_3vUCACzH8U6y2_8iUG-f0Ab3jv9zPYXmiQEVZttUflzjUft4qV4MTGy4GCqEz4MsOYi21W49xCU659uWzmdkruIi5H72dPkBt1zt5gC6MLz0KNxNELA4Z0yOo2WQBOeRKfdYHL5u__p6Z_XQDzpr4W8ztDTFndVVh9FBRqL7o3a7zDD8RacxWSNC6KvZ_i83dB43VtPMZoEyyqUSM5RjvtwUb_h4c7Lw6jnBIf0LH0Ckwhi-roSO46mpUp-rcmMTduwIiljkhgzs0wjXx6Oj-ZTxwdmbL9ml40ycqkqpIeGLaT9Ah6bj6FwCSUy4HdDPOuCYSu0q_H-4o99XJ043NkMBP9NdiQhJQTd3d2SCIVGB1D9PHrQYhwJ63qyxc2Y0dMcQVYFH1Sm2-TDEM0e_Cwo5-Ip_iedIRmO6apf7Y45o1Wc9_et-YsJfEHbQZFlGrvWKQYg6qJwmNpbxdNqC/',
  };

  beforeEach(ctx => {
    ctx.component = render(<LyricsView
      trackName={lyricsFixture.name}
      artistName={lyricsFixture.artist}
      lyrics={lyricsFixture.lyrics}
      copyright={lyricsFixture.copyright}
      trackingUrl={lyricsFixture.trackingUrl}
    />);
  });

  it('should display track name', ({ component }) => {
    expect(component.queryByText(lyricsFixture.name))
      .toBeInTheDocument();
  });

  it('should display track artist', ({ component }) => {
    expect(component.queryByText(lyricsFixture.artist))
      .toBeInTheDocument();
  });

  it('should display track lyrics', ({ component }) => {
    expect(component.queryByText(
      content => content
        .replaceAll('\n', ' ')
        .includes(lyricsFixture.lyrics.replaceAll('\n', ' ')),
    ))
      .toBeInTheDocument();
  });

  it('should display copyright message', ({ component }) => {
    expect(component.queryByText(lyricsFixture.copyright))
      .toBeInTheDocument();
  });

  it('should render tracking url', ({ component }) => {
    expect(component.container.querySelector(`script[src="${lyricsFixture.trackingUrl}"]`))
      .toBeInTheDocument();
  });
});
