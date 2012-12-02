 <jsp:useBean id="userbean" scope="session" class="hellobeans.UserBean"/>
  <!-- pick up all the properties from the add_acct.html submit form -->
  <jsp:setProperty name="userbean" property="*"/>
  <jsp:forward page="/Controller">
    <jsp:param name="action" value="GetStops"/>
  </jsp:forward>