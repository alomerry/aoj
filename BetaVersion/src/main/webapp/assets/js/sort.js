function adminContestListOrderBy(obj) {
    event.stopPropagation();
    var sort;
    console.log(typeof ($(obj).attr("class")) + ":" + typeof ("caret-low"));
    console.log($(obj).attr("class"));
    if ($(obj).attr("class") === 'caret-low') {
        $(obj).children("i").attr("class", "fa fa-caret-up");
        console.log("当前降序，请求升序");
        sort = "asc";
    } else {
        $(obj).children("i").attr("class", "fa fa-caret-down");
        console.log("当前升序，请求降序");
        sort = "desc";
    }
    $(obj).attr("href", "/admin_contest_list?orderByType=" + $(obj).parent().data("type") + "&sort=" + sort);
}

function adminProblemListOrderBy(obj) {
    event.stopPropagation();
    var sort;
    if ($(obj).attr("class") === 'caret-low') {
        $(obj).children("i").attr("class", "fa fa-caret-up");
        console.log("当前降序，请求升序");
        sort = "asc";
    } else {
        $(obj).children("i").attr("class", "fa fa-caret-down");
        console.log("当前升序，请求降序");
        sort = "desc";
    }
    $(obj).attr("href", "/admin_problem_list?orderByType=" + $(obj).parent().data("type") + "&sort=" + sort);
}

function adminContestApplyListOrderBy(obj) {
    event.stopPropagation();
    var sort;
    if ($(obj).attr("class") === 'caret-low') {
        console.log("当前降序，请求升序");
        sort = "asc";
    } else {
        console.log("当前升序，请求降序");
        sort = "desc";
    }
    $(obj).attr("href", "/admin_contest_apply?orderByType=" + $(obj).parent().attr("type") + "&sort=" + sort);
}

function selectAll(obj) {
    $(obj).is(':checked') === true ? $("input[type=checkbox]").prop("checked", true) : $("input[type=checkbox]")
        .prop("checked", false);
}

function selectItem(obj) {
    if ($(obj).is(':checked') === false) {
        if ($("#checkbox_father").is(':checked') == true) {
            $("#checkbox_father").prop("checked", false);
        }
    } else {
        var flag = true;
        $("tbody>tr").find("input[type=checkbox]").each(function (index, element) {
            if ($(element).is(':checked') == false) {
                flag = false;
                return false;
            }
        });
        if (flag) {
            $("#checkbox_father").prop("checked", true);
        }
    }
}

function ProblemListDoDelete(obj) {
    var problem_id = $("#underDelete").html();
    console.log(problem_id);

    $.ajax({
        url: "/admin_problem_dodel",
        data: '{"problem_id":"' + problem_id + '"}',
        type: "post",
        contentType: "application/json",
        success: function (res) {
            var type = res.msgType;
            var data = res.msg;
            if (type == "0") {
                swal({
                    icon: "error",
                    title: "删除失败",
                    text: data,
                    closeOnClickOutside: false,
                    buttons: {
                        text: "OK",
                    }
                }).then(function (value) {
                    console.log(value);
                    if (value == "text") {
                        console.log(666);
                        $("#item").css("display", "none");
                        $("#item").attr("class", "modal fade");
                        $("body").removeAttr("style");
                        $("body").removeAttr("class");
                    }
                })
            } else {
                // swal({
                //     ico: "success",
                //     title: "操作成功",
                // });
                window.location.reload();
            }
        }
    });
}

function ContestListDoDelete(obj) {
    var contest_id = $("#underDelete").html();
    console.log(contest_id);

    $.ajax({
        url: "/admin_contest_dodel",
        data: '{"contest_id":"' + contest_id + '"}',
        type: "post",
        contentType: "application/json",
        success: function (res) {
            var type = res.msgType;
            var data = res.msg;
            if (type == "0") {
                swal({
                    icon: "error",
                    title: "删除失败",
                    text: data,
                    closeOnClickOutside: false,
                    buttons: {
                        text: "OK",
                    }
                }).then(function (value) {
                    console.log(value);
                    if (value == "text") {
                        console.log(666);
                        $("#item").css("display", "none");
                        $("#item").attr("class", "modal fade");
                        $("body").removeAttr("style");
                        $("body").removeAttr("class");
                    }
                })
            } else {
                // swal({
                //     ico: "success",
                //     title: "操作成功",
                // });
                window.location.reload();
            }
        }
    });
}