layui.use(['form','element','upload','layedit','table','layer'],function () {
    var element=layui.element;
    var upload = layui.upload;
    var layedit = layui.layedit;
    $=layui.jquery;
    // element.on('nav(menu-nav)',function (elem) {
    //     var dataUrl=$(elem).data('url');
    //     if (dataUrl!=null){
    //         $('#content_page').attr('src',dataUrl);
    //     }
    // });
    /*文章管理*/
    //创建一个编辑器
    var editIndex = layedit.build('LAY_demo_editor');
    $(function () {
        editormd("editormd", {
            width: "100%",
            height: 640,
            path : '/statics/editormd/lib/',
            codeFold : true,
            syncScrolling : "single",
            placeholder : "请输入内容",
            saveHTMLToTextarea : true,    // 保存 HTML 到 Textarea
            searchReplace : true,
            htmlDecode : "style,script,iframe|on*",  // 开启 HTML 标签解析，为了安全性，默认不开启
            toolbarIcons : function() { //自定义工具栏
                return [
                    "undo", "redo", "|",
                    "bold", "del", "italic", "quote", "ucwords", "uppercase", "lowercase", "|",
                    "link", "reference-link", "image", "preformatted-text", "code-block", "table", "datetime", "emoji", "html-entities", "|",
                    "watch", "preview", "fullscreen", "clear", "search", "|",
                    "help"
                    ]
            },
            emoji : true,
            taskList : true,
            tocm : true,         // Using [TOCM]
            tex : true,                   // 开启科学公式TeX语言支持，默认关闭
            flowChart : true,             // 开启流程图支持，默认关闭
            sequenceDiagram : true,       // 开启时序/序列图支持，默认关闭,
            imageUpload : true,
            imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL : "./php/upload.php",
            onload : function() { //上传成功之后的回调
                console.log('onload', this);
            }
        });
    });

    /*设置*/
    $(".setting-page .layui-btn").on('click',function () {
        var oldPwd=$(".setting-page input[name='oldPwd']").val();
        var newPwd=$(".setting-page input[name='newPwd']").val();
        var confirmPwd=$(".setting-page input[name='confirmPwd']").val();
        if ($.trim(oldPwd)=="" || $.trim(newPwd)=="" || $.trim(confirmPwd)==""){
            return;
        }
        if(newPwd.length<6 || newPwd.length>12){
            $(".setting-page .pwd-tip").attr("style","color:red!important");
            $(".setting-page input[name='newPwd']").focus();
            return;
        }else{
            $(".setting-page .pwd-tip").attr("style","color: #999!important;");
        }
        if (newPwd!=confirmPwd){
            $('.setting-page .pwd-error').text("两次密码输入不一致");
            $(".setting-page input[name='confirmPwd']").focus();
            return;
        }
        $.ajax({
            type:'post',
            url:'/admin/update_pwd',
            data:{'oldPwd':oldPwd,'newPwd':newPwd,'confirmPwd':confirmPwd},
            success:function (result) {
                if (result.state){
                    layer.msg('修改密码成功', {
                        time: 3000,
                    });
                    $(".setting-page input[name='oldPwd']").val("");
                    $(".setting-page input[name='newPwd']").val("");
                    $(".setting-page input[name='confirmPwd']").val("");
                }else{
                    layer.msg(result.data, {
                        time: 3000,
                    });
                }
            }
        });
    })

    /*基本资料*/
    var src=$('#avatar').attr('src');
    var uploadInst = upload.render({
        elem: '#uploadAvatar' //绑定元素
        // ,url: '/avatar/' //上传接口
        ,accept:'images'
        ,acceptMime:'image/*'
        ,size: 5120
        ,auto: false
        ,choose: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#avatar').attr('src', result); //图片链接（base64）
            });
            $('#cancelAvatar').attr("style","display:unset");
        }
    });
    $("#cancelAvatar").on('click',function () {
        $('#avatar').attr('src', src);
        $(this).attr("style","display:none");
        var file=$('.upload-box .layui-upload-file');
        file.val("");
        file.outerHTML=file.outerHTML;
    });
})