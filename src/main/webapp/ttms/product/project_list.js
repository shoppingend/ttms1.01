$(document).ready(function(){
	$("#queryFormId")
	.on("click",".btn-search",doQueryObjects)
	.on("click",".btn-valid,.btn-invalid",doValidById)
	.on("click",".btn-add,.btn-update",doLoadEditPage)
	doGetObjects();
});

/**加载编辑页面*/
function doLoadEditPage(){
	var url="project/editUI.do";
	//$(".content").load(url)
	var title;
	//在模态框中异步加载显示页面。
	//本项目中模态框的定义在index.jsp中，而且默认是隐藏的
	if($(this).hasClass("btn-add")){
		title="添加项目信息"
	}
	if($(this).hasClass("btn-update")){
		//将获得的要修改记录的id值绑定到模态框对象上
		//根据模态框的id值判定执行添加还是修改
		var idValue=$(this).parent().parent().data("id");
		$("#modal-dialog").data("idKey",idValue);
		title="修改项目信息";
	}
	$("#modal-dialog .modal-body")
	.load(url,function(){//callback method
		//页面加载完成显示模态框(其中show表示显示，hide表示隐藏
		$(".modal-title").html(title);
		$("#modal-dialog").modal("show");
	});
}

/*禁用或启用项目信息*/
function doValidById(){
	//1.设置valid的值
	var valid;
	//1.1获得点击对象上的
	/*var clazz=$(this).attr("class");
	if(clazz=="btn btn-primary btn-valid"){
		valid=1;
	}*/
	if($(this).hasClass("btn-valid")){
		valid=1;//启用
	}
	if($(this).hasClass("btn-invalid")){
		valid=0;//禁用
	}
	//2.获得选中的checkbox的id值
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
	//3.发起异步请求，更新数据
	var url="project/doValidById.do";
	var params={
			"valid":valid,
			"ids":ids
	};
	$.post(url,params,function(result){//controller return new JsonResult
		if(result.state==1){
			alert(result.message);
			//重新执行查询操作
			doGetObjects();
		}else{
			alert(result.message);
		}
	})
}

/*点击查询按钮时执行此方法*/
function doQueryObjects(){
	//1.初始化当前页码数据
	$("#pageId").data("pageCurrent",1);
	//2.根据条件查询数据
	doGetObjects();
}

/*获取项目信息*/
function doGetObjects(){
//	var url="project/doGetObjects.do";
/*	$.ajax({
		url:url,
		type:"get",
		dataType:"json",
		success:function(result){
			console.log(result);
		}
	});*/
	var url="project/doGetPageObjects.do";
	var pageCurrent=$("#pageId").data("pageCurrent");
	if(!pageCurrent)pageCurrent=1;
	var params={"pageCurrent":pageCurrent};
	params.name=$("#searchNameId").val();
	params.valid=$("#searchValidId").val();
	$.getJSON(url,params,function(result){
		//console.log(result);
		if(result.state==1){
			/*将数据显示在table 的tbody位置*/
			setTableBodyRows(result.data.list);//map中的key对应的值
			setPagination(result.data.pageObject);
		}else{
			alert(result.message);
		}
		
	});
}

function setTableBodyRows(data){
	//1.获得tbody对象
	var tBody=$("#tbodyId");
	tBody.empty();
	//2.迭代数据集result
	for(var i in data){
		//2.1构建一个tr对象
		var tr=$("<tr></tr>");
		tr.data("id",data[i].id);
		//2.2构建每行td对象（一行有多个）
		//2.3在td对象内容填充具体数据
		var tds="<td><input type='checkbox' name='checkId' value='"+data[i].id+"'></td>"+
				"<td>"+data[i].code+"</td>"+
				"<td>"+data[i].name+"</td>"+
				"<td>"+data[i].beginDate+"</td>"+
				"<td>"+new Date(data[i].endDate).toLocaleDateString()+"</td>"+
				"<td>"+(data[i].valid?"有效":"无效")+"</td>"+
				"<td><input type='button' class='btn btn-default btn-update' value='修改'></td>";
		//2.4将td添加倒tr对象中（一行要放多个）
		tr.append(tds);
		//2.5将tr追加倒tbody中
		tBody.append(tr);
	}
}