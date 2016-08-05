function jsondrawshow(preid, data, urlid, url, canvasid, img) {
	var prejsonface = document.getElementById(preid);
	var preresturl = document.getElementById(urlid);
	if (data.message == "no_image") {
		drawimage(canvasid);
	} else if (data.message.indexOf("_face") > -1) {
		// 此处是用于控制人脸和人脸特征点画板控制，可以换成其他方式
		if (data.face)
			drawimage(canvasid, img, data.face);
		else
			drawimage(canvasid, img, data);
	} else if (data.message.indexOf("yes") > -1) {
		drawimage(canvasid, img);
	} else if (data.message.indexOf("_carPlate") > -1) {
		drawimage(canvasid, img);
	}
	preresturl.innerHTML = url;
	prejsonface.innerHTML = formatJson(data);
}
function jsondrawshowcompare(preid, data, urlid, url, canvasid1, img1,
		canvasid2, img2) {
	var prejsonface = document.getElementById(preid);
	var preresturl = document.getElementById(urlid);
	if (data.returnSimilarity.message != "no_result") {
		drawimage(canvasid1, img1, data.face);
		drawimage(canvasid2, img2, data.faceto);
	}
	preresturl.innerHTML = url;
	prejsonface.innerHTML = formatJson(data.returnSimilarity);
}
// 取得缩放比例
function getScaleRatio(rectSrc, rectDest) {
	var ratioDest = rectDest.width / rectDest.height;
	var ratioSrc = rectSrc.width / rectSrc.height;
	if (ratioSrc < ratioDest)
		return rectDest.height / rectSrc.height;
	else
		return rectDest.width / rectSrc.width;
}
// 画板绘图
function drawimage(id, img, data) {
	var canvas = document.getElementById(id);
	if (!canvas)
		return;
	var ctx = canvas.getContext('2d');

	var canvas_w = canvas.width;// 画布的宽
	var canvas_h = canvas.height;// 画布的高
	// 清空画布，保持原来颜色
	ctx.clearRect(0, 0, canvas.width, canvas.height);
	// ctx.beginPath();
	if (!img)
		return;
	ctx.lineWidth = 2;
	ctx.strokeStyle = 'rgba(0,255,255,0.5)';
	var ratio = getScaleRatio({
		width : img.width,
		height : img.height
	}, {
		width : canvas_w,
		height : canvas_h
	});
	var img_w = img.width * ratio;// 缩放后图片的宽
	var img_h = img.height * ratio;// 缩放后图片的高
	ctx.drawImage(img, (canvas_w - img_w) / 2, (canvas_h - img_h) / 2, img_w,
			img_h);
	if (!data)
		// 参考别处代码
		return;
	if (data.landmarks) {// 人脸特征点
		var p = new Array(2);
		ctx.fillStyle = 'rgba(32,219,154,.8)';// 填充颜色：红色，半透明，默认是黑色
		$.each(data.landmarks, function(k, v) {
			for (var index = 0; index < 154; index += 2) {
				p[0] = v[index] * ratio + (canvas_w - img_w) / 2;
				p[1] = v[index + 1] * ratio + (canvas_h - img_h) / 2;
				ctx.beginPath();
				ctx.arc(p[0], p[1], 2, 0, 360, false);
				ctx.fill();// 画实心圆
			}
		});

	} else {// 人脸画框
		$.each(data, function(k, v) {
			var x = v.face_x * ratio + (canvas_w - img_w) / 2;// 人脸框x坐标
			var y = v.face_y * ratio + (canvas_h - img_h) / 2;// 人脸框y坐标
			var width = v.face_width * ratio;
			var height = v.face_height * ratio;
			ctx.strokeRect(x, y, width, height);

			// 画左眼
			/*
			 * ctx.beginPath(); ctx.arc(v.lefteye_x * ratio + (canvas_w - img_w) /
			 * 2, v.lefteye_y ratio + (canvas_h - img_h) / 2, 3, 0, 2 *
			 * Math.PI); ctx.stroke();
			 */

			// 画右眼
			/*
			 * ctx.beginPath(); ctx.arc(v.righteye_x * ratio + (canvas_w -
			 * img_w) / 2, v.righteye_y ratio + (canvas_h - img_h) / 2, 3, 0, 2 *
			 * Math.PI); ctx.stroke();
			 */

			// 画嘴
			/*
			 * ctx.beginPath(); ctx.arc(v.mouth_x * ratio + (canvas_w - img_w) /
			 * 2, v.mouth_y ratio + (canvas_h - img_h) / 2, 3, 0, 2 * Math.PI);
			 * ctx.stroke();
			 */
		});
	}
}
// jsonstring 格式化显示处理
// 从下面这个网站例子
// Example usage: http://jsfiddle.net/q2gnX/
function formatJson(json, options) {
	var reg = null, formatted = '', pad = 0, PADDING = '    '; // one can also
	// use '\t' or a
	// different
	// number of
	// spaces

	// optional settings
	options = options || {};
	// remove newline where '{' or '[' follows ':'
	options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true
			: false;
	// use a space after a colon
	options.spaceAfterColon = (options.spaceAfterColon === false) ? false
			: true;

	// begin formatting...
	if (typeof json !== 'string') {
		// make sure we start with the JSON as a string
		json = JSON.stringify(json);
	} else {
		// is already a string, so parse and re-stringify in order to remove
		// extra whitespace
		json = JSON.parse(json);
		json = JSON.stringify(json);
	}

	// add newline before and after curly braces
	reg = /([\{\}])/g;
	json = json.replace(reg, '\r\n$1\r\n');

	// add newline before and after square brackets
	reg = /([\[\]])/g;
	json = json.replace(reg, '\r\n$1\r\n');

	// add newline after comma
	reg = /(\,)/g;
	json = json.replace(reg, '$1\r\n');

	// remove multiple newlines
	reg = /(\r\n\r\n)/g;
	json = json.replace(reg, '\r\n');

	// remove newlines before commas
	reg = /\r\n\,/g;
	json = json.replace(reg, ',');

	// optional formatting...
	if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
		reg = /\:\r\n\{/g;
		json = json.replace(reg, ':{');
		reg = /\:\r\n\[/g;
		json = json.replace(reg, ':[');
	}
	if (options.spaceAfterColon) {
		reg = /\:/g;
		json = json.replace(reg, ': ');
	}

	$.each(json.split('\r\n'), function(index, node) {
		var i = 0, indent = 0, padding = '';

		if (node.match(/\{$/) || node.match(/\[$/)) {
			indent = 1;
		} else if (node.match(/\}/) || node.match(/\]/)) {
			if (pad !== 0) {
				pad -= 1;
			}
		} else {
			indent = 0;
		}

		for (i = 0; i < pad; i++) {
			padding += PADDING;
		}

		formatted += padding + node + '\r\n';
		pad += indent;
	});

	return formatted;
}
// url图片转化为base64
function convertImgToBase64(url, callback, outputFormat) {
	var canvas = document.createElement('CANVAS'), ctx = canvas
			.getContext('2d'), img = new Image;
	img.crossOrigin = 'Anonymous';
	img.onload = function() {
		canvas.height = img.height;
		canvas.width = img.width;
		ctx.drawImage(img, 0, 0);
		var dataURL = canvas.toDataURL(outputFormat || 'image/jpeg');
		callback.call(this, dataURL);
		canvas = null;
	};
	img.src = url;
}

// loading效果
function loading(canvasid, options) {
	this.canvas = document.getElementById(canvasid);
	if (options) {
		this.radius = options.radius || 12;
		this.circleLineWidth = options.circleLineWidth || 4;
		this.circleColor = options.circleColor || 'lightgray';
		this.moveArcColor = options.moveArcColor || 'gray';
	} else {
		this.radius = 50;
		this.circelLineWidth = 40;
		this.circleColor = 'lightgray';
		this.moveArcColor = 'gray';
	}
}
loading.prototype = {
	show : function() {
		var canvas = this.canvas;
		if (!canvas.getContext)
			return;
		if (canvas.__loading)
			return;
		canvas.__loading = this;
		var ctx = canvas.getContext('2d');
		var radius = this.radius;
		var me = this;
		var rotatorAngle = Math.PI * 1.5;
		var step = Math.PI / 6;
		canvas.loadingInterval = setInterval(function() {
			ctx.clearRect(0, 0, canvas.width, canvas.height);
			var lineWidth = me.circleLineWidth;
			var center = {
				x : canvas.width / 2,
				y : canvas.height / 2
			};
			ctx.beginPath();
			ctx.lineWidth = lineWidth;
			ctx.strokeStyle = me.circleColor;
			ctx.arc(center.x, center.y, radius, 0, Math.PI * 2);
			ctx.closePath();
			ctx.stroke();
			// 在圆圈上面画小圆
			ctx.beginPath();
			ctx.strokeStyle = me.moveArcColor;
			ctx.arc(center.x, center.y, radius, rotatorAngle, rotatorAngle
					+ Math.PI * .45);
			ctx.stroke();
			rotatorAngle += step;

		}, 50);
	},
	hide : function() {
		var canvas = this.canvas;
		canvas.__loading = false;
		if (canvas.loadingInterval) {
			window.clearInterval(canvas.loadingInterval);
		}
		var ctx = canvas.getContext('2d');
		if (ctx)
			ctx.clearRect(0, 0, canvas.width, canvas.height);
	}
};

//上下文js获取，全局变量保存
var localObj = window.location;
var contextPath = "/" + localObj.pathname.split("/")[1];
var basePath = localObj.protocol + "//" + localObj.host;
var baseContextPath = basePath + contextPath;