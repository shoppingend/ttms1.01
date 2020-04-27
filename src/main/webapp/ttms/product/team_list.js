$(document).ready(function(){
	$("#queryFormId")
	.on("click",".btn-search",doQueryObjects)
	.on("click",".btn-add,.btn-update",doLoadEditPage)
	.on("click",".btn-valid,.btn-invalid",doValidById)
	doGetObjects();
});

/**启用或禁用*/
function doValidById(){
	var valid;
	if($(this).hasClass("btn-valid")){
		valid=1;
	}
	if($(this).hasClass("btn-invalid")){
		valid=0;
	}
	var ids="";
	$("#tbodyId input[name='checkId']")
	.each(function(){
		if($(this).prop("checked")){
			if(ids==""){
				ids+=$(this).val();				
			}else{
				ids+=","+$(this).val();
			}
		}
	});
	if(ids.length==0){
		alert("请至少选择一个");
		return;
	}
	
	var url="team/doValidById.do";
	var params={
			"valid":valid,
			"ids":ids
	};
	$.post(url,params,function(result){
		if(result.state==1){
			doGetObjects();
		}else{
			alert(result.message);
		}
	});
}

/**加载编辑页面*/
function doLoadEditPage(){
	var url="team/editUI.do";
	var title;
	if($(this).hasClass("btn-add")){
		title="添加团目信息";
	}
	if($(this).hasClass("btn-update")){
		var idValue=$(this).parent().parent().data("id");
		$("#modal-dialog").data("idKey",idValue);
		title="修改团目信息";
	}
	$("#modal-dialog .modal-body")
	.load(url,function(){
		$(".modal-title").html(title);
		$("#modal-dialog").modal("show");
	});
}

/**获取项目信息*/
function doGetObjects(){
	var url="team/doFindPageObjects.do?t="+Math.random();
	var pageCurrent=$("#pageId").data("pageCurrent");
	if(!pageCurrent)pageCurrent=1;
	var params={"pageCurrent":pageCurrent};
	params.name=$("#searchNameId").val();
	$.post(url,params,function(result){
		if(result.state==1){
			setTableBodyRows(result.data.list);
			setPagination(result.data.pageObject);
		}else{
			alert(result.message);
		}
	});
}

/**在tablebody中显示数据*/
function setTableBodyRows(list){
	var tBody=$("#tbodyId");
	tBody.empty();
	
	for(var i in list){
		var tr=$("<tr></tr>");
		tr.data("id",list[i].id);
		var tds="<td><input type='checkbox' name='checkId' value='"+list[i].id+"'/></td>"+
				"<td>"+list[i].name+"</td>"+
				"<td>"+list[i].projectName+"</td>"+
				"<td>"+(list[i].valid?'有效':'无效')+"</td>"+
				"<td><input type='button' class='btn btn-default btn-update' value='修改'/ ></td>";
		tr.append(tds);
		tBody.append(tr);
	}
}

/**根据项目名称查询团目信息*/
function doQueryObjects(){
	$("#pageId").data("pageCurrent",1);
	doGetObjects();
}
