
<jsp:useBean id="userbean" scope="session" class="hellobeans.UserBean"/>
  <!-- pick up all the properties from the add_acct.html submit form -->
  <jsp:setProperty name="userbean" property="*"/>
  <!-- invoke controller Servlet to process the request -->
  <jsp:forward page="/Controller">
    <jsp:param name="action" value="Stops"/>
  </jsp:forward>