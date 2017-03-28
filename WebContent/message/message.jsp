<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function ready(){
		if(typeof(EventSource)){
			var ev = new EventSource("/web_pro_lodop/sendServlet");
			ev.onopen=function(){
				
			};
			ev.onerror=function(){
				alert("出现错误");
			};
			ev.onmessage=function(event){
				document.getElementById("XIAOMING").innerHTML=document.getElementById("XIAOMING").innerHTML+"<br/>"+event.data;
			};
		}else{
			alert("不支持EventMessage");
		}
	}
</script>
</head>
<body onload="ready()">
	<DIV ID="XIAOMING"></DIV>
</body>
</html>