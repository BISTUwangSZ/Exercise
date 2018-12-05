function makeTop() {
    var idValue = $("#id").val();
    var params = {
        id:idValue
    };
    $.post("/getUserCondition/top",params,function (data) {
        appendSql(data);
    })
}
function appendSql(data) {
    if(data==1){
        alert("该数据不存在,移动失败");
        return;
    } else if(data<0){
        alert("移动失败");
        return;
    } else {
        var str="";
        $.each(data,function (i,item) {
            str+=data[i].id +"\t";
            str+=data[i].name+"\t";
            str+=data[i].age+"\t";
            str+=data[i].gender+"\t";
            str+=data[i].psw+"\t";
            str+=data[i].seq+"\t\n";
        });
        console.info(str);
        $("#show").val(str);
    }
}
function moveUp() {
    var idValue = $("#up_id").val();
    var params = {
        id:idValue
    };
    $.post("/getUserCondition/moveUp",params,function (data) {
        appendSql(data);
    })
}

function moveDown() {
    var idValue = $("#down_id").val();
    var params = {
        id:idValue
    };
    $.post("/getUserCondition/moveDown",params,function (data) {
        appendSql(data);
    })
}