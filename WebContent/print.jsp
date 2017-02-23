<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>证照列表</title>
<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="js/GetLoDop.js"></script>
<script type="text/javascript">
	$(function(){
		//alert();
	});
	
	function showLicense(licenseName){
		
		var ele='<object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="600" border="0"><param name="SRC" id="panel_object" value="hear"><embed width="100%" id="panel_embed"  height="600" src="hear"> </embed></object>';
		
		//alert(licenseName);
		$("#licensename").val(licenseName);
		
		ele=ele.replace(/hear/g,"/ssh/license_licensePrintPre.action?licenseName="+licenseName);
		$("#panel_show").html(ele);
		
		$.ajax({
			url:'/ssh/license_licensePrintPre1.action?licenseName='+licenseName,
			error:function(){
				alert("请求出错");
			},
			success:function(data){
				//var obj=eval(data);
				$("#width").val(data.width);
				$("#height").val(data.height);
			},
			dataType:"json"
		});
		
	}
	
	function print(){
		var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_按网址打印");
		var width=794;
		var height=1123;
		var pageWidth=794+35;
		var pageHeight=1123+35;
		var url="images/bk.png";
		LODOP.set_print_pagesize(1,pageWidth+"px",pageHeight+"px",null);
		LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' width='"+width+"' height='"+height+"' src='images/bk1.png' />");
		
		$.ajax({
			url:"templateServlet",
			dataType:"json",
			type:"post",
			success:function(data){
				for(var i=0;i<data.elems.length;i++){
					var elem = data.elems[i];
					if(elem.name=="ZZYWM"){
						LODOP.ADD_PRINT_BARCODE(elem.top,elem.left,elem.w,elem.h,"Code93",elem.val);
					}else if(elem.name=="ZZEWM"){
						LODOP.ADD_PRINT_BARCODE(elem.top,elem.left,elem.w,elem.h,"QRCode",elem.val);
					}else{
						LODOP.ADD_PRINT_TEXT(elem.top,elem.left,elem.w,elem.h,elem.val);
						LODOP.SET_PRINT_STYLEA(0,"FontSize",data.fontSize);
						LODOP.SET_PRINT_STYLEA(0,"FontName",data.fontName);
					}
				}
			},
			async:false
		});
		
		LODOP.SET_PRINT_STYLEA(1,"Stretch",2);
		
		//LODOP.SET_SHOW_MODE("PREVIEW_IN_BROWSE",1);
		//LODOP.SET_SHOW_MODE("HIDE_PAPER_BOARD",1);
		LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);

		LODOP.PREVIEW();//打印预览
		//LODOP.PRINT_SETUP();//打印维护
		/* 
		var designScript = LODOP.PRINT_DESIGN();//打印设计
		$("#designScript").text(designScript);
		 */
	}
	
	function print1(){
		var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_按网址打印");
		LODOP.set_print_pagesize(1,"1024px","745px",null);
		LODOP.ADD_PRINT_RECT(0,0,"100%","100%",0,1);
		//LODOP.ADD_PRINT_IMAGE(0,0,"1024px","745px","<img border='1' src='C:\\Users\\Administrator\\Pictures\\11_2.jpg' />");
		LODOP.SET_PRINT_STYLEA(1,"HOrient",3);
		LODOP.SET_PRINT_STYLEA(1,"VOrient",3);
		LODOP.PREVIEW();	
	}
	
</script>
<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
	<embed id="LODOP_EM" type="application/x-print-lodop" width="0" height="0" pluginspage="install_lodop.exe"></embed>
</object> 
</head>
<body>

<div>
	<div id="tools">		
		<button onclick="print()">打印预览</button>
		<button onclick="print1()">打印矩形</button>
		
	</div>
	<p id="designScript">
	
	</p>
</div>

</body>
</html>
