import React, { PureComponent } from 'react'
import styled, { keyframes } from 'styled-components'


const WrappedBody = styled.div`
  margin-top: 30px;
`

const Wrapper = styled.div.attrs(({ visible }) => ({
  style: {
    pointerEvents: visible ? 'visible' : 'none',
    opacity: visible ? 1 : 0
  }
}))`
  position: fixed;
  display: flex;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  background-color: ${({ theme: { color } }) => color.black60};
  transition: opacity .2s;
  z-index: 100;
  pointer-events:none

`

const pulse = keyframes`
  0% {
    transform: scale(1);
  }

  50% {
    transform: scale(.5);
  }

  100% {
    transform: scale(1);
  }
`

const PulsingBall = styled.div`
  width: 112px;
  height: 112px;
  border-radius: 100%;
  margin-top: 30px;
  background: ${({ theme: { color } }) => color.pink};
  animation: ${pulse} 1s ${({ theme: { easings } }) => easings.easeInOutSine} infinite;
`

export default class extends PureComponent {
  interval = 0
  state = {
    showSlowLoading: false
  }

  onTimeout = () => {
    this.setState({ showSlowLoading: true })
  }

  componentWillReceiveProps (nextProps) {
    if (nextProps.visible) {
      this.interval = setTimeout(this.onTimeout, 20000)
    }

    if (nextProps.visible === false) {
      clearTimeout(this.interval)
    }
  }

  render () {
    const { visible } = this.props
    const { showSlowLoading } = this.state
    return (
      <Wrapper visible={visible}>
        <PulsingBall />
        {showSlowLoading && <WrappedBody white>It's taking a while to load.<br /><br />Please check your internet connection and/or restart your browser and try again.</WrappedBody>}
      </Wrapper>
    )
  }
}
