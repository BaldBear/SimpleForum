<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/../../../jspHead.jsp" %>
</head>
<body>
	<form action="<%=basePath %>saveUeditorContent" method="post">
		
        <!-- 加载编辑器的容器 -->
        <div style="padding: 0px;margin: 0px;width: 100%;height: 100%;" >
	        <script id="container" name="content" type="text/plain">
            
        	</script>
        </div>
        
        <button type="submit">保存</button>
    </form>

        <!-- 配置文件 -->
    <script type="text/javascript" src="<%=basePath %>uedit/js/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="<%=basePath %>uedit/js/ueditor.all.js"></script>
    <!-- 实例化编辑器 -->
    <script type="text/javascript">
   		 var editor = UE.getEditor('container');
    </script>
    <!-- end富文本 -->
</body>
</html>