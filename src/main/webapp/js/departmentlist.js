$(document).ready(function() {
	getlist();
});

function getlist() {
	$.ajax({
		url : '../rest/department',
		type : 'GET',
		success : function(data) {
			$("#info").empty();
			$.each(data,function(i,item){
				var row = "<tr><td>"
					+item.name+"</td><td>"
					+item.introduction+"</td><td><a class='btn btn-small btn-primary'>员工列表</a>&nbsp<a class='btn btn-small btn-success'>修改</a>&nbsp<a class='btn btn-small btn-danger'>删除</a></td></tr>";
				$("#info").append(row);
			});
		}
	});
}