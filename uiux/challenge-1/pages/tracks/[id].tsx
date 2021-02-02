import React from "react"
import { GetServerSideProps } from "next"
import PageLayout from "../../app/components/layout/PageLayout"
import trackService from "../../app/services/trackService"
import { TrackLyrics, Track } from "../../app/models"
import Lyrics from "../../app/components/Lyrics"

interface TrackPageProps {
  track: Track
  trackLyrics: TrackLyrics
}

export default function TrackPage({
  track,
  trackLyrics,
}: TrackPageProps): JSX.Element {
  const pageTitle = `${track.name} lyrics - Sing It with Me!`
  return (
    <>
      <PageLayout backUrl={`/artists/${track.artistId}`} title={pageTitle}>
        <Lyrics track={track} trackLyrics={trackLyrics} />
      </PageLayout>
      <script type="text/javascript" src={trackLyrics.trackingScript} />
      <img src={trackLyrics.trakingPixel} className="test-mxm-pixel" />
    </>
  )
}

export const getServerSideProps: GetServerSideProps = async ({ params }) => {
  const trackId = parseInt(params.id.toString())
  const [track, trackLyrics] = await Promise.all([
    trackService.getTrack(trackId),
    trackService.getTrackLyrics(trackId),
  ])

  return {
    props: {
      track,
      trackLyrics,
    },
  }
}
