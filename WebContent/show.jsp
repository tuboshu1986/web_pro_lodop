<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>

<link rel="stylesheet" type="text/css" href="js/easyui/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="js/easyui/themes/extension.css">

<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="js/easyui/jquery.easyui.extension.js"></script>
<script type="text/javascript" src="js/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/easyui/plugins/jquery.parser.js"></script>

<script type="text/javascript">
	$(function(){
		$("#dd").dialog({    
		    title:'测试',    
		    width:400,    
		    height:200,    
		    closed:true,    
		    cache:false,    
		    href:'http://www.baidu.com',    
		    modal:true,
		    zIndex:9000,
		    onClose:function(){
		    	$("#myIfram").show();
		    }
		});    

	});
	
	function openDialog(){
		$("#myIfram").hide();
		$("#dd").dialog("open");
	}
	
</script>

</head>
<body>
	<div style="position:relative;width:100%;"><!--  style="position:relative;top:100px;left:0px;" -->
		<iframe id="myIfram" width="100%" height="600px" src="pdf/1.pdf" style="position:absolute;top:0;left:0;z-index:0;border:1px solid #f00;"></iframe>
		<!-- <iframe width="100%" height="600px" style="position:absolute;top:0;left:0;z-index:1;opacity:0;" src="javascript:false;"></iframe> -->
		<!-- <div style="position:absolute;top:0;left:0;z-index:1;opacity:0.5;width:100%;height:600px;border:1px solid #f00;">aaaa</div> -->
	</div>
	<div style="position:absolute;bottom:0px;width:100%;"><button onclick="openDialog()">打开</button></div>
	<div id="dd"></div>
</body>
</html>
