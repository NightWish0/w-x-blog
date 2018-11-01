layui.use(['flow'],function () {
    var flow = layui.flow;
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
                                        '<label>评论 20</label>'+
                                    '</div>'+
                                '</div>'+
                        '</li>');
                });
                next(lis.join(''),page<res.pages);
            })
        }
    });
})