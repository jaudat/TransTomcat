<!-- Use the Java Standard Tag Library to provide tags to iterate
     through a resultSet represented as a list of UserBean values -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html>
  <head><title>sampleAppBeans: Account Creation Successful</title></head>
  <body>
    <h2>sampleAppBeans: Account Creation Successful!<br/>
 	Current Accounts:</h2>
    <!-- Invoke ViewAccounts Servlet to populate accountData -->
    <jsp:include page="/ViewAccounts"/>
    <table border="1">
      <tr><th>Username</th><th>Password</th></tr>
      <c:forEach items="${accountData}" var="myData">
        <tr>
            <td><c:out value="${myData.id}"/></td>
            <td><c:out value="${myData.password}"/></td>
        </tr>
      </c:forEach>
    </table>
    <br/>
    <a href="/WEB-INF/index.html">Back to Main Page</a>
  </body>
</html>
