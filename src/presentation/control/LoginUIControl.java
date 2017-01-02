package presentation.control;

import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import presentation.data.LoginData;
import business.Login;
import business.exceptions.BackendException;

@Controller
public class LoginUIControl {
	LoginData loginData = new LoginData();

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String getLoginPage() {
		System.out.println("Loading login page");
		return "login";
	}

	/*@RequestMapping(method = RequestMethod.GET, value = "/loadCustomer")
	public String LoadCustomerData() {
		System.out.println("Loading customer data with customerId: " + getCustomerId() + " and authorization level"
				+ getAuthorizationLevel());
		Login login = new Login(getCustomerId(), null);
		try {
			loginData.loadCustomer(login, getAuthorizationLevel());
		} catch (BackendException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}*/

	@RequestMapping(method = RequestMethod.GET, value = "/logout")
	public String logout() {
		System.out.println("Log out...");
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/accessdenied")
	public String accessDenied() {
		System.out.println("Log out...");
		return "accessdenied";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/loginfailed")
	public String getLoginPageWithError(HttpSession session) {
		System.out.println("Loading login page with error");
		session.setAttribute("error", "Invalid Username or Password");
		return "redirect:/login";
	}

	/*private Integer getCustomerId() {
		try {
			return (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private int getAuthorizationLevel() {
		try {
			Iterator<? extends GrantedAuthority> it = SecurityContextHolder.getContext().getAuthentication()
					.getAuthorities().iterator();
			while (it.hasNext()) {
				if (it.next().toString().equals("ADMIN")) {
					return 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}*/
}
