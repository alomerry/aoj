export default ({
    logout: state => {
        state.token = null;
        state.user = null;
        state.admin = null;
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        localStorage.removeItem("admin");
        localStorage.clear();
    },
    login(state, data) {
        state.user = data.user;
        state.admin = data.admin;
        // console.log(JSON.stringify(data.user));
        // console.log(typeof JSON.stringify(data.user));
        localStorage.setItem("user", JSON.stringify(data.user));
        localStorage.setItem("admin", data.admin);
        localStorage.setItem("level", data.level);
    },
    updateActiveName(state, activeName) {
        state.menu_active_name = activeName;
    },
    changeToken(state, this_token) {
        state.token = this_token;
        localStorage.setItem("token", this_token);
    },
});
