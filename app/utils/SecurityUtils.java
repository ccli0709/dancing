package utils;

import com.google.common.base.Strings;

import play.mvc.Http.Context;
import play.mvc.Http.Session;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class SecurityUtils extends Security.Authenticator {

	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get("LOGIN_UID");
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		ctx.session().put("LAST_UNAUTH_URL", ctx.request().uri());
		return redirect(controllers.routes.Application.login());
	}

	public static String getLastUnauthUrl() {
		String lastUnauthUrl = Context.current().session().get("LAST_UNAUTH_URL");
		if (Strings.isNullOrEmpty(lastUnauthUrl))
			lastUnauthUrl = controllers.routes.Application.index().absoluteURL(Context.current().request());

		return lastUnauthUrl;

	}

	public static void SetLoginParams(forms.PageParams params) {
		params.putString("loginUid", getLoginUid());
		params.putString("loginEmail", getLoginEmail());
		params.putString("loginName", getLoginName());
	}

	public static void setLoginUid(String loginUid) {
		Context.current().session().put("LOGIN_UID", loginUid);
	}

	public static String getLoginUid() {
		return Context.current().session().get("LOGIN_UID");
	}

	public static String getLoginEmail() {
		return Context.current().session().get("LOGIN_EMAIL");
	}

	public static void setLoginEmail(String loginEmail) {
		Context.current().session().put("LOGIN_EMAIL", loginEmail);
	}
	
	public static String getLoginName() {
		return Context.current().session().get("LOGIN_NAME");
	}
	
	public static void setLoginName(String loginName) {
		Context.current().session().put("LOGIN_NAME", loginName);
	}

}
