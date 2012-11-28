var pageSize = 5;

$(document).ready(function() {
	inintPage();
	$("#search").click(function(){
		inintPage();
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
					+item.department.name+"</td><td>"
					+item.position+"</td><td><a class='btn btn-small btn-primary'>详细</a>&nbsp<a class='btn btn-small btn-success'>修改</a>&nbsp<a class='btn btn-small btn-danger' onclick='javascript:deleteStaff("
					+item.id+");'>删除</a></td></tr>";
				$("#info").append(row);
			});
		}
	});
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