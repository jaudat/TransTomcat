  function validate_form(thisform) {
    with (thisform) {
      if (validate_required(uid, "uid must be entered") == false) {
        uid.focus();
        return false;
      }
      if (validate_required(pwd, "pwd must be entered") == false) {
        pwd.focus();
        return false;
      }
    }
  }
  
  function validate_required(field, alerttxt) {
    with (field) {
      if (value==null||value=="") {
        alert(alerttxt);
        return false;
      }
      else return true;
    }
  }
