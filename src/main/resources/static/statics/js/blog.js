layui.use(['flow','layer'],function () {
    var flow = layui.flow;
    var layer = layui.layer;
    var $ = layui.jquery;
    flow.load({
        elem: '#topicList',
        end: '差不多就行了吧，我不要底线的啊',
        done:function(page,next){
            var lis = [];
            $.get('/blogByPage?currentSize='+page,function (res) {
                layui.each(res.records,function (index,item) {
                    var labelHtml='';
                    $.each(item.labels,function (index,label) {
                        labelHtml+='<span>' +
                            '<i class="fa fa-tag"></i>'+
                            '<a href="">'+label.name+'</a>'+
                            '</span>';
                    })
                    var categoryHtml=''
                    if (item.category.name!=null){
                        categoryHtml='<label>—</label>'+
                                         '<a href="">'+item.category.name+'</a>';
                    }
                    lis.push('<li>' +
                                '<a class="title" href="/blog/'+item.id+'">'+item.title+'</a>'+
                                '<div class="topic-label">'+labelHtml+'</div>'+
                                '<div class="topic-content">'+item.content.replace(/<[^>]+>/g,"")+'</div>'+
                                '<div class="meta">' +
                                    '<a href="">'+item.user.userName+'</a>'+
                                    categoryHtml+
                                    '<div class="meta-info">' +
                                        '<label>'+item.createdAt+'</label>'+
                                        '<label>浏览 '+item.readCount+'</label>'+
                                        '<label>点赞 '+item.likeCount+'</label>'+
                                        '<label>评论 '+item.commentCount+'</label>'+
                                    '</div>'+
                                '</div>'+
                        '</li>');
                });
                next(lis.join(''),page<res.pages);
            })
        }
    });
    var isLikeBtn=true;
    $('.like-btn').on('click',function () {
        var obj=$(this);
        var topicId=obj.attr('data');
        if (isLikeBtn){
            $.ajax({
                type:'post',
                url:'/blog/'+topicId+'/like',
                success:function (result) {
                    if (result.status){
                        obj.html('<i class="fa fa-fw fa-heartbeat"></i>已喜欢');
                        isLikeBtn=false;
                    }
                }
            });
        }
    });
    var name='';
    var email='';
    $('#comment_publish').on('click',function () {
        var content=$('#comment_content').val();
        if ($.trim(content)==''){
            $('#comment_content').focus();
            return;
        }
        var topicId=$('#comment_publish').attr('data');
        if(name!=''&&email!=''){
            $.ajax({
                type:'post',
                url:'/blog/'+topicId+'/comment',
                data:{name:name,email:email,content:content},
                success:function (result) {
                    if (!result.status){
                        layer.msg("发表评论失败", {
                            time: 3000,
                        });
                    }else{
                        var data=result.data;
                        if (data.commentCount==1) {
                            $('#commentBox').prepend(
                                '<div class="comment-box">'+
                                '<img class="layui-nav-img" src="/statics/image/avatar/default_avatar.png">'+
                                '<div class="comment-user">'+
                                '<a class="user-name">'+data.name+'</a>'+
                                '<p>'+
                                '<img class="first_blood" src="/statics/image/first_blood.jpg"> - '+
                                '<span>'+data.createdAt+'</span>'+
                                '</p>'+
                                '</div>'+
                                '<div class="comment-info">'+
                                '<p class="comment-content">'+data.content+'</p>'+
                                '<div>' +
                                '<a class="comment-a" href="">'+
                                '<i class="fa fa-fw fa-paper-plane-o"></i>' +
                                '回复' +
                                '</a>' +
                                '</div>'+
                                '</div>'+
                                '</div>');
                        }else{
                            $('#commentBox').prepend(
                                '<div class="comment-box">'+
                                '<img class="layui-nav-img" src="/statics/image/avatar/default_avatar.png">'+
                                '<div class="comment-user">'+
                                '<a class="user-name">'+data.name+'</a>'+
                                '<p>'+

                                '<span>'+data.commentCount+'楼 - </span>'+
                                '<span>'+data.createdAt+'</span>'+
                                '</p>'+
                                '</div>'+
                                '<div class="comment-info">'+
                                '<p class="comment-content">'+data.content+'</p>'+
                                '<div>' +
                                '<a class="comment-a" href="">'+
                                '<i class="fa fa-fw fa-paper-plane-o"></i>' +
                                '回复' +
                                '</a>' +
                                '</div>'+
                                '</div>'+
                                '</div>');
                        }
                        $('#comment_content').val('');
                        layer.close(index);
                    }
                }
            });
        }else{
            layer.open({
                id:'user_info',
                title:'雁过留名',
                area: ['330px', '215px'],
                btnAlign: 'c',
                anim: 4,
                resize:false,
                content:'<div class="comment-user-info">'+
                '<div class="layui-form-item">'+
                '<label class="layui-form-label">名号</label>'+
                '<div class="layui-input-block">'+
                '<input id="name" type="text" value="'+name+'" required  lay-verify="required" placeholder="好汉请留名" autocomplete="off" class="layui-input">'+
                '</div>'+
                '</div>'+
                '<div class="layui-form-item">'+
                '<label class="layui-form-label">邮箱</label>'+
                '<div class="layui-input-block">'+
                '<input id="email" type="text" value="'+email+'" required  lay-verify="required" placeholder="仅用于交流，绝不私下py" autocomplete="off" class="layui-input">'+
                '</div>'+
                '</div>'+
                '</div>',
                yes:function(index, layero){
                    var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
                    name=$('#name').val();
                    if ($.trim(name)==''){
                        $('#name').focus();
                        return;
                    }
                    email=$('#email').val();
                    if ($.trim(email)==''|| !reg.test(email)){
                        $('#email').focus();
                        return;
                    }
                    $.ajax({
                        type:'post',
                        url:'/blog/'+topicId+'/comment',
                        data:{name:name,email:email,content:content},
                        success:function (result) {
                            if (!result.status){
                                layer.msg("发表评论失败", {
                                    time: 3000,
                                });
                            }else{
                                var data=result.data;
                                if (data.commentCount==1) {
                                    $('#commentBox').prepend(
                                        '<div class="comment-box">'+
                                            '<img class="layui-nav-img" src="/statics/image/avatar/default_avatar.png">'+
                                            '<div class="comment-user">'+
                                                '<a class="user-name">'+data.name+'</a>'+
                                                '<p>'+
                                                    '<img class="first_blood" src="/statics/image/first_blood.jpg"> - '+
                                                    '<span>'+data.createdAt+'</span>'+
                                                '</p>'+
                                            '</div>'+
                                            '<div class="comment-info">'+
                                                '<p class="comment-content">'+data.content+'</p>'+
                                                '<div>' +
                                                    '<a class="comment-a" href="">'+
                                                        '<i class="fa fa-fw fa-paper-plane-o"></i>' +
                                                            '回复' +
                                                    '</a>' +
                                                '</div>'+
                                            '</div>'+
                                        '</div>');
                                }else{
                                    $('#commentBox').prepend(
                                        '<div class="comment-box">'+
                                            '<img class="layui-nav-img" src="/statics/image/avatar/default_avatar.png">'+
                                            '<div class="comment-user">'+
                                                '<a class="user-name">'+data.name+'</a>'+
                                                '<p>'+
                                                    '<span>'+data.commentCount+'楼 - </span>'+
                                                    '<span>'+data.createdAt+'</span>'+
                                                '</p>'+
                                            '</div>'+
                                            '<div class="comment-info">'+
                                                '<p class="comment-content">'+data.content+'</p>'+
                                                '<div>' +
                                                    '<a class="comment-a" href="">'+
                                                        '<i class="fa fa-fw fa-paper-plane-o"></i>' +
                                                        '回复' +
                                                    '</a>' +
                                                '</div>'+
                                            '</div>'+
                                        '</div>');
                                }
                                $('#comment_content').val('');
                                layer.close(index);
                            }
                        }
                    });
                }
            });
        }
    });
    $('.parent-comment').on('click',function () {
        var commentBox=$(this).parent().parent().parent();
        var receiverName=commentBox.find('.publish-name').text();
        var receiverId=$(this).attr('data');
        var commentListDiv=commentBox.find('.comment-list');
        commentListDiv.find('.reply-box').slideDown('normal',function () {
            var top=commentListDiv.find('.reply-box').offset().top;
            $('html, body').animate({
                scrollTop: top-100
            }, 100);
            commentListDiv.find('.comment-content').focus();
            commentListDiv.find('.btn-send').attr('data',receiverId);
            commentListDiv.find('.btn-send').attr('data-name',receiverName);
        });

    });
    $('.child-comment').on('click',function () {
        var receiverId=$(this).attr('data');
        var commentInfo=$(this).parent().parent();
        var receiverName=commentInfo.find('.publish-name').text();
        var commentListDiv=commentInfo.parent();
        commentListDiv.find('.reply-box').slideDown('normal',function () {
            var top=commentListDiv.find('.reply-box').offset().top;
            $('html, body').animate({
                scrollTop: top-100
            }, 100);
            commentListDiv.find('.comment-content').focus();
            commentListDiv.find('.btn-send').attr('data',receiverId);
            commentListDiv.find('.btn-send').attr('data-name',receiverName);
        });
    });
    $('.cancel').on('click',function () {
        $(this).parent().parent().slideUp();
    });
    $('.btn-send').on('click',function () {
        var parentId=$(this).attr('data-parent-id');
        var topicId=$(this).attr('data-id');
        var receiverName=$(this).attr('data-name');
        var receiverId=$(this).attr('data');
        var replyDiv=$(this).parent().parent();
        var textarea=replyDiv.find('.comment-content');
        var content=textarea.val();
        if($.trim(content)==''|| content==''){
            textarea.focus();
            return;
        }else{
            if(name!=''&&email!=''){
                $.ajax({
                    type:'post',
                    url:'/blog/'+topicId+'/comment',
                    data:{name:name,email:email,content:content,receiverId:receiverId,parentId:parentId},
                    success:function (result) {
                        if (!result.status) {
                            layer.msg("回复失败", {
                                time: 3000,
                            });
                        } else {
                            var data = result.data;
                            replyDiv.before(
                                '<div class="comment-info">' +
                                    '<p>' +
                                        '<a class="user-name">'+data.name+'</a> : '+
                                        '<a class="user-name">@'+receiverName+'  </a>'+
                                        '<span>'+data.content+'</span>'+
                                    '</p>'+
                                '<div class="comment-child-tool">' +
                                    '<span class="comment-date">'+data.createdAt+'</span>'+
                                    '<a class="comment-a child-comment" data="'+data.id+'" href="javascript:;">' +
                                        '<i class="fa fa-fw fa-paper-plane-o"></i>回复'+
                                    '</a>'+
                                '</div>'+
                                '</div>'
                            );

                        }
                        textarea.val('');
                    }
                });
            }else{
                layer.open({
                    id:'user_info',
                    title:'雁过留名',
                    area: ['330px', '215px'],
                    btnAlign: 'c',
                    anim: 4,
                    resize:false,
                    content:'<div class="comment-user-info">'+
                    '<div class="layui-form-item">'+
                    '<label class="layui-form-label">名号</label>'+
                    '<div class="layui-input-block">'+
                    '<input id="name" type="text" value="'+name+'" required  lay-verify="required" placeholder="好汉请留名" autocomplete="off" class="layui-input">'+
                    '</div>'+
                    '</div>'+
                    '<div class="layui-form-item">'+
                    '<label class="layui-form-label">邮箱</label>'+
                    '<div class="layui-input-block">'+
                    '<input id="email" type="text" value="'+email+'" required  lay-verify="required" placeholder="仅用于交流，绝不私下py" autocomplete="off" class="layui-input">'+
                    '</div>'+
                    '</div>'+
                    '</div>',
                    yes:function(index, layero){
                        var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
                        name=$('#name').val();
                        if ($.trim(name)==''){
                            $('#name').focus();
                            return;
                        }
                        email=$('#email').val();
                        if ($.trim(email)==''|| !reg.test(email)){
                            $('#email').focus();
                            return;
                        }
                        $.ajax({
                            type:'post',
                            url:'/blog/'+topicId+'/comment',
                            data:{name:name,email:email,content:content,receiverId:receiverId,parentId:parentId},
                            success:function (result) {
                                if (!result.status){
                                    layer.msg("回复失败", {
                                        time: 3000,
                                    });
                                }else{
                                    var data=result.data;
                                    replyDiv.before(
                                        '<div class="comment-info">' +
                                        '<p>' +
                                        '<a class="user-name">'+data.name+'</a> : '+
                                        '<a class="user-name">@'+receiverName+'  </a>'+
                                        '<span>'+data.content+'</span>'+
                                        '</p>'+
                                        '<div class="comment-child-tool">' +
                                        '<span class="comment-date">'+data.createdAt+'</span>'+
                                        '<a class="comment-a child-comment" data="'+data.id+'" href="javascript:;">' +
                                        '<i class="fa fa-fw fa-paper-plane-o"></i>回复'+
                                        '</a>'+
                                        '</div>'+
                                        '</div>'
                                    );
                                }
                                textarea.val('');
                                layer.close(index);
                            }
                        });
                    }
                });
            }
        }
    });
})