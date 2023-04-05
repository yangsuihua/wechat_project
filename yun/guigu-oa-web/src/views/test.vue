<template>
  <div>
    <div>账号切换</div>
    <button @click="wjl()" type="default" size="mini">王经理</button>
    <button @click="rsjl()" type="default" size="mini">李人事经理</button>
    <button @click="zzjl()" type="default" size="mini">张总经理</button>
    <button @click="lisi()" type="default" size="mini">李四</button>
    <div>当前token：{{ token }}</div>
  </div>
</template>

<script>
export default {
  name: "Test",

  data() {
    return {
      token: ''
    };
  },

  created(){

  },

  methods: {
    wjl() {
      window.localStorage.setItem('token', '');
      let token = 'eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNLMwNLcws7Aw1VEqLU4t8kxRsjKCMPMSc1OBWsqzcpRqATl4g19AAAAA.pGdCs7gavheRUbbcmLbfp9Q9wb0aGWJchnVVOYqi3FbVzuLMPRaQiHWSdttQBYB30UsLG-oU1umznkzT7YKc9Q'
      window.localStorage.setItem('token', token);
      this.token = window.localStorage.getItem('token')
    },

    rsjl() {
      window.localStorage.setItem('token', '');
      let token = 'eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNLMwNLcwszQ31VEqLU4t8kxRsjKGMPMSc1OBWnKKirNylGoBxSJN5EIAAAA.U3UKqFomZfkIjOQU03OKBlfGZjZ7ZFw64SG70ZpVKrMIsjfl-T-TZAoorCsrkrsAHSPAJglGy1oYwgrI1EoFUg'
      window.localStorage.setItem('token', token);
      this.token = window.localStorage.getItem('token')
    },

    zzjl() {
      window.localStorage.setItem('token', '');
      let token = 'eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNLMwNLcwNzA001EqLU4t8kxRsjKFMPMSc1OBWqqqsnKUagFcrRu1QQAAAA.6-a16HgedOtQXgFlU-dIeGwdQRlPKd8kI7carWKH2uoCpwlgQUzDupGC9WJqhZKdv-rijD9RaOYRpQTtXiNthA'
      window.localStorage.setItem('token', token);
      this.token = window.localStorage.getItem('token')
    },

    lisi() {
      window.localStorage.setItem('token', '');
      let token = 'eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNLMwNLcwNzA101EqLU4t8kxRsjKBMPMSc1OBWnIyizOVagEaWuN4QQAAAA.UTtJC_bPMqW3bx3hemCg5ltJlFbNyvJ_-ambPmhxbIyQTc19aYjqnj4sjKk_1BvCx1uPlR3dJm7zCXMCIUi1MQ'
      window.localStorage.setItem('token', token);
      this.token = window.localStorage.getItem('token')
    }
  }
}
</script>