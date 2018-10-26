layui.use(['form','element','upload','layedit','table','layer'],function () {
    var element=layui.element;
    var upload = layui.upload;
    var layedit = layui.layedit;
    var table= layui.table;
    var layer= layui.layer;
    $=layui.jquery;
    /*文章管理*/
    table.on('tool(topicTable)', function(obj){
        var id = obj.data.id;
        var layEvent = obj.event;
       if(layEvent === 'del'){
            layer.confirm('确定删除吗？', function(index){
                //向服务端发送删除指令
                $.ajax({
                    type:'delete',
                    url:'/admin/topics/'+id,
                    success:function (result) {
                        if (result.status){
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.close(index);
                        }else{
                            layer.close(index);
                            layer.msg("删除失败", {
                                time: 3000,
                            });
                        }
                    }
                });
                // $.post('/admin/topics/'+id,function (result) {
                //     if (result.status){
                //         obj.del(); //删除对应行（tr）的DOM结构
                //         layer.close(index);
                //     }else{
                //         layer.close(index);
                //         layer.msg("删除失败", {
                //             time: 3000,
                //         });
                //     }
                // });
            });
        }
    });

    //选中、全部删除
    var active = {
        selectedDel: function(){
            var checkStatus = table.checkStatus('topicTable')
                ,checkData = checkStatus.data; //得到选中的数据
            if(checkData.length === 0){
                return layer.msg('请选择数据');
            }
            var ids=[];
            $.each(checkData,function (index,value) {
                ids[index]=parseInt(value.id);
            })
            layer.confirm('确定删除吗？', function(index) {
                $.ajax({
                    type:'delete',
                    url:'/admin/topics/deleteSelected',
                    dateType:"json",
                    contentType : 'application/json',
                    data:JSON.stringify(ids),
                    success:function (result) {
                        if (result.status){
                            // var tableContainer = $('div[lay-filter="LAY-table-1"]');
                            // tableContainer.find('input[name="layTableCheckbox"]:checked').each(function(){
                            //     var trDel = $(this).parents('tr');
                            //     $(trDel).del();
                            // })
                            // layer.msg('已删除');
                            location.reload();
                        }else{
                            layer.msg("删除失败", {
                                time: 3000,
                            });
                        }
                    }
                });
            });
        }
        //全部删除
        ,allDel: function(othis){
            layer.confirm('确定全部删除吗？', function(index) {
                $.ajax({
                    type:'delete',
                    url:'/admin/topics/deleteAll',
                    success:function (result) {
                        if (result.status){
                            location.reload();
                        }else{
                            layer.msg("删除失败", {
                                time: 3000,
                            });
                        }
                    }
                });
            });
        }
    };
    $('.layui-btn.topic-del').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    /*我的分类*/
    $('.add-category').on('click',function () {
       layer.open({
           id:'addCategory',
           title:'新增分类',
           content:'<label class="layui-form-label category-name-label">名称</label>'+
                       '<div class="layui-input-block category-name-box">'+
                         '<input type="text" id="categoryName" autocomplete="off" required class="layui-input"/>'+
                    '</div>',
           resize:false,
           area: ['300px', '180px'],
           yes: function(index, layero){
               //do something
               var name=$('#categoryName').val();
               $.post('/admin/topics/category',{name:name},function (result) {
                  if (result.status) {
                      location.reload();
                  }else{
                      layer.msg("新增失败", {
                          time: 3000,
                      });
                  }
               });
           }
       });
    });

    //创建一个编辑器
    var editIndex = layedit.build('LAY_demo_editor');
    $(".new-topics .save-draft").on('click',function () {
        $('#status').val(0);
        $('.new-topics #topicForm').submit();
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
});

