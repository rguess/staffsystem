
$(document).ready(function() {
	var id = $.getParam("id");
	initpage(id);
});

function initpage(id){
	$.ajax({
		url : '../rest/staff/'+id,
		type : 'GET',
		success : function(data){
			$("#headerName").empty().append(data.staffName);
			$("#staffName").empty().append(data.staffName);
			$("#loginId").empty().append(data.loginId);
			$("#sex").empty().append(data.sex);
			$("#position").empty().append(data.position);
			$("#phoneNumber").empty().append(data.phoneNumber);
			$("#department").empty().append(data.department == null?"未分配":data.department.name);
			$("#companyDate").empty().append(data.companyDate);
			$("#staffImage").attr("src",getImagePath(data.loginId));
		}
	});
}

function getImagePath(loginId){
	var path = "";
	$.ajax({
		url : '../image/'+loginId+'.gif',
		type : 'GET',
		async : false,
		success : function(){
			
			path = "../image/"+loginId+".gif";
		},
		error:function(){
			
			path = "../image/"+loginId+".jpg";
		}
	});
	
	return path;
}











