<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function ref(){
		location.href='RefererServlet';
	}
</script>
</head>
<body>
	<p><a href="RefererServlet">链接</a></p>
	<p><a href="javascript:location.href='RefererServlet';">链接</a></p>
	<p><a href="javascript:void()';" onclick="ref()">链接</a></p>
	
</body>
</html>