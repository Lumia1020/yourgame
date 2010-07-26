<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xml:lang="en" xmlns="http://www.w3.org/1999/xhtml" lang="en"><head>
	<base href="<%=basePath%>">
    <title>非邮发报刊业务处理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
     <link href="css/stylesheet.css" rel="stylesheet" type="text/css"></link>
     <script type="text/javascript" src="js/jquery-1.3.2.min.js">
     	
     </script>
     <script type="text/javascript">
     	$(function(){
     		$('#submit').click(function(){
     			$('#LoginForm').submit();
     		});
     	}); 
     </script>
 </head><body id="body_id" class="PrimaryME  login firefox windows account"> 
    <!-- Main Page Body -->
    <div id="wrapper" class="v2">

  <div id="owner-v2" class="container">
    <div class="inner">
      <h1 id="mmlogo" class="v2">MobileMe</h1>
      <h2>非邮发报刊业务处理系统</h2>
		<form id="LoginForm" name="LoginForm" action="<%=basePath%>page/login.action" method="post">
    	  <fieldset>
    	    <div class="row username">
      	    <label id="usernameLabel" for="username" class="placeholder focus"></label>
    	    <input id="username" name="user.userId" class="text" maxlength="128" type="text"/>
          	</div>
	    
    	    <div class="row password">
      	    <label id="passwordLabel" for="password" class="placeholder"><strong id="error" style="display: ${requestScope.msg == 'show' ? '':'none' };">用户或者密码错误.</strong></label>
    	      <input id="password" name="user.password" class="text" maxlength="128" value="" type="password"/>
          	</div>
      
	    
    	    <div id="row-continue">
    	     	<a href="#" id="submit" class="button submit"  role="button"><strong>Log In</strong></a>
    	    </div>
    	  </fieldset>
    	</form>
  	
    	<div id="motd-container">
	<div class="visitor" style="">
	<h2>系统简单介绍.</h2>
	<table border="0">
	<tbody><tr>
		<td><img src="images/en_marketing_image_1_1263407446.png" alt="" height="25" width="25"/></td>
		<td>....</td>
	</tr>
	<tr>
		<td><img src="images/en_marketing_image_2_1263407446.png" alt="" height="25" width="25"/></td>
		<td>说明......</td>
	</tr>
	<tr>
		<td><img src="images/en_marketing_image_3_1263407446.png" alt="" height="25" width="25"/></td>
		<td>说明.......</td>
	</tr>
	</tbody></table>
	</div>

				</div>

  	</div>
  </div>


  <div class="footer">
    <p id="copyright">Copyright © 2010 ZHUHAI POST Inc. All rights reserved.</p>
  </div>
</div>
	</body></html>
