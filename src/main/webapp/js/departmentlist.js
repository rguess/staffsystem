$(document).ready(function() {
	getlist();
	$("#departmentsubmit").click(function() {
		$("#departmentform").ajaxSubmit(function(data) {
			if (data == "success") {
				alert("修改成功！");
				window.location.href = "department.html";
			}
		});
		return false;
	});
});

function getlist() {
	$
			.ajax({
				url : '../rest/department',
				type : 'GET',
				success : function(data) {
					$("#info").empty();
					$
							.each(
									data,
									function(i, item) {
										var row = "<tr><td>"
												+ item.name
												+ "</td><td>"
												+ item.introduction
												+ "</td><td><a class='btn btn-small btn-primary'>员工列表</a>&nbsp<a href='#modal' data-toggle='modal' class='btn btn-small btn-success' onclick='javascript:initModal("
												+ item.id
												+ ");'>修改</a>&nbsp<a class='btn btn-small btn-danger' onclick='javascrit:deleteDepartment("
												+ item.id
												+ ")'>删除</a></td></tr>";
										$("#info").append(row);
									});
				}
			});
}

function initModal(id) {
	$.ajax({
		url : '../rest/department/' + id,
		type : 'GET',
		success : function(data) {
			$("#Did").val(data.id);
			$("#departmentName").val(data.name);
			$("#introduction").val(data.introduction);
		}
	});
}

function deleteDepartment(id) {
	bootbox.confirm("Are you sure?", function(result) {
		if (result) {
			$.ajax({
				url : '../rest/department/' + id,
				type : 'DELETE',
				success : function() {
					getlist();
				}
			});
		} else {

		}
	});
}