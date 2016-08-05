<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%
	String path = request.getContextPath();
	String webPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<title>演示页面</title>
<link rel='icon' href='<%=path%>/img/title.ico' type='image/x-ico'/> 
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="<%=path%>/jscss/bootstrap/css/bootstrap.min.css">
<!-- 所有网页共同需要的样式 ,必须放在三方样式后面-->
<link rel="stylesheet" href="<%=path%>/jscss/css/global.css">
<link rel="stylesheet" href="<%=path%>/jscss/css/demo.css">
<script type="text/javascript"
	src="<%=path%>/jscss/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/jscss/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/jscss/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=path%>/jscss/js/util.js"></script>
</head>

<body>
	<!-- 默认不显示，只有人脸单张检测才显示 -->
	<div id="showcontain" class="showcontain">
		<div id="demoimageshow" class="demoimageshow">
			<ul id="ulli">

			</ul>
		</div>
	</div>
	<div class="demo-contain">
		<div class="demo-panel">
			<div class="panel-body-left">
				<div class="div-menu">
					<div class="showmenu">
						<ul class="nav nav-pills nav-stacked">

							<li id="carplate-detect" role="presentation"><a
								href="javascript:;" class="list-group-item">车牌识别</a></li>
							
						</ul>
					</div>
					<div class="illustrate">
						<h5 class="danlan">演示说明</h5>
						<p>可以选择示例图片、粘贴图片URL和上传本地图片进行图片识别，拍照测试请使用谷歌或者火狐内核的浏览器哟！</p>
					</div>
				</div>
			</div>
			<div class="panel-body-center">
				<div id="handel-face-detect" class="handel" style="display: none">
					<div class="input-group" id="input-group">
						<input id="imageurl" type="text" class="form-control"
							placeholder="URL"> <span class="input-group-btn">
							<button id="openUrl" class="btn btn-default" type="button" title="图片URL"
								onclick="openurl('imageurl','imagecanvas')">
								<span class="glyphicon glyphicon-eject" aria-hidden="true"></span>
							</button> <!-- 使用模态框 bootstrap -->
							<button id="openVideo" type="button" class="btn btn-default cameraimage"
								onclick="openvideo('imagecanvas')" title="打开摄像头" data-toggle="modal"
								data-target="#myModal">
								<span class="glyphicon glyphicon-camera" aria-hidden="true"></span>
							</button>
							<button id="openFile" type="button" class="btn btn-default" title="文件打开"
								onclick="openFile('imagecanvas')">
								<span class="glyphicon glyphicon-folder-open" aria-hidden="true">
								</span>
							</button>
							<button id="imageShow" type="button" class="btn btn-default" title="展示示例图片"
								onclick="imageShow('yes')">
								<span id="whyimage" class="glyphicon glyphicon-resize-full" aria-hidden="true">
								</span>
							</button>
						</span>
					</div>
					<div class="image-show">
						<canvas id="imagecanvas" width="410" height="410">
						</canvas>
					</div>
					<!-- display：不占用，使用 $("#inputimage").click();触发事件 多文件 multiple="multiple"-->
						<input type="file" id="inputimage" name="inputimage"
							accept="image/*" 
							style="display: none;" >
				</div>
			</div>
			<div class="panel-body-right">
				<div class="div-response">
					<div class="response" style="height: 80px;">
						<div class="danlan">REST URL:</div>
						<pre class="responsepre" id="preresturl"></pre>
					</div>
					<div class="response" style="height: 360px; margin-top: 13px;">
						<div class="danlan">RESPONSE JSON:</div>
						<pre id="prejsonface" class="responsepre">
					</pre>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal 模态框 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">获取相机图片</h4>
				</div>
				<!-- valign垂直 -->
				<div class="modal-body" align="center">
					<div id="contentHolder" style="display: block;">
						<video id="video" width="320" height="240" autoplay>
						</video>
						<!--隐藏画板，要通过画板获取图像-->
						<canvas style="display: none;" id="canvas" width="320"
							height="240"></canvas>
					</div>
				</div>
				<div class="modal-footer" align="center">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="takepicture"
						 data-dismiss="modal">获取</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		/* 此处是加载图片 */
		function ulli(){
			var temp =$("#ulli");
			var htmlL="<li><a href=\"javascript:;\"> <img height=\"118\" width=\"118\" src=\""+contextPath+"/img/plateimg/";
			var htmlR=".jpg\"></a></li>";
			for(var i=1;i<=20;i++){
				var html = htmlL+i+htmlR;
				temp.append(html);
			}
		}
		$(function() {

			//加载图片示例
			ulli();

			//默认选择证件识别，当前也只有一个
			$("#carplate-detect").addClass("active");
			$("#handel-face-detect").attr("style", 'display: block;');
			var clickdom="openurl('imageurl','imagecanvas',false,'carplate')";
			$("#openUrl").attr("onclick", clickdom);
			clickdom="openFile('imagecanvas','carplate')";
			$("#openFile").attr("onclick", clickdom);
			clickdom="openvideo('imagecanvas','carplate')";
			$("#openVideo").attr("onclick", clickdom);

			//判断浏览器内核，是否显示照相机图标，目前火狐和谷歌内核支持
			var kernel = navigator.userAgent.toLowerCase();
			var mozilla = /firefox/.test(kernel);
			var webkit = /webkit/.test(kernel);
			var cameraimage = $(".cameraimage");
			//火狐firfox和谷歌chrome浏览器判定
			if (mozilla || webkit) {
				//显示摄像头按钮
				cameraimage.attr("style", 'display: inline-block;');
			} else {
				//不显示摄像头按钮
				cameraimage.attr("style", 'display: none;');
			}
			
		});
		
		function openFile(canvasid,fun) {
			//文件打开调用事件
			var clickdom="fileOpen(\'"+canvasid+"\',\'"+fun+"\')";
			$("#inputimage").attr('onchange',clickdom);
			$("#inputimage").click();
		}
		//获取video图片功能，canvasid判断是否保存，以及其他功能，传入fun时判断执行人脸检测还是证件识别
		function fileOpen(canvasid,fun) {
			// 执行上传文件操作的函数
			var file = $("#inputimage")[0].files[0];
			if (!/image\/\w+/.test(file.type)) {
				alert("请选择图片！");
				return false;
			}
			var reader = new FileReader();
			// 将文件以Data URL形式读入页面
			reader.readAsDataURL(file);
			reader.onload = function(e) {
				//此处得到也是base64图片
				var src = this.result;
				var img = new Image();
				img.src = src;

				//使用ajax传递到后台服务数据
				var actionUrl;
				var loadingObj = new loading(canvasid);
						
				if(fun=="carplate")
					actionUrl = contextPath+"/handle/carPlateDectectFile";
				else
					actionUrl = contextPath+"/handle/carPlateDectectFile";
				//此处采用html5新特性formData上传文件,如果不支持则用$.ajaxFileUpload
				if(window.FormData){
				var formData = new FormData();
				formData.append('inputimage', file);//inputimage是后台获取的参数
				$.ajax({
					url : actionUrl,
					type : 'POST',
					dataType : "text",
					data : formData,
					cache : false,
					processData: false,
					contentType : false,
					beforeSend:loadingObj.show(),
					success : function(data, status) {
						loadingObj.hide();
						if (data){
							data = $.parseJSON(data);//将字符串变为json格式
							jsondrawshow("prejsonface",data,"preresturl",basePath+actionUrl,canvasid,img);
						}	
					},
					error : function(data, status, e) {
						loadingObj.hide();
						var prejsonface = $("#prejsonface")[0];
						var preresturl = $("#preresturl")[0];
						prejsonface.innerHTML = "服务器连接错误"; 
						preresturl.innerHTML = basePath+actionUrl;
					}
				});
				}
				//是post方法
				else{ $.ajaxFileUpload({
					// 处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
					url : actionUrl,
					secureuri : false, // 是否启用安全提交,默认为false
					fileElementId : 'inputimage', // 文件选择框的id属性
					dataType : 'text', // 服务器返回的格式,可以是json或xml等,
					beforeSend:loadingObj.show(),
					success : function(data, status) { // 服务器响应成功时的处理函数
						loadingObj.hide();
						if (data){
							data = $.parseJSON(data);//将字符串变为json格式
							jsondrawshow("prejsonface",data,"preresturl",basePath+actionUrl,canvasid,img);
						}
					},
					error : function(data, status, e) { // 服务器响应失败时的处理函数，data为空时也是失败
						loadingObj.hide();
						var prejsonface = $("#prejsonface")[0];
						var preresturl = $("#preresturl")[0];
						preresturl.innerHTML=basePath+actionUrl;
						prejsonface.innerHTML = "服务器连接错误";
					}

				}); 
				}
			}
		}
		/*用于控制相机打开和关闭，暂时只能是全局变量 */
		var localMediaStream;
		//canvasid判断是否保存，以及其他功能，传入fun时判断执行人脸检测还是证件识别，默认是人脸检测
		function openvideo(canvasid,fun) {
			// Grab elements, create settings, etc.
			var video = document.getElementById("video"), videoObj = {
				"video" : true
			}, errBack = function(error) {
				console.log("Video capture error: ", error.code);
			};

			// Put video listeners into place
			if (navigator.getUserMedia) { // Standard
				navigator.getUserMedia(videoObj, function(stream) {
					localMediaStream = stream;
					video.src = stream;
					video.play();
				}, errBack);
			} else if (navigator.webkitGetUserMedia) { // WebKit-prefixed
				navigator.webkitGetUserMedia(videoObj, function(stream) {
					localMediaStream = stream;
					video.src = window.URL.createObjectURL(stream);
					video.play();
				}, errBack);
			} else if (navigator.mozGetUserMedia) { // Firefox-prefixed
				navigator.mozGetUserMedia(videoObj, function(stream) {
					localMediaStream = stream;
					video.src = window.URL.createObjectURL(stream);
					video.play();
				}, errBack);
			}
			//改变照相机获取的图片展示位置
			 var clickdom="takepicture(\'"+canvasid+"\',\'"+fun+"\')";
			 $("#takepicture").attr('onclick',clickdom);
		}
		//获取video图片功能，canvasid判断是否保存，以及其他功能，传入fun时判断执行
		function takepicture(canvasid,fun) {
			var canvas = document.getElementById("canvas");
			var context = canvas.getContext("2d");
			var video = document.getElementById("video");
			context.drawImage(video, 0, 0, canvas.width, canvas.height);
			
			// returns "data:image/jpeg;base64,/9j/4AAQSkZJRgABA...
			var imgData = canvas.toDataURL("image/jpeg");
			var img = new Image();
			img.src = imgData;

			var actionUrl;
			if(fun=="carplate")
				actionUrl = contextPath+"/handle/carPlateDectect";
			else
				actionUrl = contextPath+"/handle/carPlateDectect";
			var loadingObj = new loading(canvasid);
			$.ajax({
				url : actionUrl,
				type : 'POST',//GET方法不能传递，头比较大
				dataType : "text",
				data : {
					"img" : imgData
				},
				cache : false,
				//contentType : "application/json", //text/html
				beforeSend:loadingObj.show(),
				success : function(data, status) {
					loadingObj.hide();
					if (data){
						data = $.parseJSON(data);//将字符串变为json格式
						jsondrawshow("prejsonface",data,"preresturl",basePath+actionUrl,canvasid,img);
					}	
				},
				error : function(data, status, e) {
					loadingObj.hide();
					var prejsonface = $("#prejsonface")[0];
					var preresturl = $("#preresturl")[0];
					prejsonface.innerHTML = "服务器连接错误"; 
					preresturl.innerHTML = basePath+actionUrl;
				}
			});
		}
		//booststrap模态框隐藏后触发事件
		$('#myModal').on('hidden.bs.modal', function(e) {
			//停止流获取，及关闭摄像头
    		stopMedia();
			video.pause();
			video.src = "";
		})
		//flag判断传进来的参数是url还是input框id
		function openurl(imageurlid, canvasid, flag,fun) {		
			if (!flag) {
				
			}else{
				var imageurl=imageurlid;
			}
			var img = new Image();
			img.src = imageurl;
			var actionUrl;
			if(fun=="carplate"){
				actionUrl = contextPath+"/handle/carPlateDectect?url=" + imageurl;
			}
			
			var loadingObj = new loading(canvasid);
			$.ajax({
				url : actionUrl,
				type : 'GET',
				dataType : "text",
				cache : false,
				//contentType : "application/json",
				beforeSend:loadingObj.show(),
				success : function(data, status) {
					loadingObj.hide();
					if (data){
						data = $.parseJSON(data);//将字符串变为json格式
						jsondrawshow("prejsonface",data,"preresturl",basePath+actionUrl,canvasid,img);
					}
				},
				error : function(data, status, e) {
					loadingObj.hide();
					var prejsonface = $("#prejsonface")[0];
					var preresturl = $("#preresturl")[0];
					prejsonface.innerHTML = "服务器连接错误";
					preresturl.innerHTML = basePath+actionUrl;
				}

			});
		}
		/* 图片点击事件 */
		var urlid="carplate-detect";//保存当前功能指定，便于控制
		$("#demoimageshow").on("click","li",function(){
			var url=basePath+$(this).find("img").attr("src");
			if(urlid.indexOf("carplate-detect")>-1){
				openurl(url,'imagecanvas',true,'carplate');
			}
			else{
				openurl(url,'imagecanvas',true);
			}
		});
		
		//摄像头停止获取视频流
		function stopMedia(){
			if (localMediaStream) {
		        if (localMediaStream.getVideoTracks) {
		            // get video track to call stop on it
		            var tracks = localMediaStream.getVideoTracks();
		            if (tracks && tracks[0] && tracks[0].stop) tracks[0].stop();
		        }
		        else if (localMediaStream.stop) {
		            // deprecated, may be removed in future
		            localMediaStream.stop();
		        }
		        localMediaStream = null;
		    }
		}
		//清空画板和右侧标签数据 clear canvas
		function clear() {
			var prejsonface = $("#prejsonface")[0];
			var preresturl = $("#preresturl")[0];
			prejsonface.innerHTML = "";
			preresturl.innerHTML = "";
			var p=['imagecanvas']; //原来有好几个画板，此处暂时不再修改
			var canvas;
			var ctx;
			for(var i=0;i<1;i++){
				canvas = document.getElementById(p[i]);
				ctx = canvas.getContext('2d');
				ctx.clearRect(0, 0, canvas.width, canvas.height);
			}
		}
		/*示例图片是否展示*/
		function imageShow(fun){
			var clickdom;
			//.slideDown 和  .slideUp的效果 .slideToggle
			// $(".demoimageshow").slideToggle('1000');
			if(fun=="yes"){
				$(".demoimageshow").slideDown('1000');
				$("#imageShow").attr("title", '隐藏示例图片');
				$("#whyimage").attr("class","glyphicon glyphicon-resize-small");
				clickdom="imageShow('no')";
			}
			else {
				$(".demoimageshow").slideUp('1000');
				$("#imageShow").attr("title", '展示示例图片');
				$("#whyimage").attr("class","glyphicon glyphicon-resize-full");
				clickdom="imageShow('yes')";
			}
			$("#imageShow").attr("onclick", clickdom);
		}
		/* 导航栏点击事，暂时只有车牌 */
		$(".showmenu").on("click","li",function(){
			clear();//清空所有面板
			urlid=($(this).attr("id"));
			$(this).addClass("active").siblings().removeClass("active");
			var clickdom;
			if(urlid.indexOf("carplate-detect")>-1){
				//车牌识别页面和功能控制
				$("#carplate-detect").addClass("active");
				//使用人脸检测框
				$("#handel-face-detect").attr("style", 'display: block;');

				$("#input-group").attr("style", 'display:  table;');
				$("#btn-group").attr("style", 'display: none;');
				
				clickdom="openurl('imageurl','imagecanvas',false,'carplate')";
				$("#openUrl").attr("onclick", clickdom);
				
				clickdom="openFile('imagecanvas','carplate')";
				$("#openFile").attr("onclick", clickdom);
				
				clickdom="openvideo('imagecanvas','carplate')";
				$("#openVideo").attr("onclick", clickdom);
			}
		
		});
	</script>
</body>
</html>