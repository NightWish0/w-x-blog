$(function () {
    var fileMarkHash=$('#fileMarkHash').val();
    console.log(fileMarkHash);
    editormd("editormd", {
        width: "100%",
        height: 640,
        path : '/statics/tools/editormd/lib/',
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
        imageUploadURL : "/admin/topics/upload?fileMarkHash="+fileMarkHash,
        onload : function() { //上传成功之后的回调
            // console.log('onload', this);
        }
    });
});
$(function () {
    // var labels = new Bloodhound({
    //     datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
    //     queryTokenizer: Bloodhound.tokenizers.whitespace,
    //     // prefetch: '/admin/labels'
    //     remote: {
    //         url: '/admin/labels',
    //         filter: function(names) {
    //             return $.map(names,function (labelName) {
    //                 return {name:labelName};
    //             });
    //         }
    //     }
    // });
    // labels.initialize(true);
    // $('#labels').tagsinput({
    //     maxTags: 5,
    //     maxChars: 50,
    //     trimValue: true,
    //     typeaheadjs: {
    //         name: 'labels',
    //         displayKey: 'name',
    //         valueKey: 'name',
    //         source: labels.ttAdapter()
    //     }
    // });
    var substringMatcher = function(strs) {
        return function findMatches(q, cb) {
            //传入的q是键盘输入的字符，传入的cb一个处理数组的函数
            var matches, substringRegex;
            matches = [];
            substrRegex = new RegExp(q.toLowerCase());
            $.each(strs, function(i, str) {
                if (substrRegex.test(str.name.toLowerCase())){
                    matches.push({"name" : str.name});
                }
            });
            cb(matches);
        };
    };
    $.get("/admin/labels", function(result){
        var data=$.map(result,function (labelName) {
            return {'name':labelName};
        });
        $("#labels").tagsinput({
            maxTags: 5,
            maxChars: 50,
            confirmKeys: [],
            typeaheadjs : {
                name : 'labels',
                displayKey : 'name',
                valueKey : 'name',
                source : substringMatcher(data)
            }
        });
        var addLabel=function(){
            var label=$.trim($(".tt-input").val());
            if (label.length>0){
                var flag=true;
                $.each(data, function(i, name) {
                    if (label.toLowerCase()==name.name.toLowerCase()){
                        $('#labels').tagsinput('add',name.name);
                        flag=false;
                    }
                });
                if (flag){
                    $('#labels').tagsinput('add',label);
                }
            }
        }
        $('.tt-input').on('blur',function (event) {
            addLabel();
        })
        $('.tt-input').on('keydown',function (event) {
            if (event.keyCode==13) {
                addLabel();
                return false;
            }
        })
    }, "json");
});