/**
 * (各模块)刷新验证码
 * @param obj img对象
 */
function getVerify(obj) {
    obj.src = "/getVerify?" + Math.random();
    console.log("verifyCode update...");
}


/**
 * (后台用户信息列表模块)点击后跳转至用户信息修改模块
 * @param id 修改的用户Id
 */
function admin_go_to_editUser(id) {
    console.log(id);
    window.location.href = ("/admin_user_edit" + "?userId=" + id);
}

/*$.message({
    message: '失败提示',
    type: 'success',
    time: '20000',
    autoClose: true,
    showClose: false
});*/

/**
 * (后台用户列表模块)点击后执行删除用户操作
 * @param user_id 待删除的用户Id
 */
function doUserDel(user_id) {
    console.log("666");
    $("#somedialog" + user_id).attr("class", "dialog dialog--close");
    $.ajax({
        url: "/admin_user_doDel",
        type: "post",
        data: '{"user_id":"' + user_id + '"}',
        contentType: "application/json",
        success: function (result) {
            console.log(result);
            var types = "";
            if (result.msgType === 0) {
                types = "error";
                console.log("删除失败...");
            } else if (result.msgType === 1) {
                types = 'success';
                console.log("删除成功...");
                $("#tr_del_need_" + user_id).remove();
            }
            $.message({
                message: result.msg,
                type: types,
                time: '1000',
                autoClose: true,
                showClose: false
            });
            setTimeout(function () {
                // window.location.reload();
            }, 1000);
        }
    })
}

/**
 * 多选
 * @param obj
 */
function selectAll(obj) {
    var i = $(obj);
    if (i.attr("class") === "fa fa-circle-o") {
        console.log("选中所有");
        $(".underSel").each(function () {
            $(this).find(".fa").attr("class", "fa fa-dot-circle-o");
        });
        i.attr("class", "fa fa-dot-circle-o");
    } else {
        console.log("取消选中所有");
        i.attr("class", "fa fa-circle-o");
        $(".underSel").each(function () {
            $(this).find(".fa").attr("class", "fa fa-circle-o");
        });
        i.attr("class", "fa fa-circle-o");
    }
}

//TODO 判断多选
/**
 * 单选
 * @param obj
 */
function selectItem(obj) {
    var father = $("#i_father");
    var i = $(obj);
    if (i.attr("class") !== "fa fa-circle-o") {
        console.log("未选中此checkbox");
        i.attr("class", "fa fa-circle-o");
        if (father.attr("class") === "fa fa-dot-circle-o") {
            father.attr("class", "fa fa-circle-o")
        }
    } else {
        console.log("选中此checkbox");
        i.attr("class", "fa fa-dot-circle-o");
        var flag = true;
        $(".underSel").each(function () {
            if ($(this).find(".fa").attr("class") === "fa fa-circle-o") {
                flag = false;
                return false;
            }
        });
        if (flag) {
            father.attr("class", "fa fa-dot-circle-o");
        }
    }
}

/**
 * 初始化自定义font-awesome样式的checkbox
 * 根据勾选状态初始化data-checked属性
 */
function init_Awesome_checkbox() {
    $(".fa .fa-fw").each(function () {
        var item = $(this);
        if (item.attr("class").indexOf("check-") !== -1) {
            item.data("checked", "true");
        } else {
            item.data("checked", "false");
        }
    })
}

/**
 * font-awesome样式的checkbox点击时间
 * 修改图片以及data-checked属性
 * @param obj
 */
function awesome_checkbox_click(obj) {
    var item = $(obj);
    console.log("class:" + item.attr("class") + ",checked:" + item.data("checked"));
    if (item.attr("class").indexOf("check-") !== -1) {
        item.attr("class", "fa fa-square-o fa-fw");
        item.data("checked", "false");
    } else {
        item.attr("class", "fa fa-check-square-o fa-fw");
        item.data("checked", "true");
    }
}