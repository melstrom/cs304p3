import java.sql.*;

// no where near complete...
public class ShipItem {
	private int sid;
	private int upc;
	private int supPrice;
	private int quantity;
	private Statement stmt;
	private ResultSet rs;
	
	public ShipItem (int sid, int upc)
	{
	sid = this.sid;
	upc = this.upc;
	};