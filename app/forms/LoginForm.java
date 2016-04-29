package forms;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;

public class LoginForm {

	@Constraints.Required
	public String email;
	@Constraints.Required
	public String password;

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<ValidationError>();

		if ("test@gmail.com".equals(email) && "test@gmail.com".equals(password)) {
		} else {
			errors.add(new ValidationError("password", "帳號／密碼不符，請重新輸入。"));
		}
		// if (User.byEmail(email) != null) {
		// errors.add(new ValidationError("email", "This e-mail is already
		// registered."));
		// }
		return errors.isEmpty() ? null : errors;
	}
}
