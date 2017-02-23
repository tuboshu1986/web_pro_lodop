/**
 * 2014-07-02 李博禹
 * This is the method for easyui-datagrid to move or hide the columns extended by lee.
 */
$.extend($.fn.datagrid.methods,{
	columnMoving:function(jq){
		return jq.each(function(){
			var grid = this;
			
			var directionDiv = $("<div></div>");
			
			directionDiv.hide();
			
			$("body").append(directionDiv);
			
			$(grid).datagrid("getPanel")
					.find(".datagrid-header td[field]:not(td[field='ckb'])").draggable({
				revert:true,
				cursor:"move",
				deltaX:10,
				deltaY:10,
				edge:10,
				proxy:function(source){
					var proxyEl = $("<div></div>");
					
					proxyEl.addClass("dg-proxy dg-proxy-error");
					
					proxyEl.text($(source).text());
					
					proxyEl.appendTo($("body"));
					
					return proxyEl;
				}
			}).droppable({
				accept:".datagrid-header td[field]",
				onDragOver:function(e,source){
					$(source).draggable("proxy").removeClass("dg-proxy-error").addClass("dg-proxy-right");
					
					$(".dg-hide-div").hide();
					
					var thisIndex = $(this).index();
					var sourceIndex = $(source).index();
					
					var className = null;
					
					var height = null;
					
					var thisOffset = null;
					
					var left = null;
					var top = null;
					
					height = $(this).height();
					
					if(sourceIndex > thisIndex){
						className = "dg-move-prev";

						thisOffset = $(this).offset();
						
						left = thisOffset.left;
						top = thisOffset.top;
					}else{
						className = "dg-move-next";
						
						if(thisIndex == $(this).parent().children(":last").index()){
							thisOffset = $(this).offset();
							
							left = thisOffset.left + $(this).width() - directionDiv.width();
							top = thisOffset.top;
						}else{
							thisOffset = $(this).next().offset();
							
							left = thisOffset.left - directionDiv.width();
							top = thisOffset.top;
						}
					}
					
					directionDiv.removeClass().addClass(className);
					directionDiv.css({height:height, left:left, top:top});
					directionDiv.show();
				},
				onDragLeave:function(e,source){
					$(source).draggable("proxy").removeClass("dg-proxy-right").addClass("dg-proxy-error");
					
					directionDiv.hide();
				},
				onDrop:function(e,source){
					directionDiv.remove();
					
					var thisIndex = $(this).index();
					var sourceIndex = $(source).index();
					
					var sourceCol = new Array();
					
					$(source).remove();
					$.each($(grid).datagrid("getPanel")
									.find(".datagrid-body tr"),function(index,obj){
						var sourceTd = $(obj).children("td:eq(" + sourceIndex + ")");
						
						sourceCol.push(sourceTd);
						
						sourceTd.remove();
					});
					
					var prev = sourceIndex > thisIndex;
					
					thisIndex = $(this).index();
					
					if(prev){
						$(this).before($(source));
					}else{
						$(this).after($(source));
					}
					
					$.each($(grid).datagrid("getPanel")
									.find(".datagrid-body tr"),function(index,obj){
						var thisTd = $(obj).children("td:eq(" + thisIndex + ")");
						
						if(prev){
							thisTd.before(sourceCol[index]);
						}else{
							thisTd.after(sourceCol[index]);
						}
					});
					
					$(grid).datagrid("columnMoving").datagrid("columnHiding");
				}
			});
		});
	},
	columnHiding:function(jq){
		return jq.each(function(){
			var grid = this;
			
			var tds = $(grid).datagrid("getPanel").find(".datagrid-header td[field]:not(td[field='ckb'])");
			
			var downDiv = null;
			
			if($(".dg-hide-div").length == 0){
				downDiv = $("<div></div>");
				downDiv.addClass("dg-hide-div");
				downDiv.hide();
				
				$("body").append(downDiv);
			}else{
				downDiv = $(".dg-hide-div");
			}
			
			downDiv.click(function(){
				tbDiv.show();
			}).mouseout(function(){
				var tbVisible = tbDiv.is(":visible");
				
				if(!tbVisible){
					$(this).hide();
				}
			});
			
			var tbDiv = null;
			
			if($(".dg-hide-tb").length == 0){
				tbDiv = $("<div><table></table></div>");
				tbDiv.addClass("dg-hide-tb");
				tbDiv.hide();
				
				$("body").append(tbDiv);
			}else{
				tbDiv = $(".dg-hide-tb");
				tbDiv.children("table").children().remove();
			}
			
			var trs = "";
			
			var columns = ($(grid).datagrid("options").columns)[0];
			
			$.each(columns,function(index, obj){
				if(index > 0){
					trs += "<tr>";
					
					trs += "<td><input type='checkbox' checked='checked'></td><td id=" + obj.field + ">" + obj.title + "</td>";
					
					trs += "</tr>";
				}
			});
			
			tbDiv.children().append($(trs));
			
			tbDiv.mouseout(function(e){
				var minX = $(this).offset().left;
				
				var curMouseX = e.pageX;
				
				var maxX = $(this).offset().left + $(this).width();
				
				var minY = $(this).offset().top;
				
				var curMouseY = e.pageY;
				
				var maxY = $(this).offset().top + $(this).height();
				
				var ifOverThis = (curMouseX >= minX && curMouseX < maxX) 
									&& (curMouseY >= minY && curMouseY <= maxY);
				
				if(!ifOverThis){
					downDiv.hide();
					$(this).hide();
				}
			});
			
			tbDiv.children().find("input[type='checkbox']").click(function(){
				var checked = $(this).is(":checked");
				
				var visibleTds = $(grid).datagrid("getPanel")
										.find(".datagrid-header td[field]:visible").length - 1;

				if(1 == visibleTds && !checked){
					return false;
				}
				
				var field = $(this).parent().next().attr("id");
				
				if(checked){
					$(grid).datagrid("showColumn", field);
				}else{
					$(grid).datagrid("hideColumn", field);
				}
			});
			
			tds.mouseover(function(){
				tbDiv.hide();
				
				var thisOffset = $(this).offset();
				
				var height = $(this).height();
				
				var left = null;
				
				if($(this).index() == $(this).parent().children(":last").index()){
					left = thisOffset.left;
				}else{
					//left = thisOffset.left + $(this).width() - downDiv.width();
					
					//fix the conflict between hide and resize. 2015-02-28
					//more improvement is needed.
					left = thisOffset.left + $(this).width() - downDiv.width() - 2;
				}
				
				var top = thisOffset.top;
				
				downDiv.css({height:height, left:left, top:top});
				tbDiv.css({left:left,top:top+height+1});
				
				downDiv.show();
			}).mouseout(function(e){
				
				var minY = $(this).offset().top;
				
				var curMouseY = e.pageY;
				
				var maxY = $(this).offset().top + $(this).height();
				
				var minX = null;
				
				var curMouseX = e.pageX;
				
				var maxX = null;
				
				if($(this).index() == $(this).parent().children(":last").index()){
					minX = $(this).offset().left;
					maxX = $(this).offset().left + downDiv.width();
				}else{
					minX = $(this).offset().left + $(this).width() - downDiv.width();
					maxX = $(this).next().offset().left;
				}
				
				var ifOverThis = (curMouseX >= minX && curMouseX <= maxX) 
									&& (curMouseY >= minY && curMouseY <= maxY);
				
				if(!ifOverThis){
					downDiv.hide();
				}
			});
			
		});
	}
});


/**
 * 2014-08-23 李博禹
 * This is the extension for rules of easyui-validatebox to validate the value that user inputs.
 *
 * Default rules: email: eg. validType:'email',
 * 				    url: eg. validType:'url',
 *               length: eg. validType:'length[3,5]',
 *               remote: eg. validType:{remote:['actionUrl','paramName']}
 *             combobox: eg. validType:{combobox:['param']}
 *             						   'combobox[\'param\']'
 * 
 * multiply validType: eg. validType:['email','length[3,5]']
 * 					   eg. validType:['vehicleNo','combobox[\'param\']']
 *                     eg. validType:{length:[3,5],remote:['actionUrl','paramName']}
 */
$.extend($.fn.validatebox.defaults.rules,{
	minLength: {// 判断当前值最小长度
        validator: function(value, param) {
            return value.length >= param[0];
        },
        message: "最少输入 {0}个字符。"
    },
    maxLength: {// 判断当前值最小长度
        validator: function(value, param) {
            return /^\d{0,5}$/i.test(value);
        },
        message: "请输入数字，最多输入5位。"
    },
    combobox: {// 验证当前comboxbox文本框中的值是否在列表中
    	validator: function(value,param){
    		return $(param[0]) && undefined != $(param[0]).combobox("getValue");
    	},
    	message: "请选择列表中存在的条目"
    },
    equals: {// 验证两个输入值是否相等
        validator: function(value,param){
            return value == $(param[0]).val();
        },
        message: "密码不一致"
    },
    number: {// 验证数字，包含整数、小数
    	validator: function(value) {
            return /^(\d{1,3}(,\d\d\d)*(\.\d{1,3}(,\d\d\d)*)?|\d+(\.\d+))?$/i.test(value);
        },
        message: "请输入数字"
    },
    integer: {// 验证数据，必须为整数
    	validator: function(value) {
            return /^[+]?[1-9]+\d*$/i.test(value);
        },
        message: "请输入整数"
    },
    chinese: {// 验证中文
        validator: function(value) {
            return /^[\u0391-\uFFE5]+$/i.test(value);
        },
        message: "请输入中文"
    },
    english: {// 验证英语
        validator: function(value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message: "请输入英文"
    },
    name: {// 验证姓名，可以是中文或英文(正则有点有问题，需要调整)
        validator: function(value) {
            return /^[\u0391-\uFFE5]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value);
        },
        message: "请输入有效的姓名"
    },
    idCard: {// 验证身份证号码
    	validator: function(value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: "请输入有效的身份证号码"
    },
    phone: {// 验证固定电话或手机号码
    	validator: function(value) {
            return /^(13|15|18)\d{9}$/i.test(value) || /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: "请输入有效的号码,如：13688888888或020-8888888"
    },
    fixedPhone: {// 验证固定电话
        validator: function(value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: "请输入有效的固定电话,如:020-88888888"
    },
    mobilePhone: {// 验证手机号码
    	validator: function(value) {
            return /^(13|15|18)\d{9}$/i.test(value);
        },
        message: "请输入有效的手机号码,如：13688888888"
    },
    faxNo: {// 验证传真
        validator: function(value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: "请输入有效的传真号码"
    },
    ip: {// 验证IP地址
        validator: function(value) {
            return /d+.d+.d+.d+/i.test(value);
        },
        message: "请输入有效的IP地址"
    },
    vehicleNo: {// 验证车牌号
        validator: function(value){
            return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(value);
        },
        message: "请输入有效的车牌号码"
    },
    photo: {// 验证上传图片格式
    	validator: function(value) {
    		return /^[a-zA-Z]:(\\.+)(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$/.test(value);
    	},
    	message: "图片格式不正确,请选择.JPEG|.JPG|.BMP|.PNG格式的图片"
    },
    checkError: {// 用户输入错误
    	validator: function(value) {
            return /^\d{20}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: "请重新输入"
    },
    email: {// 用户输入错误
    	validator: function(value) {
            return /^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$/i.test(value);
        },
        message: "请输入正确的邮箱地址"
    },
    userName: {// 用户输入错误
    	validator: function(value) {
            return /^[a-z][a-z_0-9]{5,20}$/i.test(value);
        },
        message: "以字母开头6-20位组成"
    },
    chOrEg: {// 验证中文和英文
        validator: function(value) {
            return /^[\u4e00-\u9fa5a-zA-Z0-9]+$/i.test(value);
        },
        message: "请输入中文、英文、数字"
    }
});