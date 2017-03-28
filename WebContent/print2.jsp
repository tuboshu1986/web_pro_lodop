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

	var x=0,y=0;
	function print(){
		var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  
		//var printerIndex = LODOP.SELECT_PRINTER();
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_按网址打印");
		LODOP.SET_SHOW_MODE ("PREVIEW_IN_BROWSE",true);
		var width=794;
		var height=1123;
		var pageWidth=794+35;
		var pageHeight=1123+35;
		var url="images/bk.png";
		LODOP.set_print_pagesize(1,pageWidth+"px",pageHeight+"px",null);
		//LODOP.ADD_PRINT_BARCODE(elem.top,elem.left,elem.w,elem.h,"Code93",elem.val);
		LODOP.SET_PRINT_STYLE("Alignment",1);
		
		LODOP.ADD_PRINT_TEXT(0,0,100,50,"aaaaa");
		LODOP.ADD_PRINT_RECT(100,100,300,300,0,1);
		LODOP.SET_PRINT_STYLE("Stretch",1);
		x=parseInt(x==undefined||x==""?0:x);
		y=parseInt(y==undefined||y==""?0:y);
		LODOP.ADD_PRINT_BARCODE(100+y,100+x,300,300,"QRCode","http://szjspx.com:58081/elicenseAdmin/");
		
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
	
	function setXY(){
		x = document.getElementById("xxx").value;
		y = document.getElementById("yyy").value;
		print();
	}
	
</script>
</head>
<body>
<div>
	<div id="tools">		
		<p><button onclick="print()">打印预览</button></p>
		<p>
			x:<input type="text" id="xxx"/>
			y:<input type="text" id="yyy"/>
			<button onclick="setXY()">设置</button>
		</p>
	</div>
</div>

<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
	<embed id="LODOP_EM" type="application/x-print-lodop" width="800" height="1000" pluginspage="install_lodop.exe"></embed>
</object> 

</body>
</html>
