/*function initUploadGoodsExcel() {
    var button = $('#btn_import');
    new AjaxUpload(button, {
        action :  '/excel/uploadExcel',
        autoSubmit : true,
        name : 'file',// 文件对象名称（不是文件名）
        data : {},
        onSubmit : function(file, extension) {
            console.log(file);
            var d = /\.[^\.]+$/.exec(file); // 文件后缀
            if (d != ".xls" && d != ".xlsx") {
                alert('文件格式错误，请上传.xls或.xlsx格式！');
                return false;
            } else {
                $("#ImportFileName").text(file);
            }
        },
        onComplete : function(file,data ,response) {
            var msg ="";
            if(data.result >0){
                msg+="EXCEL导入成功，其中有"+data.result+"条写入失败，请检查商品ID,商品名称,类目名称是否重复，或者商品ID,商品名称,类目名称信息不完整！"
            }else{
                msg+="EXCEL导入成功，并且全部写入成功！"
            }
            $("#sample_1").bootstrapTable('refresh', param);
            alert(msg);
        }
    });
}*/
$(document).ready(function () {
    //数据表格
    initList();
});
function initList(){
    var str='';
    $.post("/excel/getList",function (data) {
        $.each(data,function (index,row) {
            str += (row.id + '\t' + row.excel1 + '\t' + row.excel2 + '\t' + row.excel3 + '\t' + row.status + '\n');
        })
        $("#tableArea").append(str);
    });
}
function searchParam() {
    var param={
        status:1
    };
    return param;
}
function initDownloadGoodsExcel() {
    var strPro=JSON.stringify(searchParam());
    console.info(strPro);
    location.href='/excel/downloadExcel?strPro='+encodeURIComponent(searchParam());
}