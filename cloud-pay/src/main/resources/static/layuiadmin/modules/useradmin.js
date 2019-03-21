/** layuiAdmin.std-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form"], function (e) {
    var t = layui.$, i = layui.table;
    layui.form;
    i.render({
        elem: "#LAY-user-manage",
        url: "",
        cols: [[{type: "checkbox", fixed: "left"}, {field: "id", width: 100, title: "ID", sort: !0}, {
            field: "username",
            title: "用户名",
            minWidth: 100
        }, {field: "avatar", title: "头像", width: 100, templet: "#imgTpl"}, {
            field: "phone",
            title: "手机"
        }, {field: "email", title: "邮箱"}, {field: "sex", width: 80, title: "性别"}, {
            field: "ip",
            title: "IP"
        }, {field: "jointime", title: "加入时间", sort: !0}, {
            title: "操作",
            width: 150,
            align: "center",
            fixed: "right",
            toolbar: "#table-useradmin-webuser"
        }]],
        page: !0,
        limit: 30,
        height: "full-220",
        text: "对不起，加载出现异常！"
    }), e("useradmin", {})
});