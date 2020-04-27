var zTree; 
var setting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "id",  //节点数据中保存唯一标识的属性名称
			pIdKey : "parentId",  //节点数据中保存其父节点唯一标识的属性名称
			rootPId : null  //根节点id
		}
	}
}

$(document).ready(function(){
	//点击上级菜单
	$("#editTypeForm")
	.on("click",".load-product-type",loadZtreeNodes);
	
	$("#typeLayer")
	.on("click",".btn-cancle",doHideZtree)
	.on("click",".btn-confirm",doSetSelectedType)
	//点击返回
	$("#btn-return").click(doBack);
	//点击确定按钮提交
	$("#btn-save").click(doSaveOrUpdate);
	
	var id=$(".content").data("id");
//	alert("id="+id);
	if(id)
		doFindObjectById(id);
});

function doFindObjectById(id){
	var url="type/doFindObjectById.do";
	var params={"id":id};
	$.post(url,params,function(result){
		if(result.state==1){
			doInitFormData(result.data);
			alert("能走到这一步");
		}else{
			alert(result.message);
		}
	});
}

function doInitFormData(obj){
	$("#typeNameId").val(obj.name);
	$("#parentNameId").val(obj.parentName);
	$("#editTypeForm").data("parentId",obj.parentId);
	$("#typeSortId").val(obj.sort);
	$("#typeNoteId").html(obj.note);
}

function doSaveOrUpdate(){
	//1.获取表单数据
	if(!$("#editTypeForm").valid())return;
	var params=getEditFormData();
	var id=$(".content").data("id");
	//2.保存数据
	var saveUrl="type/doSaveObject.do";
	var updateUrl="type/doUpdateObject.do";
	var url=id?updateUrl:saveUrl;
	$.post(url,params,function(result){
		if(result.state==1){
			alert(result.message);
			doBack();
		}else{
			alert(result.message);
		}
	});
	
}

function getEditFormData(){
	var params={
		"name":$("#typeNameId").val(),
		"parentId":$("#editTypeForm").data("parentId"),
		"sort":$("#typeSortId").val(),
		"note":$("#typeNoteId").val()
	};
	return params;
}

/**设置上级分类信息*/
function doSetSelectedType(){
	//1.获得选中的数据信息
	var nodes=zTree.getSelectedNodes();
	//2.将数据信息填充再form表单中
	//console.log(nodes[0].id);
	$("#parentNameId").val(nodes[0].name);
	$("#editTypeForm").data("parentId",nodes[0].id);
	//3.隐藏zTree对象
	doHideZtree();
}

function loadZtreeNodes(){
	//显示ztree窗口
	$("#typeLayer").css("display","block");
	//异步加载数据
	var url="type/doFindZtreeNodes.do"
	$.getJSON(url,function(result){
		if(result.state==1){
			//jquery.zTree.js
			zTree=$.fn.zTree.init($("#typeTree"),setting,result.data);
		}else{
			alert(result.message);
		}
	});
}

function doHideZtree(){
	//隐藏ztree窗口
	$("#typeLayer").css("display","none");
}

function doBack(){
	doClearData();
	var url="type/listUI.do";
	$(".content").load(url);
}

function doClearData(){
	$(".dynamicClear").val('');
	$(".content").removeData("id");
	$("#editTypeForm").removeData("parentId");
}
