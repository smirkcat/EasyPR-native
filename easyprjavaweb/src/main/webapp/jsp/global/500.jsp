<%@ page language="java" contentType="text/html; charset=UTF-8"
	isErrorPage="true"%>
<%
	response.setStatus(HttpServletResponse.SC_OK);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<%
	String path = request.getContextPath();
	String webPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>

<title>500</title>

<!-- 需要引用的CSS -->
<link rel="stylesheet"
	href="<%=webPath%>/jscss/bootstrap/css/bootstrap.min.css">
<style type="text/css">
.content {
	margin-top: 6%;
	text-align: center;
}

ul {
	margin-top: 20px;
}

li {
	line-height: 30px;
	margin-left: 190px;
	list-style: none;
}

a {
	color: #027bff;
	text-decoration: none;
}

a:hover {
	color: blue;
	text-decoration: underline;
}
</style>
</head>
<body>
	<!-- 页面结构 -->
	<div class="content">
		<img src="<%=webPath%>/img/erro/500.png" />
		<ul>
			<li><a href="javascript:window.location.reload();"><i
					class="fa fa-angle-double-left"></i>刷新一下</a></li>
			<li><a href="javascript:history.go(-1);"><i
					class="fa fa-angle-double-left"></i>返回上一页</a></li>
		</ul>
	</div>

	<!-- 需要添加的JS -->
</body>
</html>