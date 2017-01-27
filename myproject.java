import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.*;
class SwingMenu
{
  public SwingMenu()
   {

    JFrame frame = new JFrame(" ");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JMenuBar menubar = new JMenuBar();
    JMenu filemenu = new JMenu("menu");
    filemenu.add(new JSeparator());
    JMenuItem fileItem1 = new JMenuItem("browse");
    fileItem1.addActionListener(new menuAction());
    filemenu.add(fileItem1);
    menubar.add(filemenu);
    frame.setJMenuBar(menubar);
    frame.setSize(400,400);
    frame.setVisible(true);

   }
} 

public class myproject extends JFrame
{

   JButton button2;
   public static JTextField field;
   public static  String letername=null;
   public myproject ()
    { 
      this.setLayout(null);
      field = new JTextField();
      JLabel label1 = new JLabel("enter unique feild name for names of letter files  to be generated",JLabel.LEFT);
      button2 = new JButton("OK");
      label1.setBounds(10, 50, 400, 25);
      field.setBounds(100, 70, 200, 25);
      button2.setBounds(150, 150, 60, 35);
      this.add(label1);
      this.add(field);
      this.add(button2);
      button2.addActionListener(new butonAction());
      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }
   public static void main(String args[])
    {
      SwingMenu s = new SwingMenu();
    }
}
 class brwse extends JFrame implements ActionListener

{
   JButton button,okbuton;
   JTextField field2;
   public static  String filename=null;
   public brwse()
   {  
     this.setLayout(null);
     button = new JButton("browse");
     okbuton = new JButton("generate");
     field2 = new JTextField();  
     field2.setBounds(30, 50, 200, 25); 
     button.setBounds(240, 50, 100, 25);
     okbuton.setBounds(120, 80, 100, 25);
     this.add(field2);
     this.add(button); 
     this.add(okbuton);
     button.addActionListener(this);
     okbuton.addActionListener(new okAction());
     setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
   }
public void actionPerformed(ActionEvent e) 
  {
     Chooser frame = new Chooser();
     field2.setText(frame.fileName);
    filename=frame.fileName;
   
  }

public  static  void generate() 
{
try
{
    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    Connection conn=DriverManager.getConnection("jdbc:odbc:me");
    Statement stmt = null;
    ResultSet rs = null;
    String str2,name,fname,rollno;
    stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
    String excelQuery = "select * from [Sheet1$]";
    rs = stmt.executeQuery(excelQuery);
    while (rs.next()) 
     {
      File f1 = new File(filename);
      name= rs.getString(myproject.letername);
      rs.refreshRow();
      fname=new String("C:/Documents and Settings/bindu/Desktop/letters/"+name +".doc");
      File f=new File(fname);
      FileOutputStream out=new FileOutputStream(f);
      InputStream in = new FileInputStream(f1);
      byte len;
      while ((len = (byte) in.read()) > 0)
      {
       char c=(char)len;
       if(c=='$')
        { 
         String str=new String();
         len=(byte)in.read();
         c=(char)len;
         while(c!='$')
         {  
          str = str+c;
          len=(byte)in.read();
          c=(char)len;
         }
       str2=rs.getString(str);
       rs.refreshRow();
       System.out.println(str2);
       out.write(str2.getBytes());
      }
      else
      {
       String str1=new String();
       str1=str1+c;
       out.write(str1.getBytes());
}
  }  
          out.flush();
          out.close();
          in.close();
          System.out.println("The data has been written");
 
 }
 
    rs.close();
    stmt.close();
    conn.close();
    JFrame  frame = new JFrame("Show Message Dialog");   
    JOptionPane.showMessageDialog(frame,"letters are generated succesfully");

}
catch (Exception e)
		{       JFrame  frame = new JFrame("Show Message Dialog");   
                        JOptionPane.showMessageDialog(frame,"some problem encountered retry again");
                        e.printStackTrace();
                    
		}

}

}

class Chooser extends JFrame
 {
   JFileChooser chooser;
   String fileName;
   public Chooser()
    {
      chooser = new JFileChooser();
      int r = chooser.showOpenDialog(new JFrame());
      if (r == JFileChooser.APPROVE_OPTION) {
      fileName = chooser.getSelectedFile().getPath();
     }
    }
 }
class menuAction implements ActionListener
  {
    public void actionPerformed(ActionEvent ev)
     {   
       myproject frame = new myproject ();
       frame.setSize(400, 300);
       frame.setLocation(200, 100);
       frame.setVisible(true);
      }
  }

class okAction implements ActionListener
  {
     public void actionPerformed(ActionEvent ev)
       {   
         JFrame  frame = new JFrame("Show Message Dialog");   
         if(brwse.filename==null)
         JOptionPane.showMessageDialog(frame,"select the path name for letter template");
         else
         brwse.generate();
       }
      }

class butonAction implements ActionListener
  {
    public void actionPerformed(ActionEvent ev)
       {  
        JFrame  frame = new JFrame("Show Message Dialog");   
        if(myproject.field.getText()==null)
          {
           JOptionPane.showMessageDialog(frame,"select the feild name for letter template");
          }
       else
        { myproject.letername=myproject.field.getText();
          brwse frame1 = new brwse();
          frame1.setSize(400, 300);
          frame1.setLocation(200, 100);
          frame1.setVisible(true);}
         }
  }

 
  












