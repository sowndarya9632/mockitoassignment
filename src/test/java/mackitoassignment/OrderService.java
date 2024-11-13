package mackitoassignment;

//PaymentDetails class
 class PaymentDetails {
 private String cardNumber;
 private double amount;
public PaymentDetails(String cardNumber, double amount) {
	super();
	this.cardNumber = cardNumber;
	this.amount = amount;
}
/**
 * @return the cardNumber
 */
public String getCardNumber() {
	return cardNumber;
}
/**
 * @param cardNumber the cardNumber to set
 */
public void setCardNumber(String cardNumber) {
	this.cardNumber = cardNumber;
}
/**
 * @return the amount
 */
public double getAmount() {
	return amount;
}
/**
 * @param amount the amount to set
 */
public void setAmount(double amount) {
	this.amount = amount;
}
 

 // Constructors, getters, and setters
}

//MenuItem class to represent menu items
class MenuItem {
 private Long id;
 private String name;
 private boolean available;

 public MenuItem(Long id, String name, boolean available) {
     this.id = id;
     this.name = name;
     this.available = available;
 }

 public Long getId() { return id; }
 public String getName() { return name; }
 public boolean isAvailable() { return available; }
 public void setAvailable(boolean available) { this.available = available; }
}

//MenuService to fetch menu items
 interface MenuService {
 MenuItem getMenuItem(Long menuItemId);
}

//PaymentService to process payments
interface PaymentService {
 boolean processPayment(PaymentDetails paymentDetails);
}

//OrderService class to handle order placements
public class OrderService {
 private final MenuService menuService;
 private final PaymentService paymentService;

 public OrderService(MenuService menuService, PaymentService paymentService) {
     this.menuService = menuService;
     this.paymentService = paymentService;
 }

 // Method to place an order
 public String placeOrder(Long menuItemId, int quantity, PaymentDetails paymentDetails) {
     // Check if the item is available
     MenuItem menuItem = menuService.getMenuItem(menuItemId);
     if (menuItem == null || !menuItem.isAvailable()) {
         return "Item is out of stock.";
     }

     // Process the payment
     boolean paymentSuccessful = paymentService.processPayment(paymentDetails);
     if (!paymentSuccessful) {
         return "Payment failed.";
     }

     return "Order placed successfully.";
 }
}

