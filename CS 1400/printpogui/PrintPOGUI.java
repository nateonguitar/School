package printpogui;

import javax.swing.JOptionPane;

public class PrintPOGUI
{
    public static void main(String[] args)
    {
        String itemName; // name of purchase item
        double price;    // price of purchase item
        int qty;         // number of items purchased
        
        itemName =  JOptionPane.showInputDialog("Name of purchase item: ");
        price    =  Double.parseDouble(JOptionPane.showInputDialog("Price of one item: $"));
        qty      =  Integer.parseInt(JOptionPane.showInputDialog("Quantity: "));
        
        JOptionPane.showMessageDialog(null, "PURCHASE ORDER:\n\n" + "Item: " + itemName + "\nQuantity: " + qty + "\nTotal price: $" + price * qty);
    }
}