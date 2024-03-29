
package newpackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.*;
import javax.swing.JScrollPane;
public class finance
{

   public void event_report(int event_id)
    {
        String query= "select \n" +
                "event_info.no_visitor ,\n" +
                "event_info.price_per_person,\n" +
                "CAST(no_visitor AS DECIMAL(10,2)) * CAST(price_per_person AS DECIMAL(10,2)) as \"Total visitors price \",\n" +
                "event_info.no_meals,\n" +
                "event_info.meal_price,\n" +
                "CAST(no_meals AS DECIMAL(10,2)) * CAST(meal_price AS DECIMAL(10,2)) as \"Total meals price \",\n" +
                "event_info.no_drinks,\n" +
                "event_info.drink_price,\n" +
                "CAST(no_drinks AS DECIMAL(10,2)) * CAST(drink_price AS DECIMAL(10,2)) as \"Total drinks price \",\n" +
                "\n" +
                " TIMESTAMPDIFF(\n" +
                "        HOUR,\n" +
                "        CONCAT(CAST(start_date AS CHAR), ' ', CAST(start_time AS CHAR)),\n" +
                "        CONCAT(CAST(end_date AS CHAR), ' ', CAST(end_time AS CHAR))\n" +
                "    ) as number_of_hours,\n" +
                "place.Price_per_hour ,\n" +
                "CAST(Price_per_hour AS DECIMAL(10,2)) * CAST( TIMESTAMPDIFF(\n" +
                "        HOUR,\n" +
                "        CONCAT(CAST(start_date AS CHAR), ' ', CAST(start_time AS CHAR)),\n" +
                "        CONCAT(CAST(end_date AS CHAR), ' ', CAST(end_time AS CHAR))\n" +
                "    ) AS DECIMAL(10,2)) as \"Total place price \"\n" +
                "from event_info \n" +
                "left join event_place_order on event_place_order.event_info_id = event_info.id\n" +
                "left join place on place.id = event_place_order.place_id\n" +
                "where event_info.id = "+event_id+";";
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
  
           
            while (rs.next()) {
                int no_persons = rs.getInt(1);
                float person_price = rs.getFloat(2);
                float persons_total = rs.getFloat(3);
                int no_meal = rs.getInt(4);
                float meal_price = rs.getFloat(5);
                float meals_total = rs.getFloat(6);
                int no_drinks = rs.getInt(7);
                float drink_price = rs.getFloat(8);
                float drinks_total = rs.getFloat(9);
                int no_hours = rs.getInt(10);
                float hour_price = rs.getFloat(11);
                float hours_total = rs.getFloat(12);
                
                float Earned_money = persons_total + meals_total + drinks_total ;
                float Lost_money = hours_total;
                float total = Earned_money - Lost_money;
                
            // Create an HTML table for the finance report
          String reportHtml = "<html><body style='font-family: Arial, sans-serif;'>"
            + "<h1 style='text-align: center; color: blue;'>Finance Report</h1>"
            + "<table style='width: 100%; border-collapse: collapse;'>"
            + "<tr><th style='border: 1px solid black;'>Type</th><th style='border: 1px solid black;'>Count</th><th style='border: 1px solid black;'>Price/item</th><th style='border: 1px solid black;'>Total</th></tr>"
            + "<tr style='color: green;'><td style='border: 1px solid black;'>Tickets</td><td style='border: 1px solid black;'>" + no_persons + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", person_price) + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", persons_total) + "</td></tr>"
            + "<tr style='color: green;'><td style='border: 1px solid black;'>Meals</td><td style='border: 1px solid black;'>" + no_meal + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", meal_price) + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", meals_total) + "</td></tr>"
            + "<tr style='color: green;'><td style='border: 1px solid black;'>Drinks</td><td style='border: 1px solid black;'>" + no_drinks + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", drink_price) + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", drinks_total) + "</td></tr>"
            + "<tr style='color: green; font-weight: bold;'><td colspan='3' style='border: 1px solid black;'>Total Earned Money</td><td style='border: 1px solid black;'>" + String.format("%.2f", Earned_money) + "</td></tr>"
            + "<tr style='color: red;'><td style='border: 1px solid black;'>Place</td><td style='border: 1px solid black;'>" + no_hours + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", hour_price) + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", hours_total) + "</td></tr>"
            + "<tr style='color: red; font-weight: bold;'><td colspan='3' style='border: 1px solid black;'>Total Lost Money</td><td style='border: 1px solid black;'>" + String.format("%.2f", Lost_money) + "</td></tr>"
            + "<tr style='color: " + (total >= 0 ? "green" : "red") + "; font-weight: bold;'><td colspan='3' style='border: 1px solid black;'>Your Total Money</td><td style='border: 1px solid black;'>" + String.format("%.2f", total) + "</td></tr>"
            + "</table></body></html>";

       JLabel reportLabel = new JLabel(reportHtml);
        reportLabel.setPreferredSize(new Dimension(280, 300)); // Set the preferred size
        
        // ScrollPane for larger content
        JScrollPane scrollPane = new JScrollPane(reportLabel);
        scrollPane.setPreferredSize(new Dimension(280, 300));

        // OptionPane configuration
        JOptionPane optionPane = new JOptionPane(scrollPane, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
        
        // Display in a dialog
        JDialog dialog = optionPane.createDialog(null, "Finance Report");
        dialog.setVisible(true);

        // After the dialog is closed, save the content to an image
        BufferedImage image = new BufferedImage(scrollPane.getViewport().getWidth(), 
                                                scrollPane.getViewport().getHeight(), 
                                                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        scrollPane.getViewport().printAll(g2d);
        g2d.dispose();

        try {
            // Save as PNG
            ImageIO.write(image, "png", new File("FinanceReport.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
   
  public void enhancment_report(int event_id)
  {
       String query= "select \n" +
                "event_info.no_visitor ,\n" +
                "event_info.price_per_person,\n" +
                "CAST(no_visitor AS DECIMAL(10,2)) * CAST(price_per_person AS DECIMAL(10,2)) as \"Total visitors price \",\n" +
                "event_info.no_meals,\n" +
                "event_info.meal_price,\n" +
                "CAST(no_meals AS DECIMAL(10,2)) * CAST(meal_price AS DECIMAL(10,2)) as \"Total meals price \",\n" +
                "event_info.no_drinks,\n" +
                "event_info.drink_price,\n" +
                "CAST(no_drinks AS DECIMAL(10,2)) * CAST(drink_price AS DECIMAL(10,2)) as \"Total drinks price \",\n" +
                "\n" +
                " TIMESTAMPDIFF(\n" +
                "        HOUR,\n" +
                "        CONCAT(CAST(start_date AS CHAR), ' ', CAST(start_time AS CHAR)),\n" +
                "        CONCAT(CAST(end_date AS CHAR), ' ', CAST(end_time AS CHAR))\n" +
                "    ) as number_of_hours,\n" +
                "place.Price_per_hour ,\n" +
                "CAST(Price_per_hour AS DECIMAL(10,2)) * CAST( TIMESTAMPDIFF(\n" +
                "        HOUR,\n" +
                "        CONCAT(CAST(start_date AS CHAR), ' ', CAST(start_time AS CHAR)),\n" +
                "        CONCAT(CAST(end_date AS CHAR), ' ', CAST(end_time AS CHAR))\n" +
                "    ) AS DECIMAL(10,2)) as \"Total place price \",\n" +
                "place.capacity , event_info.no_visitor \n"
               + "from event_info \n" +
                "left join event_place_order on event_place_order.event_info_id = event_info.id\n" +
                "left join place on place.id = event_place_order.place_id\n" +
                "where event_info.id = "+event_id+";";
       
       String query1 = "SELECT\n" +
"    CAST(no_visitor AS DECIMAL(10,2)) * CAST(0.1 AS DECIMAL(10,2)) + no_visitor AS \"no_visitors\",\n" +
"    event_info.price_per_person,\n" +
"    CAST(CAST(no_visitor AS DECIMAL(10,2)) * CAST(0.1 AS DECIMAL(10,2)) + no_visitor AS DECIMAL(10,2)) * CAST(price_per_person AS DECIMAL(10,2)) AS \"Total visitors price\",\n" +
"    \n" +
"    CAST(no_visitor AS DECIMAL(10,2)) * CAST(0.15 AS DECIMAL(10,2)) + no_visitor AS \"no_meals\",\n" +
"    event_info.meal_price,\n" +
"    CAST(CAST(no_visitor AS DECIMAL(10,2)) * CAST(0.15 AS DECIMAL(10,2)) + no_visitor AS DECIMAL(10,2)) * CAST(meal_price AS DECIMAL(10,2)) AS \"Total meals price\",\n" +
"    \n" +
"    CAST(no_visitor AS DECIMAL(10,2)) * CAST(0.20 AS DECIMAL(10,2)) + no_visitor AS \"no_drinks\",\n" +
"    event_info.drink_price,\n" +
"    CAST(CAST(no_visitor AS DECIMAL(10,2)) * CAST(0.20 AS DECIMAL(10,2)) + no_visitor AS DECIMAL(10,2)) * CAST(drink_price AS DECIMAL(10,2)) AS \"Total drinks price\",\n" +
"    \n" +
"    TIMESTAMPDIFF(\n" +
"        HOUR,\n" +
"        CONCAT(CAST(start_date AS CHAR), ' ', CAST(start_time AS CHAR)),\n" +
"        CONCAT(CAST(end_date AS CHAR), ' ', CAST(end_time AS CHAR))\n" +
"    ) AS number_of_hours,\n" +
"    \n" +
"    CAST(price_per_hour AS DECIMAL(10,2)) / CAST(place.capacity AS DECIMAL(10,2)) *\n" +
"    (CAST(no_visitor AS DECIMAL(10,2)) * CAST(0.1 AS DECIMAL(10,2)) + CAST(no_visitor AS DECIMAL(10,2)))\n" +
"    AS `new price`,\n" +
"    \n" +
"    CAST(\n" +
"        CAST(price_per_hour AS DECIMAL(10,2)) / CAST(place.capacity AS DECIMAL(10,2)) *\n" +
"        (CAST(no_visitor AS DECIMAL(10,2)) * CAST(0.1 AS DECIMAL(10,2)) + CAST(no_visitor AS DECIMAL(10,2)))\n" +
"        AS DECIMAL(10,2)\n" +
"    ) * CAST(\n" +
"        TIMESTAMPDIFF(\n" +
"            HOUR,\n" +
"            CONCAT(CAST(start_date AS CHAR), ' ', CAST(start_time AS CHAR)),\n" +
"            CONCAT(CAST(end_date AS CHAR), ' ', CAST(end_time AS CHAR))\n" +
"        ) AS DECIMAL(10,2)\n" +
"    ) AS \"Total place price\",\n" +
"    \n" +
"    CAST(no_visitor AS DECIMAL(10,2)) * CAST(0.1 AS DECIMAL(10,2)) + no_visitor AS \"new capacity\",\n" +
"    event_info.no_visitor\n" +
"\n" +
"FROM event_info\n" +
"LEFT JOIN event_place_order ON event_place_order.event_info_id = event_info.id\n" +
"LEFT JOIN place ON place.id = event_place_order.place_id\n" +
"WHERE event_info.id = "+event_id+";";
        
       
       
       String query2 = "";
       String reportHtml = null;
       String reportHtml1 = null;

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
  
           
            while (rs.next()) {
                int no_persons = rs.getInt(1);
                float person_price = rs.getFloat(2);
                float persons_total = rs.getFloat(3);
                int no_meal = rs.getInt(4);
                float meal_price = rs.getFloat(5);
                float meals_total = rs.getFloat(6);
                int no_drinks = rs.getInt(7);
                float drink_price = rs.getFloat(8);
                float drinks_total = rs.getFloat(9);
                int no_hours = rs.getInt(10);
                float hour_price = rs.getFloat(11);
                float hours_total = rs.getFloat(12);
                int place_capacity = rs.getInt(13);
                int no_visitors = rs.getInt(14);
                float Earned_money = persons_total + meals_total + drinks_total ;
                float Lost_money = hours_total;
                float total = Earned_money - Lost_money;
                
            // Create an HTML table for the finance report
          reportHtml = "<html><body style='font-family: Arial, sans-serif;'>"
            + "<h2 style='text-align: center; color: black;'>Finance Report</h2>"
            + "<h3 style='text-align: left; color: blue;'>Number of visitors :"+no_visitors+"</h3>"
            + "<table style='width: 100%; border-collapse: collapse;'>"
            + "<tr><th style='border: 1px solid black;'>Type</th><th style='border: 1px solid black;'>Count</th><th style='border: 1px solid black;'>Price/item</th><th style='border: 1px solid black;'>Total</th></tr>"
            + "<tr style='color: green;'><td style='border: 1px solid black;'>Tickets</td><td style='border: 1px solid black;'>" + no_persons + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", person_price) + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", persons_total) + "</td></tr>"
            + "<tr style='color: green;'><td style='border: 1px solid black;'>Meals</td><td style='border: 1px solid black;'>" + no_meal + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", meal_price) + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", meals_total) + "</td></tr>"
            + "<tr style='color: green;'><td style='border: 1px solid black;'>Drinks</td><td style='border: 1px solid black;'>" + no_drinks + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", drink_price) + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", drinks_total) + "</td></tr>"
            + "<tr style='color: green; font-weight: bold;'><td colspan='3' style='border: 1px solid black;'>Total Earned Money</td><td style='border: 1px solid black;'>" + String.format("%.2f", Earned_money) + "</td></tr>"
            + "<tr style='color: red;'><td style='border: 1px solid black;'>Place</td><td style='border: 1px solid black;'>" + no_hours + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", hour_price) + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", hours_total) + "</td></tr>"
            + "<tr style='color: red; font-weight: bold;'><td colspan='3' style='border: 1px solid black;'>Place Capacity</td><td style='border: 1px solid black;'>" + place_capacity + "</td></tr>"
            + "<tr style='color: red; font-weight: bold;'><td colspan='3' style='border: 1px solid black;'>Total Lost Money</td><td style='border: 1px solid black;'>" + String.format("%.2f", Lost_money) + "</td></tr>"
            + "<tr style='color: " + (total >= 0 ? "green" : "red") + "; font-weight: bold;'><td colspan='3' style='border: 1px solid black;'>Your Total Money</td><td style='border: 1px solid black;'>" + String.format("%.2f", total) + "</td></tr>"
            + "</table></body></html>";
            }
            
              rs = stmt.executeQuery(query1);
  
            while (rs.next()) {
                int no_persons = rs.getInt(1);
                float person_price = rs.getFloat(2);
                float persons_total = rs.getFloat(3);
                int no_meal = rs.getInt(4);
                float meal_price = rs.getFloat(5);
                float meals_total = rs.getFloat(6);
                int no_drinks = rs.getInt(7);
                float drink_price = rs.getFloat(8);
                float drinks_total = rs.getFloat(9);
                int no_hours = rs.getInt(10);
                float hour_price = rs.getFloat(11);
                float hours_total = rs.getFloat(12);
                int place_capacity = rs.getInt(13);
                int no_visitors = rs.getInt(14);
                float Earned_money = persons_total + meals_total + drinks_total ;
                float Lost_money = hours_total;
                float total = Earned_money - Lost_money;
                query2 = "select * from place where place.capacity BETWEEN '"+place_capacity+"' and '"+(place_capacity+(place_capacity*0.3))+"'\n" +
                "ORDER BY place.capacity;";
            // Create an HTML table for the finance report
          reportHtml1 = "<html><body style='font-family: Arial, sans-serif;'>"
            + "<h2 style='text-align: center; color: black;'>Forcasting & emhancment </h2>"
            + "<h3 style='text-align: left; color: blue;'>Number of visitors :" + no_visitors + "</h3>"
            + "<table style='width: 100%; border-collapse: collapse;'>"
            + "<tr><th style='border: 1px solid black;'>Type</th><th style='border: 1px solid black;'>Count</th><th style='border: 1px solid black;'>Price/item</th><th style='border: 1px solid black;'>Total</th></tr>"
            + "<tr style='color: green;'><td style='border: 1px solid black;'>Tickets</td><td style='border: 1px solid black;'><span style='color: blue;'>" + no_persons + "</span></td><td style='border: 1px solid black;'>" + String.format("%.2f", person_price) + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", persons_total) + "</td></tr>"
            + "<tr style='color: green;'><td style='border: 1px solid black;'>Meals</td><td style='border: 1px solid black;'><span style='color: blue;'>" + no_meal + "</span></td><td style='border: 1px solid black;'>" + String.format("%.2f", meal_price) + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", meals_total) + "</td></tr>"
            + "<tr style='color: green;'><td style='border: 1px solid black;'>Drinks</td><td style='border: 1px solid black;'><span style='color: blue;'>" + no_drinks + "</span></td><td style='border: 1px solid black;'>" + String.format("%.2f", drink_price) + "</td><td style='border: 1px solid black;'>" + String.format("%.2f", drinks_total) + "</td></tr>"
            + "<tr style='color: green; font-weight: bold;'><td colspan='3' style='border: 1px solid black;'>Total Earned Money</td><td style='border: 1px solid black;'>" + String.format("%.2f", Earned_money) + "</td></tr>"
            + "<tr style='color: red;'><td style='border: 1px solid black;'>Place</td><td style='border: 1px solid black;'>" + no_hours + "</td><td style='border: 1px solid black;'><span style='color:blue;'>" + String.format("%.2f", hour_price) + "</span></td><td style='border: 1px solid black;'>" + String.format("%.2f", hours_total) + "</td></tr>"
            + "<tr style='color: red; font-weight: bold;'><td colspan='3' style='border: 1px solid black;'>Place Capacity</td><td style='border: 1px solid black;'><span style='color: blue;'>" + place_capacity + "</span></td></tr>"
            + "<tr style='color: red; font-weight: bold;'><td colspan='3' style='border: 1px solid black;'>Total Lost Money</td><td style='border: 1px solid black;'>" + String.format("%.2f", Lost_money) + "</td></tr>"
            + "<tr style='color: " + (total >= 0 ? "green" : "red") + "; font-weight: bold;'><td colspan='3' style='border: 1px solid black;'>Your Total Money</td><td style='border: 1px solid black;'>" + String.format("%.2f", total) + "</td></tr>"
            + "</table></body></html>";

            }
            
            rs = stmt.executeQuery(query2);
            String htmlBuilder = "<html><body style='font-family: Arial, sans-serif;'>"
                    + "<h2 style='text-align: center; color: black;' >Nominated places</h2>"
                    + "<table style='width: 100%; border-collapse: collapse'>"
                    + "<tr><th>ID</th><th>Name</th><th>Location</th><th>Capacity</th><th>Price per hour</th><th>Rate</th></tr>";

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String location = rs.getString("location");
                int capacity = rs.getInt("capacity");
                float price = rs.getFloat("price_per_hour");
                int rate = rs.getInt("rate");
                htmlBuilder += "<tr>"
                + "<td style='border: 1px solid black; color: blue;'>" + id + "</td>"
                + "<td style='border: 1px solid black;'>" + name + "</td>"
                + "<td style='border: 1px solid black;'>" + location + "</td>"
                + "<td style='border: 1px solid black; color: blue;'>" + capacity + "</td>"
                + "<td style='border: 1px solid black;'>" + String.format("%.2f", price) + "</td>"
                + "<td style='border: 1px solid black;'>" + rate + "</td>"
                + "</tr>";
            }
            htmlBuilder += "</table></body></html>";
       JLabel reportLabel = new JLabel(reportHtml);
       JLabel reportLabel2 = new JLabel(reportHtml1);
       JLabel  forecasting_places = new JLabel(htmlBuilder);

         

       
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
        horizontalPanel.add(new JScrollPane(reportLabel));
        horizontalPanel.add(new JScrollPane(reportLabel2));

       
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.add(horizontalPanel);
        verticalPanel.add(new JScrollPane(forecasting_places));

        
        JScrollPane scrollPane = new JScrollPane(verticalPanel);
        scrollPane.setPreferredSize(new Dimension(600, 450));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

       
        JOptionPane optionPane = new JOptionPane(scrollPane, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);

        
        JDialog dialog = optionPane.createDialog(null, "Forcasting Report");
        dialog.setVisible(true);

       
        BufferedImage image = new BufferedImage(scrollPane.getViewport().getWidth(), 
                                                scrollPane.getViewport().getHeight(), 
                                                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        scrollPane.getViewport().printAll(g2d);
        g2d.dispose();
        
        try {
            
            ImageIO.write(image, "png", new File("ForcastingReport.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
  }
   
   
}
