
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Purchase
{

    private ArrayList purchaseItems;
    private float subtotal;
    private int receiptId;
    private Date date;
    private int cardnum;
    private int expiry;
    private String expectedDate;
    private String deliveredDate;
    private Statement stmt;
    private ResultSet rs;
    private Connection con;

    public Purchase(Connection con)
    {
        //fetchReceiptID
        //getCurrentDate
        this.con = con;
        this.purchaseItems = new ArrayList();
        this.date = new Date();
        long t = date.getTime();
        java.sql.Date dt = new java.sql.Date(t);
        try
        {
            stmt = con.createStatement();
            stmt.executeUpdate("insert into Purchase values"
                    + "receipt_counter.nextval, " + dt + ", null, null, null, null, null");
            rs = stmt.executeQuery("SELECT receiptId"
                    + "FROM Purchase"
                    + "WHERE receiptID = receipt_counter.currval");
        } catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }

    public Purchase(Connection con, int receiptId)
    {
        this.purchaseItems = new ArrayList();

        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT receiptId, date, cardnum, expiry, expectedDate, deliveredDate"
                    + "FROM Purchase"
                    + "WHERE receiptId = " + receiptId + ";");
            if (rs.next() == true)
            {
                this.receiptId = rs.getInt(1);
                this.date = rs.getDate(2);
                this.cardnum = rs.getInt(3);
                this.expiry = rs.getInt(4);
                this.expectedDate = rs.getString(5);
                this.deliveredDate = rs.getString(6);
                rs = stmt.executeQuery("SELECT upc, quantity"
                        + "FROM PurchaseItem"
                        + "WHERE receiptId = " + receiptId + ";");
                while (rs.next())
                {
                    for (int i = 0; i < rs.getInt(2); i++)
                    {
                        purchaseItems.add(new Item(con, rs.getInt(1)));
                    }
                }
            } else
            {
                throw new SQLException("ReceiptId not found");
            }
        } catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }

    public void addItem(int upc)
    {
        try
        {
            Item Item = new Item(con, upc);
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT quantity"
                    + "FROM PurchaseItem"
                    + "WHERE receiptId = " + receiptId + ", upc = " + upc + ";");
            if (rs.next() == false)
            {
                if (checkStock(Item, 1) == true)
                {
                    stmt.executeUpdate("insert into PurchaseItem values"
                            + "(" + receiptId + "," + upc + ", 1);");
                }
                purchaseItems.add(Item);
            } else
            {
                if (checkStock(Item, rs.getInt(1)))
                {
                    stmt.executeUpdate("Update PurchaseItem"
                            + "SET quantity = quantity + 1"
                            + "WHERE receiptId = " + receiptId + ", upc = " + upc + ";");
                }
                purchaseItems.add(Item);
            }
            calculateSubtotal();

        } catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }

    public void removeItem(Item item)
    {
        try
        {
            stmt = con.createStatement();
            stmt.executeUpdate("Update PurchaseItem"
                    + "SET quantity = quantity + - 1"
                    + "WHERE receiptId = " + receiptId + ", upc = " + item.getUPC() + ";");
            Object purchaseItems[] = this.purchaseItems.toArray();
            for (int i = 0; i < this.purchaseItems.size(); i++)
            {
                if (((Item) purchaseItems[i]).getUPC() == item.getUPC())
                {
                    this.purchaseItems.remove(i);
                }
            }
            calculateSubtotal();
        } catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }

    private void calculateSubtotal()
    {
        float subtotal = 0;
        Object purchaseItems[] = this.purchaseItems.toArray();
        for (int i = 0; i < this.purchaseItems.size(); i++)
        {
            subtotal = +((Item) purchaseItems[i]).getSellPrice();
        }
        this.subtotal = subtotal;
    }

    public void Cancel()
    {
        try
        {
            stmt.executeUpdate("DELETE FROM PurchaseItem"
                    + "WHERE receiptID = " + receiptId + ";");
            stmt.executeUpdate("DELECTE FROM Purchase"
                    + "WHERE receiptID = " + receiptId + ";");
        } catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }

    private boolean checkStock(Item pi, int purchaseQuantity)
    {
        int stock = pi.getQuantity();
        if (stock > purchaseQuantity)
        {
            return true;
        } else
        {
            return false;
        }
    }

    public int getReceiptId()
    {
        return receiptId;
    }

    public Date getDate()
    {
        return date;
    }

    public int getCardnum()
    {
        return cardnum;
    }

    public int getExpiry()
    {
        return expiry;
    }

    public String getExpectedDate()
    {
        return expectedDate;
    }

    public String getDeliveredDate()
    {
        return deliveredDate;
    }

    public ArrayList getPurchaseItems()
    {
        return this.purchaseItems;
    }
}
