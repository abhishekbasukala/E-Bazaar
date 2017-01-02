package business.customersubsystem;

import java.util.List;

import business.exceptions.BackendException;
import business.exceptions.BusinessException;
import business.exceptions.RuleException;
import business.externalinterfaces.Address;
import business.externalinterfaces.CreditCard;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.CustomerSubsystem;
import business.externalinterfaces.DbClassAddressForTest;
import business.externalinterfaces.DbClassCustomerProfileForTest;
import business.externalinterfaces.Order;
import business.externalinterfaces.OrderSubsystem;
import business.externalinterfaces.Rules;
import business.externalinterfaces.ShoppingCart;
import business.externalinterfaces.ShoppingCartSubsystem;
import business.ordersubsystem.OrderSubsystemFacade;
import business.shoppingcartsubsystem.ShoppingCartSubsystemFacade;
import middleware.exceptions.DatabaseException;

public class CustomerSubsystemFacade implements CustomerSubsystem {
	ShoppingCartSubsystem shoppingCartSubsystem;
	OrderSubsystem orderSubsystem;
	List<Order> orderHistory;
	AddressImpl defaultShipAddress;
	AddressImpl defaultBillAddress;
	CreditCardImpl defaultPaymentInfo;
	CustomerProfileImpl customerProfile;

	/**
	 * Use for loading order history, default addresses, default payment info,
	 * saved shopping cart,cust profile after login
	 * 
	 * @throws DatabaseException
	 */
	public void initializeCustomer(Integer id, int authorizationLevel) throws BackendException {

		boolean isAdmin = (authorizationLevel >= 1);
		loadCustomerProfile(id, isAdmin);
		loadOrderData();
		loadDefaultShipAddress();
		loadDefaultBillAddress();
		loadDefaultPaymentInfo();
		shoppingCartSubsystem = ShoppingCartSubsystemFacade.INSTANCE;
		shoppingCartSubsystem.setCustomerProfile(customerProfile);
		shoppingCartSubsystem.retrieveSavedCart();
		loadOrderData();

	}

	void loadCustomerProfile(int id, boolean isAdmin) throws BackendException {
		try {
			DbClassCustomerProfile dbclass = new DbClassCustomerProfile();
			customerProfile = dbclass.readCustomerProfile(id);
			customerProfile.setIsAdmin(isAdmin);
			customerProfile.setCustId(id);
		} catch (DatabaseException e) {
			throw new BackendException(e);
		}
	}

	void loadDefaultShipAddress() throws BackendException {
		// implement
		try {
			DbClassAddress dbClass = new DbClassAddress();
			Address add = dbClass.readDefaultShipAddress(customerProfile);
			defaultShipAddress = new AddressImpl(add.getStreet(), add.getCity(), add.getState(), add.getZip(), true,
					false);
		} catch (DatabaseException e) {
			throw new BackendException(e);
		}
	}

	void loadDefaultBillAddress() throws BackendException {
		// implement
		try {
			DbClassAddress dbClass = new DbClassAddress();
			Address add = dbClass.readDefaultBillAddress(customerProfile);
			defaultBillAddress = new AddressImpl(add.getStreet(), add.getCity(), add.getState(), add.getZip(), false,
					true);
		} catch (DatabaseException e) {
			throw new BackendException(e);
		}
	}

	void loadDefaultPaymentInfo() throws BackendException {
		// implement
		try {
			DbClassCustomerProfile dbClass = new DbClassCustomerProfile();
			defaultPaymentInfo = dbClass.readDefaultPaymentInfo(customerProfile);

		} catch (DatabaseException e) {
			throw new BackendException(e);
		}
	}

	void loadOrderData() throws BackendException {
		// retrieve the order history for the customer and store here
		orderSubsystem = new OrderSubsystemFacade(customerProfile);
		orderHistory = orderSubsystem.getOrderHistory();

	}

	/**
	 * Returns true if user has admin access
	 */
	/*
	 * public boolean isAdmin() { return customerProfile.isAdmin(); }
	 *//**
	 * Use for saving an address created by user
	 */
	/*
	 * public void saveNewAddress(Address addr) throws BackendException { try {
	 * orderHistory = orderSubsystem.getOrderHistory();
	 * 
	 * 
	 * 
	 * }
	 */
	/**
	 * Returns true if user has admin access
	 */
	public boolean isAdmin() {
		return customerProfile.isAdmin();
	}

	/**
	 * Use for saving an address created by user
	 */
	public void saveNewAddress(Address addr) throws BackendException {
		try {
			DbClassAddress dbClass = new DbClassAddress();
			dbClass.setAddress(addr);
			dbClass.saveAddress(customerProfile);
		} catch (DatabaseException e) {
			throw new BackendException(e);
		}
	}

	public CustomerProfile getCustomerProfile() {

		return customerProfile;
	}

	public Address getDefaultShippingAddress() {
		return defaultShipAddress;
	}

	public Address getDefaultBillingAddress() {
		return defaultBillAddress;
	}

	public CreditCard getDefaultPaymentInfo() {
		return defaultPaymentInfo;
	}

	/**
	 * Use to supply all stored addresses of a customer when he wishes to select
	 * an address in ship/bill window
	 */
	public List<Address> getAllAddresses() throws BackendException {

		try {
			DbClassAddress dbClass = new DbClassAddress();
			return dbClass.readAllAddresses(customerProfile);

		} catch (DatabaseException e) {
			throw new BackendException(e);
		}
		// implement
	}

	public Address runAddressRules(Address addr) throws RuleException, BusinessException {

		Rules transferObject = new RulesAddress(addr);
		transferObject.runRules();

		// updates are in the form of a List; 0th object is the necessary
		// Address
		AddressImpl update = (AddressImpl) transferObject.getUpdates().get(0);
		return update;
	}

	public void runPaymentRules(Address addr, CreditCard cc) throws RuleException, BusinessException {
		Rules transferObject = new RulesPayment(addr, cc);
		transferObject.runRules();
	}

	public static Address createAddress(String street, String city, String state, String zip, boolean isShip,
			boolean isBill) {
		return new AddressImpl(street, city, state, zip, isShip, isBill);
	}

	public static CustomerProfile createCustProfile(Integer custid, String firstName, String lastName, boolean isAdmin) {
		return new CustomerProfileImpl(custid, firstName, lastName, isAdmin);
	}

	public static CreditCard createCreditCard(String nameOnCard, String expirationDate, String cardNum, String cardType) {
		return new CreditCardImpl(nameOnCard, expirationDate, cardNum, cardType);
	}

	@Override
	public List<Order> getOrderHistory() {
		return orderHistory;
	}

	@Override
	public void submitOrder() throws BackendException {
		ShoppingCart finalShoppingCartt = ShoppingCartSubsystemFacade.INSTANCE.getLiveCart();
		OrderSubsystem os = new OrderSubsystemFacade(customerProfile);
		os.submitOrder(finalShoppingCartt);
	}

	@Override
	public void setShippingAddressInCart(Address addr) {
		ShoppingCartSubsystemFacade.INSTANCE.setShippingAddress(addr);
	}

	@Override
	public void setBillingAddressInCart(Address addr) {
		ShoppingCartSubsystemFacade.INSTANCE.setBillingAddress(addr);
	}

	@Override
	public void setPaymentInfoInCart(CreditCard cc) {
		ShoppingCartSubsystemFacade.INSTANCE.setPaymentInfo(cc);
	}

	@Override
	public void deleteAddress(int addressId) throws BackendException {
		DbClassAddress dbClass = new DbClassAddress();
		try {
			dbClass.deleteAddress(addressId);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			throw new BackendException(e);
		}
	}

	@Override
	public DbClassAddressForTest getGenericDbClassAddress() {
		return new DbClassAddress();
	}

	@Override
	public CustomerProfile getGenericCustomerProfile() {
		return new CustomerProfileImpl();
	}

	@Override
	public DbClassCustomerProfileForTest getGenericDbClassCustomerProfile() {
		return new DbClassCustomerProfile();
	}

}
