var pageSize = 10;

$(document).ready(function() {
	getDepartment();
	inintPage();
	$("#search").click(function(){
		inintPage();
	});
	$("#staffsubmit").click(function() {
		if(formValidate()){
			$("#staffform").ajaxSubmit(function(data){
				if(data == "success"){
					alert("修改成功！");
					window.location.href="staff.html";
				}
			});
		}else{
			bootbox.alert("请认真填写！", "确定");
		}
		return false;
	});
});

function inintPage(){
	$.ajax({
		url : '../rest/staff/count?'+getParameters(),
		type : 'GET',
		success : function(meta) {
			$("#Pagination").pagination(meta, {
				num_edge_entries : 1, // 边缘页数
				num_display_entries : 4, // 主体页数
				callback : getlist,
				items_per_page : pageSize, // 每页显示1项
				prev_text : "前一页",
				next_text : "后一页"
			});
		}
	});
}

function getParameters() {
	var staffName = $("#staffName").val().trim();
	var address = $("#address").val().trim();
	var phone = $("#phone").val().trim();
	var position = $("#position").val().trim();
	var department = $("#department").val();
	var sex = $("#sex").val();
	var params ="";
	params += staffName == "" ? "" : "&staffName=" + staffName;
	params += address == "" ? "" : "&address=" + address;
	params += phone == "" ? "" : "&phoneNumber=" + phone;
	params += position == "" ? "" : "&position=" + position;
	params += department == "全部" ? "" : "&department=" + department;
	params += sex == "全部" ? "" : "&sex=" + sex;
	return params;
}

function getlist(page_index, jq) {
	$.ajax({
		url : '../rest/staff/listForPage?pageIndex='+page_index * pageSize+'&pagesize='+pageSize+getParameters(),
		type : 'GET',
		success : function(data) {
			$("#info").empty();
			$.each(data,function(i,item){
				var row = "<tr><td>"
					+item.staffName+"</td><td>"
					+item.sex+"</td><td>"
					+item.address+"</td><td>"
					+item.phoneNumber+"</td><td>"
					+item.salary+".00元</td><td>"
					+getDepartmentName(item.department)+"</td><td>"
					+item.position+"</td><td><a href='staffdetail.html?id="
					+item.id+"' class='btn btn-small btn-primary'>详细</a>&nbsp<a href='#modal' class='btn btn-small btn-success' data-toggle='modal' onclick='javascript:initModal("
					+item.id+")'>修改</a>&nbsp<a class='btn btn-small btn-danger' onclick='javascript:deleteStaff("
					+item.id+");'>删除</a></td></tr>";
				$("#info").append(row);
			});
		}
	});
}

function initModal(id){
	$.ajax({
		url : '../rest/staff/'+id,
		type : 'GET',
		success : function(data) {
			$("#MstaffId").val(data.id);
			$("#MstaffName").val(data.staffName);
			$("#MloginId").val(data.loginId);
			$("#Mpassword").val(data.password);
			$("#Mrepassword").val(data.password);
			$("#Mposition").val(data.position);
			$("#Maddress").val(data.address);
			$("#Msalary").val(data.salary);
			$("#Mphone").val(data.phoneNumber);
		}
	});
}

function getDepartmentName(object){
//	console.log(object);
	if(object != null){
		return object.name;
	}else {
		return "未分配";
	}
}

function deleteStaff(id){
	bootbox.confirm("Are you sure?", function(result) {
	    if (result) {
	    	$.ajax({
	    		url : '../rest/staff/'+id,
	    		type : 'DELETE',
	    		success : function() {
	    			inintPage();
	    		}
	    	});
	    } else {
	    	
	    }
	});
}

//获取部门列表
function getDepartment() {
	$("#Mdepartment").empty();
	$.ajax({
		url : '../rest/department/getDepartmentName',
		type : 'GET',
		success : function(data) {
			$.each(data,function(i,item){
				$("#Mdepartment").append("<option>"+item+"</option>");
				$("#department").append("<option>"+item+"</option>");
				
			});
			$("#Mdepartment").append("<option>暂不分配</option>");
		}
	});
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
	if($(object).val() != $("#Mpassword").val()){
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

//提交前验证表单
function formValidate() {
	var flag = true;
	
	var reg2 =/[\x21-\x7E]{6,15}/;
	if(!reg2.test($("#Mpassword").val())){
		$("#Mpassword").parent().parent().addClass("error");
		$("#Mpassword").next().html("密码长度在6-15之间");
		flag = false;
	}else{
		$("#Mpassword").parent().parent().removeClass("error");
		$("#Mpassword").next().html("");
	}
	
	if($("#Mrepassword").val() != $("#Mpassword").val()){
		$("#Mrepassword").parent().parent().addClass("error");
		$("#Mrepassword").next().html("两次密码不一致");
		flag = false;
	}else{
		$("#Mrepassword").parent().parent().removeClass("error");
		$("#Mrepassword").next().html("");
	}
	
	var reg3 = /^1[0-9]{10}$/;
	if(!reg3.test($("#Mphone").val())){
		$("#Mphone").parent().parent().addClass("error");
		$("#Mphone").next().html("请正确输入手机号");
		flag = false;
	}else{
		$("#Mphone").parent().parent().removeClass("error");
		$("#Mphone").next().html("");
	}
	
	if($("#Maddress").val().length<2){
		$("#Maddress").parent().parent().addClass("error");
		$("#Maddress").next().html("地址长度应该在2为以上");
		flag = false;
	}else{
		$("#Maddress").parent().parent().removeClass("error");
		$("#Maddress").next().html("");
	}
	
	var reg4 = /^\d{3,5}/;
	if(!reg4.test($("#Msalary").val())){
		$("#Msalary").parent().parent().parent().addClass("error");
		$("#Msalary").parent().next().html("工资不正确");
		flag = false;
	}else{
		$("#Msalary").parent().parent().parent().removeClass("error");
		$("#Msalary").parent().next().html("");
	}
	
	return flag;
	
}
