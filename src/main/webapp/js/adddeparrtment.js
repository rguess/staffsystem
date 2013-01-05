$(document).ready(function() {
	$("#departmentsubmit").click(function() {
			$("#departmentform").ajaxSubmit(function(data){
				if(data == "success"){
					bootbox.confirm("添加成功，继续添加?", function(result) {
					    if (result) {
					    	window.location.href="adddepartment.html";
					    } else {
					    	window.location.href="department.html";
					    }
					});
				}
			});
		return false;
	});
});
