<template>
  <div id="app">
    <notifications group="foo" />
    <Home></Home>
  </div>
</template>

<script>
import Home from './views/Home'

export default {
  name: 'App',
  metaInfo:{
    title: 'Public key infrastructure control panel',
  },
  components: {
    Home
  }
  
}
</script>

<style>
#app {
  font-family: 'IBM Plex Sans', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
