
import javax.swing.*;
import java.awt.*;
class Test extends JFrame
{
	JButton b;
	Test()
	{
		b = new JButton("Submit");
		add(b);
		setLayout(new FlowLayout());
		setSize(400,400);
		setLocation(300,100);
		setVisible(true);
	}
	public static void main(String args[])
	{
		new Test();
	}
}
	