layui.use(['form','element','layer'],function () {
    var element=layui.element;
    $=layui.jquery;
    // element.on('nav(menu-nav)',function (elem) {
    //     var dataUrl=$(elem).data('url');
    //     if (dataUrl!=null){
    //         $('#content_page').attr('src',dataUrl);
    //     }
    // });
    $(".settting-page .layui-btn").on('click',function () {
        var oldPwd=$(".settting-page input[name='oldPwd']").val();
        var newPwd=$(".settting-page input[name='newPwd']").val();
        var confirmPwd=$(".settting-page input[name='confirmPwd']").val();
        if (newPwd!=confirmPwd){
            $('.pwd-error').val("两次密码输入不一致");
            return;
        }
        $.post('/admin/update_pwd',{'oldPwd':oldPwd,'newPwd':newPwd,'confirmPwd':confirmPwd}
                ,function (data) {
            if (data.state){
                layer.msg('修改密码成功', {
                    time: 30000, //20s后自动关闭
                });
            }else{

            }
        })
    })
})