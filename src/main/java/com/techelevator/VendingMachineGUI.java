package com.techelevator;

import com.techelevator.view.PurchaseOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VendingMachineGUI extends JFrame {
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String FEED_MONEY = "Feed Money";
	private static final String SELECT_PRODUCT = "Select Product";
	private static final String FINISH_TRANSACTION = "Finish Transaction";
	private double currentBalance = 0.00;

	private final PurchaseOptions purchaseOptions;
	private CardLayout cardLayout;
	private JPanel cardPanel;

	public VendingMachineGUI() {
		purchaseOptions = new PurchaseOptions();

		// Set up the frame
		setTitle("Vending Machine");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);

		// Create and add the main menu panel
		JPanel mainMenuPanel = createMainMenuPanel();
		cardPanel.add(mainMenuPanel, "MainMenu");

		// Create and add the purchase options panel
		JPanel purchaseOptionsPanel = createPurchaseOptionsPanel();
		cardPanel.add(purchaseOptionsPanel, "PurchaseOptions");

		add(cardPanel);
		cardLayout.show(cardPanel, "MainMenu");
	}

	private JPanel createMainMenuPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3, 1));

		// Add buttons to the panel
		JButton displayItemsButton = new JButton(MAIN_MENU_OPTION_DISPLAY_ITEMS);
		JButton purchaseButton = new JButton(MAIN_MENU_OPTION_PURCHASE);
		JButton exitButton = new JButton(MAIN_MENU_OPTION_EXIT);

		mainPanel.add(displayItemsButton);
		mainPanel.add(purchaseButton);
		mainPanel.add(exitButton);

		// Add action listeners for buttons
		displayItemsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				purchaseOptions.displayItems();
			}
		});

		purchaseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "PurchaseOptions");
			}
		});

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				purchaseOptions.exitVendingMachine();
				System.exit(0);
			}
		});

		return mainPanel;
	}

	private JPanel createPurchaseOptionsPanel() {
		JPanel purchasePanel = new JPanel();
		purchasePanel.setLayout(new GridLayout(4, 1));

		JLabel balanceLabel = new JLabel("Current Balance: $" + String.format("%.2f", currentBalance));

		// Create buttons for purchase options
		JButton feedMoneyButton = new JButton(FEED_MONEY);
		JButton selectProductButton = new JButton(SELECT_PRODUCT);
		JButton finishTransactionButton = new JButton(FINISH_TRANSACTION);

		purchasePanel.add(balanceLabel);
		purchasePanel.add(feedMoneyButton);
		purchasePanel.add(selectProductButton);
		purchasePanel.add(finishTransactionButton);

		// Add action listeners for purchase buttons
		feedMoneyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String amount = JOptionPane.showInputDialog("Please enter a money value for your purchase:");
				if (amount != null && !amount.isEmpty()) {
					try {
						currentBalance = purchaseOptions.feedMoney(currentBalance, Double.parseDouble(amount));
						balanceLabel.setText("Current Balance: $" + String.format("%.2f", currentBalance));
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
					}
				}
			}
		});

		selectProductButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Display the items first
				purchaseOptions.displayItems();

				// Then prompt for the item code
				String userChoice = JOptionPane.showInputDialog("Please enter item code:").toUpperCase();
				if (userChoice != null && !userChoice.isEmpty()) {
					currentBalance = Double.parseDouble(purchaseOptions.selectProduct(currentBalance, userChoice));
					balanceLabel.setText("Current Balance: $" + String.format("%.2f", currentBalance));
					purchaseOptions.displayItems();  // Update the items display after product selection
				}
			}
		});

		finishTransactionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				purchaseOptions.getChange(currentBalance);
				currentBalance = 0.00;
				balanceLabel.setText("Current Balance: $" + String.format("%.2f", currentBalance));
				cardLayout.show(cardPanel, "MainMenu");
			}
		});

		return purchasePanel;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new VendingMachineGUI().setVisible(true);
			}
		});
	}
}
