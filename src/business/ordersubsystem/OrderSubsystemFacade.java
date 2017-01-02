package business.ordersubsystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import business.exceptions.BackendException;
import business.externalinterfaces.CustomerProfile;
import business.externalinterfaces.Order;
import business.externalinterfaces.OrderItem;
import business.externalinterfaces.OrderSubsystem;
import business.externalinterfaces.Product;
import business.externalinterfaces.ShoppingCart;
import business.productsubsystem.ProductImpl;
import business.usecasecontrol.ManageProductsController;
import middleware.exceptions.DatabaseException;

@Component
public class OrderSubsystemFacade implements OrderSubsystem {
	private static final Logger LOG = Logger.getLogger(OrderSubsystemFacade.class.getPackage().getName());
	CustomerProfile custProfile;

	@Autowired
	ManageProductsController manageProductsController;
	
	public OrderSubsystemFacade(){}
	
	public OrderSubsystemFacade(CustomerProfile custProfile) {
		this.custProfile = custProfile;
	}

	/**
	 * Used by customer subsystem at login to obtain this customer's order
	 * history from the database. Assumes cust id has already been stored into
	 * the order subsystem facade This is created by using auxiliary methods at
	 * the bottom of this class file. First get all order ids for this customer.
	 * For each such id, get order data and form an order, and with that order
	 * id, get all order items and insert into the order.
	 */
	public List<Order> getOrderHistory() throws BackendException {
		List<Order> orderList = new ArrayList<>();
		try {
			List<Integer> idOfOrders = getAllOrderIds();
			for (Integer id : idOfOrders) {
				// get each order data
				Order order = getOrderData(id);
				// get each orderitem of each order
				List<OrderItem> listOrderItem = getOrderItems(id);
				order.setOrderItems(listOrderItem);
				orderList.add(order);
			}
		} catch (DatabaseException e) {
			throw new BackendException("database exception view Order History");
		}
		return orderList;

	}

	public void submitOrder(ShoppingCart cart) throws BackendException {
		// implement
		Order order = new OrderImpl();
		List<OrderItem> orderItemList = cart
				.getCartItems()
				.stream()
				.map(cartItem -> {
					OrderItemImpl orderItem = new OrderItemImpl(cartItem.getProductName(), Integer.valueOf(cartItem
							.getQuantity()), Double.valueOf(cartItem.getTotalprice()));
					orderItem.setProductId(cartItem.getProductid());
					return orderItem;
				}).collect(Collectors.toList());
		order.setOrderItems(orderItemList);
		order.setBillAddress(cart.getBillingAddress());
		order.setShipAddress(cart.getShippingAddress());
		order.setPaymentInfo(cart.getPaymentInfo());
		order.setDate(LocalDate.now());

		DbClassOrder dbClass = new DbClassOrder();
		try {
			dbClass.submitOrder(custProfile, order);
		} catch (DatabaseException e) {
			throw new BackendException(e);
		}
		LOG.warning("The method submitOrder(ShoppingCart cart) in OrderSubsystemFacade has not been implemented");
	}

	/**
	 * Used whenever an order item needs to be created from outside the order
	 * subsystem
	 */
	public OrderItem createOrderItem(Integer prodId, Integer orderId, String quantityReq, String totalPrice) {
		OrderItem orderItem = null;
		try {
			Product product = (ProductImpl) manageProductsController.getProductById(prodId);
			String name = product.getProductName();
			int quantity = Integer.parseInt(quantityReq);
			Double price = Double.parseDouble(totalPrice);
			orderItem = new OrderItemImpl(name, quantity, price);
			orderItem.setOrderId(orderId);

		} catch (BackendException e) {
			e.printStackTrace();
		}

		return orderItem;
	}

	/** to create an Order object from outside the subsystem */
	public static Order createOrder(Integer orderId, String orderDate, String totalPrice) {
		OrderImpl order = new OrderImpl();
		order.setOrderId(orderId);
		order.setDate(LocalDate.now());
		return order;
	}

	// /////////// Methods internal to the Order Subsystem -- NOT public
	List<Integer> getAllOrderIds() throws DatabaseException {
		DbClassOrder dbClass = new DbClassOrder();
		return dbClass.getAllOrderIds(custProfile);

	}

	/** Part of getOrderHistory */
	List<OrderItem> getOrderItems(Integer orderId) throws DatabaseException {
		DbClassOrder dbClass = new DbClassOrder();
		return dbClass.getOrderItems(orderId);
	}

	/**
	 * Uses order id to locate top-level order data for customer -- part of
	 * getOrderHistory
	 */
	OrderImpl getOrderData(Integer orderId) throws DatabaseException {
		DbClassOrder dbClass = new DbClassOrder();
		return dbClass.getOrderData(orderId);
	}
}
