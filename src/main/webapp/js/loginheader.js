$(document).ready(function() {
	
	displayer();
//	$("#loginout").click(function() {
//		loginout();
//	});
	
});

//显示用户名
function displayer(){
	$.ajax({
		url : '../rest/staff/getUserBySession',
		type : 'GET',
		success : function(data) {
			if(data == null){
				alert("cookie is null");
				console.log("cookie is null");
			}else {
				$("#loginUsername").empty().append("欢迎! "+data["staffName"]);
			}
		}
	});
}

//登出处理
function loginout(){
	$.ajax({
		url : '../rest/staff/loginOut',
		type : 'POST',
		success : function(data) {
			if(data == "success"){
				window.location.href = "../index.jsp";
			}
		}
	});
}