var columns = [
{
field : 'selectItem',
radio : true
},
{
title : '分类id',
field : 'id',
visible : false,
align : 'center',
valign : 'middle',
width : '80px'
},
{
title : '分类名称',
field : 'name',
align : 'center',
valign : 'middle',
sortable : true,
width : '180px'
},
{
title : '上级分类',
field : 'parentName',
align : 'center',
valign : 'middle',
sortable : true,
width : '180px'
},
{
title : '排序号',
field : 'sort',
align : 'center',
valign : 'middle',
sortable : true,
width : '100px'
}];//定义table表头及每列元数据信息

$(document).ready(function(){
	$("#formHead")
	.on("click",".btn-delete",doDeleteObjects)
	.on("click",".btn-add,.btn-update",doLoadEditPage)
	
	doGetObjects();
});

function doGetObjects(){
	var tableId="typeTable";
	var url="type/doFindObjects.do";
	//TreeTable是在tree.table.js中定义的
	var table =new TreeTable(tableId,url,columns);
	//初始化table对象（底层会发起异步请求获得数据然后更新页面
	table.setExpandColumn(2);
	table.init();
}

function doDeleteObjects(){
	var id =getSelectedId();
	if(!id){
		alert("请选择");
		return;
	}
	var url="type/doDeleteObjects.do";
	var params={"id":id};
	$.post(url,params,function(result){
		if(result.state==1){
			alert(result.meassge);
			doGetObjects();
		}else{
			alert(result.message);
		}
	});
}

/**获得选中的id值*/
function getSelectedId(){
	var selections=$("#typeTable").bootstrapTreeTable("getSelections");
//	console.log(selections);
	if(selections.length==0){
		return;
	}
	return selections[0].id;
}

/**加载编辑页面*/
function doLoadEditPage(){
	var title;
	if($(this).hasClass("btn-add"))
		title="添加分类信息";
	if($(this).hasClass("btn-update")){
		var idValue=getSelectedId();
		if(!idValue){
			alert("请先选择");
			return;
		}
		title="修改分类信息";
		$(".content").data("id",idValue);
//		alert($(".content").data("id"));
//		console.log("id="+id);
	}
	var url="type/editUI.do";
	$(".content").load(url,function(){
		$("#titleId").html(title)
	})
}