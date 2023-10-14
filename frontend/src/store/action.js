export default ({
  logout(context) {
    context.commit('logout');
  },
  login(context, data) {
    context.commit('login', data);
    context.commit("changeToken", data.jwt);
    console.log("login success");
  },
  updateActiveName(context, activeName) {
    context.commit('updateActiveName', activeName);
  },
  changeToken(context, token) {
    context.commit("changeToken", token);
  },
});
