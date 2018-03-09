<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.ArrayList"%>

<!doctype html>
<html>
<head>
<meta charset='utf-8'>
<title>App42 Sample Java-MySql Application</title>
<link href='css/style-User-Input-Form.css' rel='stylesheet'
	type='text/css'>
</head>
<body>
	<div class='App42PaaS_header_wrapper'>
		<div class='App42PaaS_header_inner'>
			<div class='App42PaaS_header'>
				<div class='logo'>
					<a href='http://app42paas.shephertz.com'><img border='0'
						alt='App42PaaS' src='images/logo.png'></img></a>
				</div>
			</div>
		</div>
	</div>
	<div class='App42PaaS_body_wrapper'>
		<div class='App42PaaS_body'>
			<div class='App42PaaS_body_inner'>
				<div class='contactPage_title'>


					<c:choose>
						<c:when test="${allUsers.size() > 0}">
						
							<table>
								<thead class='table-head'>
									<tr><td>Name</td><td>Email</td><td>Description</td></tr>
								</thead>
								<tbody>
								<c:forEach items="${allUsers}" var="user">
									<tr>
										<td><c:out value="${user.name}" /></td>
										<td><c:out value="${user.email}" /></td>
										<td><c:out value="${user.description}" /></td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
							
						</c:when>
						
						<c:otherwise>
							No users found
						</c:otherwise>
					</c:choose>


					<br /> <a href='/' style='font-size: 18px;'>Create Post</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>