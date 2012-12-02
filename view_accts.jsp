<!-- Use the Java Standard Tag Library to provide tags to iterate
     through a resultSet represented as a list of UserBean values -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html>
  <head><title>sampleAppBeans: View Existing Accounts</title></head>
  <body>
    <h2>sampleAppBeans: View Existing Accounts</h2>
    <!-- invoke a Servlet to populate a bean list containing
	 current DB account info -->
    <jsp:include page="/Controller?action=ViewAccounts"/>
    <table border="1">
      <tr><th>Username</th><th>Password</th></tr>
      <c:forEach items="${accountData}" var="myData">
        <tr>
            <td><c:out value="${myData.uid}"/></td>
            <td><c:out value="${myData.pwd}"/></td>
        </tr>
      </c:forEach>
    </table>
    <br/>
    <a href="index.html">Back to Main Page</a>
  </body>
</html>
