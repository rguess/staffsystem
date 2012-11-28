$(document).ready(function() {
	//alert(123);
});

function checkLoginId(object){
	var reg =/^[a-zA-Z][a-zA-Z0-9_]{4,15}$/;
	if(!reg.test($(object).val())){
		$(object).parent().parent().addClass("error");
		$(object).next().html("请正确输入用户名");
	}else{
		$(object).parent().parent().removeClass("error");
		$(object).next().html("");
	}
}