<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<style lang="scss">
  @import './styles.scss'
</style>

<style lang="scss">
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;

  a {
    text-decoration:none;
    color:inherit;
  }

  .container {
    padding:0px 15px;
    margin:0 auto;

    @media(min-width:768px) {
      max-width:720px;
    }
    @media(min-width:992px) {
      max-width:960px;
    }
    @media(min-width:1200px) {
      max-width:1140px;
    }
  }

  .text-center {
    text-align:center;
  }

  .block-center {
    display:block;
    margin:0 auto;
  }

  .mt-0 {
    margin-top:0px;
  }

  .mb-0 {
    margin-bottom:0px;
  }

  .dz-button {
    padding:0.5rem 0.75rem;
    border:none;
    cursor:pointer;
    background:#2c3e50;
    color:#ffffff;

    &:hover {
      background:#2c3e65;
    }
  }

  .text-uppercase {
    text-transform:uppercase;
  }

  .text-capitalize {
    text-transform:capitalize;
  }
}

/**
 * ----------------------------------------
 * animation scale-up-ver-top
 * ----------------------------------------
 */
.scale-up-ver-top {
	-webkit-animation: scale-up-ver-top 0.5s cubic-bezier(0.390, 0.575, 0.565, 1.000) both;
	        animation: scale-up-ver-top 0.5s cubic-bezier(0.390, 0.575, 0.565, 1.000) both;
}

@-webkit-keyframes scale-up-ver-top {
  0% {
    -webkit-transform: scaleY(0.4);
            transform: scaleY(0.4);
    -webkit-transform-origin: 100% 0%;
            transform-origin: 100% 0%;
  }
  100% {
    -webkit-transform: scaleY(1);
            transform: scaleY(1);
    -webkit-transform-origin: 100% 0%;
            transform-origin: 100% 0%;
  }
}
@keyframes scale-up-ver-top {
  0% {
    -webkit-transform: scaleY(0.4);
            transform: scaleY(0.4);
    -webkit-transform-origin: 100% 0%;
            transform-origin: 100% 0%;
  }
  100% {
    -webkit-transform: scaleY(1);
            transform: scaleY(1);
    -webkit-transform-origin: 100% 0%;
            transform-origin: 100% 0%;
  }
}

/**
 * ----------------------------------------
 * animation scale-down-ver-top
 * ----------------------------------------
 */
.scale-down-ver-top {
	-webkit-animation: scale-down-ver-top 0.5s cubic-bezier(0.250, 0.460, 0.450, 0.940) both;
	        animation: scale-down-ver-top 0.5s cubic-bezier(0.250, 0.460, 0.450, 0.940) both;
}

@-webkit-keyframes scale-down-ver-top {
  0% {
    -webkit-transform: scaleY(1);
            transform: scaleY(1);
    -webkit-transform-origin: 100% 0%;
            transform-origin: 100% 0%;
  }
  100% {
    -webkit-transform: scaleY(0.3);
            transform: scaleY(0.3);
    -webkit-transform-origin: 100% 0%;
            transform-origin: 100% 0%;
  }
}
@keyframes scale-down-ver-top {
  0% {
    -webkit-transform: scaleY(1);
            transform: scaleY(1);
    -webkit-transform-origin: 100% 0%;
            transform-origin: 100% 0%;
  }
  100% {
    -webkit-transform: scaleY(0.3);
            transform: scaleY(0.3);
    -webkit-transform-origin: 100% 0%;
            transform-origin: 100% 0%;
  }
}
</style>
