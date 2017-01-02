package business.customersubsystem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import business.DbClassLogin;
import business.Login;
import business.exceptions.BackendException;
import business.exceptions.UserException;
import business.usecasecontrol.LoginControl;

public class SecurityUserServiceImpl implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			Integer customerId = Integer.parseInt(authentication.getName());
			Login login = new Login(customerId, authentication.getCredentials().toString());
			DbClassLogin dbClass = new DbClassLogin(login);
			if (!dbClass.authenticate()) {
				throw new UserException("Authentication failed for ID: " + login.getCustId());
			}
			List<GrantedAuthority> grantedAuths = new ArrayList<>();
			if (dbClass.getAuthorizationLevel() == 1) {
				grantedAuths.add(new SimpleGrantedAuthority("ADMIN"));
				grantedAuths.add(new SimpleGrantedAuthority("CUSTOMER"));
			} else {
				grantedAuths.add(new SimpleGrantedAuthority("CUSTOMER"));
			}
			Authentication auth = new UsernamePasswordAuthenticationToken(customerId, authentication.getCredentials()
					.toString(), grantedAuths);
			LoginControl loginControl = new LoginControl();
			loginControl.prepareAndStoreCustomerObject(login, dbClass.getAuthorizationLevel());
			return auth;
		} catch (BackendException e) {
			throw new UsernameNotFoundException("Invalid username or password");
		} catch (UserException e) {
			throw new UsernameNotFoundException("Invalid username or password");
		}catch(NumberFormatException e){
			throw new UsernameNotFoundException("Invalid username or password");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}