import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Product {
    String productName;
    int productId;
    int stockQuantity;
    double price;

    public Product(String productName, int productId, int stockQuantity, double price) {
        this.productName = productName;
        this.productId = productId;
        this.stockQuantity = stockQuantity;
        this.price = price;
    }

    public void updateStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public boolean isAvailable(int quantity) {
        return stockQuantity >= quantity;
    }

    @Override
    public String toString() {
        return "Product: " + productName + " | ID: " + productId + " | Stock: " + stockQuantity + " | Price: $" + price;
    }
}

class StockManagementSystem extends Frame implements ActionListener {
    Map<Integer, Product> productCatalog = new HashMap<>();
    TextArea displayArea = new TextArea(20, 50);
    TextField productIdField = new TextField(10);
    TextField quantityField = new TextField(10);
    TextField nameField = new TextField(15);
    TextField priceField = new TextField(10);
    Button displayButton = new Button("Display Products");
    Button orderButton = new Button("Place Order");
    Button replenishButton = new Button("Replenish Stock");
    Button reportButton = new Button("Generate Report");
    Button addButton = new Button("Add Product");

    public StockManagementSystem() {
        setLayout(new FlowLayout());
        setTitle("Stock Management System");

        // Add UI components
        add(new Label("Product ID:"));
        add(productIdField);
        add(new Label("Quantity:"));
        add(quantityField);
        add(new Label("Product Name:"));
        add(nameField);
        add(new Label("Price:"));
        add(priceField);
        add(addButton);
        add(orderButton);
        add(replenishButton);
        add(displayButton);
        add(reportButton);
        add(displayArea);

        // Add Action Listeners
        addButton.addActionListener(this);
        displayButton.addActionListener(this);
        orderButton.addActionListener(this);
        replenishButton.addActionListener(this);
        reportButton.addActionListener(this);

        // Sample Data
        addProduct("Laptop", 101, 50, 799.99);
        addProduct("Smartphone", 102, 150, 499.99);
        addProduct("Tablet", 103, 80, 299.99);

        setSize(600, 500);
        setVisible(true);

        // Close button functionality
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public void addProduct(String name, int id, int quantity, double price) {
        Product product = new Product(name, id, quantity, price);
        productCatalog.put(id, product);
    }

    public void displayProducts() {
        displayArea.setText("");
        for (Product product : productCatalog.values()) {
            displayArea.append(product + "\n");
        }
    }

    public void placeOrder(int productId, int quantity) {
        Product product = productCatalog.get(productId);
        if (product != null) {
            if (product.isAvailable(quantity)) {
                product.updateStock(-quantity);
                displayArea.setText("Order placed successfully for " + quantity + " of " + product.productName);
            } else {
                displayArea.setText("Insufficient stock for the order.");
            }
        } else {
            displayArea.setText("Product not found.");
        }
    }

    public void replenishStock(int productId, int replenishmentQuantity) {
        Product product = productCatalog.get(productId);
        if (product != null) {
            product.updateStock(replenishmentQuantity);
            displayArea.setText("Stock replenished for " + product.productName + " by " + replenishmentQuantity + " units.");
        } else {
            displayArea.setText("Product not found.");
        }
    }

    public void generateReport() {
        displayArea.setText("Stock Report:\n");
        for (Product product : productCatalog.values()) {
            displayArea.append(product + "\n");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == displayButton) {
                displayProducts();
            } else if (e.getSource() == orderButton) {
                int id = Integer.parseInt(productIdField.getText());
                int qty = Integer.parseInt(quantityField.getText());
                placeOrder(id, qty);
            } else if (e.getSource() == replenishButton) {
                int id = Integer.parseInt(productIdField.getText());
                int qty = Integer.parseInt(quantityField.getText());
                replenishStock(id, qty);
            } else if (e.getSource() == reportButton) {
                generateReport();
            } else if (e.getSource() == addButton) {
                String name = nameField.getText();
                int id = Integer.parseInt(productIdField.getText());
                int qty = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());
                addProduct(name, id, qty, price);
                displayArea.setText("Product added successfully.");
            }
        } catch (Exception ex) {
            displayArea.setText("Error: " + ex.getMessage());
        }
    }
}

public class StockManagementApp {
    public static void main(String[] args) {
        new StockManagementSystem();
    }
}
