$(document).ready(function() {
	getDepartment();
	$("#staffsubmit").click(function() {
		if(formValidate()){
			$("#staffform").ajaxSubmit(function(data){
				if(data == "success"){
					bootbox.confirm("添加成功，继续添加?", function(result) {
					    if (result) {
					    	window.location.href="addstaff.html";
					    } else {
					    	window.location.href="staff.html";
					    }
					});
				}
			});
		}else{
			bootbox.alert("请认真填写！", "确定");
		}
		return false;
	});
});

//提交前验证表单
function formValidate() {
	var flag = true;
	if($("#staffName").val().length>4 || $("#staffName").val().length<2){
		$("#staffName").parent().parent().addClass("error");
		$("#staffName").next().html("长度在2到4之间");
		flag = false;
	}else{
		$("#staffName").parent().parent().removeClass("error");
		$("#staffName").next().html("");
	}
	
	var reg1 =/^[a-zA-Z][a-zA-Z0-9_]{4,15}$/;
	if(!reg1.test($("#loginId").val())){
		$("#loginId").parent().parent().addClass("error");
		$("#loginId").next().html("账号必须是以字母开头，长度在5到15之间");
		flag = false;
	}else if(!checkLoginIdIsExist($("#loginId").val())){
		$("#loginId").parent().parent().addClass("error");
		$("#loginId").next().html("该登陆名已存在");
	}else{
		$("#loginId").parent().parent().removeClass("error");
		$("#loginId").next().html("");
	}
	
	var reg2 =/[\x21-\x7E]{6,15}/;
	if(!reg2.test($("#password").val())){
		$("#password").parent().parent().addClass("error");
		$("#password").next().html("密码长度在6-15之间");
		flag = false;
	}else{
		$("#password").parent().parent().removeClass("error");
		$("#password").next().html("");
	}
	
	if($("#repassword").val() != $("#password").val()){
		$("#repassword").parent().parent().addClass("error");
		$("#repassword").next().html("两次密码不一致");
		flag = false;
	}else{
		$("#repassword").parent().parent().removeClass("error");
		$("#repassword").next().html("");
	}
	
	var reg3 = /^1[0-9]{10}$/;
	if(!reg3.test($("#phone").val())){
		$("#phone").parent().parent().addClass("error");
		$("#phone").next().html("请正确输入手机号");
		flag = false;
	}else{
		$("#phone").parent().parent().removeClass("error");
		$("#phone").next().html("");
	}
	
	if($("#address").val().length<2){
		$("#address").parent().parent().addClass("error");
		$("#address").next().html("地址长度应该在2为以上");
		flag = false;
	}else{
		$("#address").parent().parent().removeClass("error");
		$("#address").next().html("");
	}
	
	var reg4 = /^\d{3,5}/;
	if(!reg4.test($("#salary").val())){
		$("#salary").parent().parent().parent().addClass("error");
		$("#salary").parent().next().html("工资不正确");
		flag = false;
	}else{
		$("#salary").parent().parent().parent().removeClass("error");
		$("#salary").parent().next().html("");
	}
	
	return flag;
	
}

//验证员工名字
function checkStaffname(object){
	
	if($(object).val().length>4 || $(object).val().length<2){
		$(object).parent().parent().addClass("error");
		$(object).next().html("长度在2到4之间");
	}else{
		$(object).parent().parent().removeClass("error");
		$(object).next().html("");
	}
}

//验证员工登陆名
function checkLoginId(object){

	var reg =/^[a-zA-Z][a-zA-Z0-9_]{4,15}$/;
	if(!reg.test($(object).val())){
		$(object).parent().parent().addClass("error");
		$(object).next().html("账号必须是以字母开头，长度在5到15之间");
	}else if(!checkLoginIdIsExist($(object).val())){
		$(object).parent().parent().addClass("error");
		$(object).next().html("该登陆名已存在");
	}else{
		$(object).parent().parent().removeClass("error");
		$(object).next().html("");
	}
	
}

//验证员工密码
function checkPassword(object) {
	var reg =/[\x21-\x7E]{6,15}/;
	if(!reg.test($(object).val())){
		$(object).parent().parent().addClass("error");
		$(object).next().html("密码长度在6-15之间");
	}else{
		$(object).parent().parent().removeClass("error");
		$(object).next().html("");
	}
}

//验证员工重复密码
function checkRePassword(object) {
	
	if($(object).val() != $("#password").val()){
		$(object).parent().parent().addClass("error");
		$(object).next().html("两次密码不一致");
	}else{
		$(object).parent().parent().removeClass("error");
		$(object).next().html("");
	}
}

//验证手机号
function checkPhone(object) {
	var reg = /^1[0-9]{10}$/;
	if(!reg.test($(object).val())){
		$(object).parent().parent().addClass("error");
		$(object).next().html("请正确输入手机号");
	}else{
		$(object).parent().parent().removeClass("error");
		$(object).next().html("");
	}
}

//验证地址
function checkAddress(object) {
	if($(object).val().length<2){
		$(object).parent().parent().addClass("error");
		$(object).next().html("地址长度应该在2为以上");
	}else{
		$(object).parent().parent().removeClass("error");
		$(object).next().html("");
	}
}

//验证工资
function checkSalary(object) {
	var reg = /^\d{3,5}/;
	if(!reg.test($(object).val())){
		$(object).parent().parent().parent().addClass("error");
		$(object).parent().next().html("工资不正确");
	}else{
		$(object).parent().parent().parent().removeClass("error");
		$(object).parent().next().html("");
	}
}

//验证用户名是否存在
function checkLoginIdIsExist(loginId) {
	var flag = true;
	$.ajax({
		url : '../rest/staff/loginIdIsExist/'+loginId,
		type : 'GET',
		async : false,
		success : function(data) {
			if(data =="no"){
				flag = true;
			}else {
				flag = false;
			}
		}
	});
	return flag;
}

//获取部门列表
function getDepartment() {
	$("#department").empty();
	$.ajax({
		url : '../rest/department/getDepartmentName',
		type : 'GET',
		success : function(data) {
			$.each(data,function(i,item){
				$("#department").append("<option>"+item+"</option>");
			});
			$("#department").append("<option>暂不分配</option>");
		}
	});
}










