package mackitoassignment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class OrderServiceTest {

    @Mock
    private MenuService menuServiceMock;
    @Mock
    private PaymentService paymentServiceMock;

    @InjectMocks
    private OrderService orderService;

    private PaymentDetails paymentDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        paymentDetails = new PaymentDetails("1234567890", 100.0);  // Example payment details
    }

    @Test
    public void testPlaceOrder_Success() {
        // Arrange: Mock menu item availability
        MenuItem availableMenuItem = new MenuItem(1L, "Pasta", true);
        when(menuServiceMock.getMenuItem(1L)).thenReturn(availableMenuItem);

        // Mock successful payment processing
        when(paymentServiceMock.processPayment(paymentDetails)).thenReturn(true);

        // Act: Place the order
        String result = orderService.placeOrder(1L, 2, paymentDetails);

        // Assert: Verify the correct order placement message
        assertEquals("Order placed successfully.", result);

        // Verify interactions
        verify(menuServiceMock).getMenuItem(1L);
        verify(paymentServiceMock).processPayment(paymentDetails);
    }

    @Test
    public void testPlaceOrder_ItemOutOfStock() {
        // Arrange: Mock a menu item that is out of stock
        MenuItem outOfStockItem = new MenuItem(1L, "Pizza", false);
        when(menuServiceMock.getMenuItem(1L)).thenReturn(outOfStockItem);

        // Act: Try placing the order
        String result = orderService.placeOrder(1L, 2, paymentDetails);

        // Assert: Verify the correct message for out of stock
        assertEquals("Item is out of stock.", result);

        // Verify interactions
        verify(menuServiceMock).getMenuItem(1L);
        verify(paymentServiceMock, never()).processPayment(paymentDetails);  // Payment should not be processed
    }

    @Test
    public void testPlaceOrder_PaymentFailed() {
        // Arrange: Mock available menu item
        MenuItem availableMenuItem = new MenuItem(1L, "Burger", true);
        when(menuServiceMock.getMenuItem(1L)).thenReturn(availableMenuItem);

        // Mock payment failure
        when(paymentServiceMock.processPayment(paymentDetails)).thenReturn(false);

        // Act: Try placing the order with failed payment
        String result = orderService.placeOrder(1L, 2, paymentDetails);

        // Assert: Verify the correct message for payment failure
        assertEquals("Payment failed.", result);

        // Verify interactions
        verify(menuServiceMock).getMenuItem(1L);
        verify(paymentServiceMock).processPayment(paymentDetails);  // Payment was attempted
    }

    @Test
    public void testPlaceOrder_ItemNotFound() {
        // Arrange: Mock that the item is not found in the menu
        when(menuServiceMock.getMenuItem(1L)).thenReturn(null);

        // Act: Try placing the order
        String result = orderService.placeOrder(1L, 2, paymentDetails);

        // Assert: Verify the correct message for item not found
        assertEquals("Item is out of stock.", result);

        // Verify interactions
        verify(menuServiceMock).getMenuItem(1L);
        verify(paymentServiceMock, never()).processPayment(paymentDetails);  // Payment should not be processed
    }
}

