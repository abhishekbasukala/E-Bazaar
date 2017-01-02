package presentation.control;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import business.exceptions.BackendException;
import presentation.data.OrderItemPres;
import presentation.data.OrderPres;
import presentation.data.ViewOrdersData;

@Controller
@RequestMapping("/customer")
public class ViewOrdersUIControl {

	@RequestMapping("/viewPastOrder")
	public String viewOrdersHandler(ModelMap modelMap) {
		try {
			List<OrderPres> orderList = ViewOrdersData.INSTANCE.getOrderList();
			modelMap.addAttribute("orders", orderList);
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("message", e.getMessage());
		}
		return "vieworder";
	}

	@RequestMapping(value = "/orderdetail/{orderid}", method = RequestMethod.GET)
	public String viewOrderHistoryDetailHandler(@PathVariable String orderid, ModelMap modelMap) {

		List<OrderItemPres> orderItems = null;
		try {

			orderItems = ViewOrdersData.INSTANCE.getOrderListDetail(Integer.parseInt(orderid));
			modelMap.addAttribute("orderItemlist", orderItems);
		} catch (BackendException e) {
			e.printStackTrace();
			modelMap.addAttribute("message", e.getMessage());
		}
		return "vieworderdetails";
	}
}
