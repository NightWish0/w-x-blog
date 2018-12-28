layui.use(['form','element','upload','layedit','table','layer'],function () {
    var element=layui.element;
    var upload = layui.upload;
    var layedit = layui.layedit;
    var table= layui.table;
    var layer= layui.layer;
    var $=layui.jquery;
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
       if(layEvent === 'resume'){
         $.ajax({
           type:'post',
             url:'/admin/topics/recycle/resume/'+id,
             success:function (result) {
               if (result.status){
                 obj.del();
               }else{
                 layer.msg("还原失败", {
                   time: 3000,
                 });
               }
             }
         });
       }
       if(layEvent === 'destroy'){
         $.ajax({
           type:'delete',
           url:'/admin/topics/recycle/destroy/'+id,
           success:function (result) {
             if (result.status){
               obj.del();
             }else{
               layer.msg("彻底删除失败", {
                 time: 3000,
               });
             }
           }
         });
       }
    });

    //选中、全部删除
    var operate = {
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
        ,allDel: function(){
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
        },
        selectedResume: function(){ //验证是否全选
            var checkStatus = table.checkStatus('topicTable')
                ,checkData = checkStatus.data; //得到选中的数据
            if(checkData.length === 0){
                return layer.msg('请选择需要还原的文章');
            }
            var ids=[];
            $.each(checkData,function (index,value) {
                ids[index]=parseInt(value.id);
            })
            layer.confirm('确定还原吗？', function(index) {
                $.ajax({
                    type:'post',
                    url:'/admin/topics/recycle/resumeSelected',
                    dateType:"json",
                    contentType : 'application/json',
                    data:JSON.stringify(ids),
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
        },
        allResume: function(){ //验证是否全选
            layer.confirm('确定全部还原吗？', function(index) {
                $.ajax({
                    type:'post',
                    url:'/admin/topics/recycle/resumeAll',
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
        },
        selectedDes: function(){
            var checkStatus = table.checkStatus('topicTable')
                ,checkData = checkStatus.data; //得到选中的数据
            if(checkData.length === 0){
                return layer.msg('请选择需要彻底删除的文章');
            }
            var ids=[];
            $.each(checkData,function (index,value) {
                ids[index]=parseInt(value.id);
            })
            layer.confirm('确定切底删除吗？', function(index) {
                $.ajax({
                    type:'delete',
                    url:'/admin/topics/recycle/destroySelected',
                    dateType:"json",
                    contentType : 'application/json',
                    data:JSON.stringify(ids),
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
        },
         allDes: function(othis){
            layer.confirm('确定全部切底删除吗？', function(index) {
                $.ajax({
                    type:'delete',
                    url:'/admin/topics/recycle/destroyAll',
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
    $('.layui-btn-container .topic-del').on('click', function(){
        var type = $(this).data('type');
        operate[type] ? operate[type].call(this) : '';
    });

    /*标签管理*/
    $('.add-label').on('click',function () {
       layer.open({
           id:'addLabel',
           title:'新增标签',
           content:'<label class="layui-form-label category-name-label">名称</label>'+
                       '<div class="layui-input-block category-name-box">'+
                         '<input type="text" id="labelName" autocomplete="off" required class="layui-input"/>'+
                    '</div>',
           resize:false,
           area: ['300px', '180px'],
           yes: function(index, layero){
               //do something
               var name=$('#labelName').val();
               $.post('/admin/topics/label',{name:name},function (result) {
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
    table.on('tool(labelTable)', function(obj){
        var id = obj.data.id;
        var layEvent = obj.event;
        if(layEvent === 'del'){
            layer.confirm('确定删除吗？', function(index){
                $.ajax({
                    type:'delete',
                    url:'/admin/topics/label/'+id,
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
            });
        }
        if(layEvent === 'edit'){
            $(obj.tr).find('td[data-field="name"] .layui-table-cell').click();
        }
    });
    var initLabelNameValue='';
    table.on('row(labelTable)', function(obj){
        initLabelNameValue=obj.data.name;
    });
    table.on('edit(labelTable)', function(obj){
        var id = obj.data.id;
        var value = obj.value;
        $.ajax({
            type:'post',
            url:'/admin/topics/label/'+id,
            data:{name:value},
            success:function (result) {
                if (!result.status){
                    layer.msg("编辑失败", {
                        time: 3000,
                    });
                    obj.value=initLabelNameValue;
                }
            }
        });
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
    table.on('tool(categoryTable)', function(obj){
        var id = obj.data.id;
        var layEvent = obj.event;
        if(layEvent === 'del'){
            layer.confirm('确定删除吗？', function(index){
                $.ajax({
                    type:'delete',
                    url:'/admin/topics/category/'+id,
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
            });
        }
        if(layEvent === 'edit'){
            $(obj.tr).find('td[data-field="name"] .layui-table-cell').click();
        }
    });
    var initNameValue='';
    table.on('row(categoryTable)', function(obj){
        initNameValue=obj.data.name;
    });
    table.on('edit(categoryTable)', function(obj){
        var id = obj.data.id;
        var value = obj.value;
        $.ajax({
            type:'post',
            url:'/admin/topics/category/'+id,
            data:{name:value},
            success:function (result) {
                if (!result.status){
                    layer.msg("编辑失败", {
                        time: 3000,
                    });
                    obj.value=initNameValue;
                }
            }
        });
    });

    /*所有评论*/
    //选中、全部删除
    var active = {
        selectedDel: function(){
            var checkStatus = table.checkStatus('commentTable')
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
                    url:'/admin/comments/deleteSelected',
                    dateType:"json",
                    contentType : 'application/json',
                    data:JSON.stringify(ids),
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
        //全部删除
        ,allDel: function(othis){
            layer.confirm('确定全部删除吗？', function(index) {
                $.ajax({
                    type:'delete',
                    url:'/admin/comments/deleteAll',
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
    $('.layui-btn.comment-del').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var flag=true;
    table.on('tool(commentTable)', function(obj){
        var id = obj.data.id;
        var layEvent = obj.event;
        flag=false;
        if(layEvent === 'del'){
            layer.confirm('确定删除吗？', function(index){
                $.ajax({
                    type:'delete',
                    url:'/admin/comments/'+id,
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
            });
        }
        if(layEvent === 'reply'){
            layer.open({
                title:'评论回复',
                area: ['500px', '330px'],
                resize:false,
                content:'<div class="layui-card" style="box-shadow: 0px 0px 0px 1px rgba(0,0,0,.05);">' +
                            '<div class="layui-card-header" style="padding-top: 0px;">'+
                                '<label style="color: #29821d;font-weight: normal;">'+obj.data.name+'</label>'+
                                '<label style="margin-left: 26px;color: #29821d;font-weight: normal;">'+obj.data.email+'</label>'+
                            '</div>'+
                            '<div class="layui-card-body">'+obj.data.content+'</div>' +
                        '</div>'+
                        '<div>' +
                            '<textarea class="reply-content" placeholder="快点回复他吧" style="width:100%;height:80px;padding:5px 0 0 5px;resize:none;"></textarea>'+
                        '</div>',
                move: false,
                yes:function(index, layero) {
                    var content=$('.reply-content').val();
                    if (content=='' || $.trim(content)==''){
                        $('.reply-content').focus();
                        return;
                    }else{
                        $.ajax({
                            type:'post',
                            url:'/admin/comments/'+obj.data.id+'/reply',
                            data:{content:content},
                            success:function (result) {
                                if (result.status){
                                    layer.close(index);
                                    location.reload();
                                }else{
                                    layer.msg("回复失败", {
                                        time: 3000,
                                    });
                                }
                            }
                        })
                    }
                }
            })
        }
        if(layEvent === 'edit'){
            layer.open({
                title:'编辑评论',
                area: ['350px', '220px'],
                resize:false,
                content:'<div>' +
                            '<textarea class="edit-content" style="width:100%;height:80px;padding:5px 0 0 5px;resize:none;">'+obj.data.content+'</textarea>'+
                        '</div>',
                move: false,
                yes:function(index, layero) {
                    var content=$('.edit-content').val();
                    if (content=='' || $.trim(content)==''){
                        $('.edit-content').focus();
                        return;
                    }else{
                        $.ajax({
                            type:'post',
                            url:'/admin/comments/'+obj.data.id,
                            data:{content:content},
                            success:function (result) {
                                if (result.status){
                                    $(obj.tr).find('td[data-field="content"] .layui-table-cell').text(content);
                                    layer.close(index);
                                }else{
                                    layer.msg("修改失败", {
                                        time: 3000,
                                    });
                                }
                            }
                        })
                    }
                }
            })
        }
    });
    table.on('row(commentTable)', function(obj){
        var data = obj.data;
        var id=data.topicId;
        var title=data.topicTitle;
        if (flag){
            layer.open({
                type:2,
                title:title,
                content:'/admin/comments/'+id+'/topic',
                // offset: ['60px', '200px'],
                area: ['100%', '100%'],
                move: false
            })
        }
        flag=true;
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
                if (result.status){
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

