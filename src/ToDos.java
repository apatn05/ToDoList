import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;

public class ToDos extends JFrame{
    private JList<String> list;
    private DefaultListModel<String> model;
    private JTextField enterItem, enterPriority;
    private JCheckBox deadline;
    private JComboBox<String> months, days, years;
    private JTextArea notes;
    private List<Item> items;
    private JButton done, delete;

    public ToDos() { 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        model = new DefaultListModel<>();
        list = new JList<>(model);
        list.setPreferredSize(new Dimension(256, 256));
        Border border1 = new TitledBorder("ToDo List");
        list.setBorder(border1);

        JPanel fields = new JPanel(new GridLayout(8, 3));
        Border border2 = new TitledBorder("Task Info");
        fields.setBorder(border2);
        add(fields, BorderLayout.CENTER);

        fields.add(new JLabel("Task:"));
        enterItem = new JTextField();
        fields.add(enterItem);

        fields.add(new JLabel("Priority:"));
        enterPriority = new JTextField();
        fields.add(enterPriority);

        fields.add(new JLabel("Deadline:"));
        deadline = new JCheckBox();
        fields.add(deadline);

        fields.add(new JLabel("Month:"));
        months = new JComboBox<>(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        months.addActionListener(e -> date());
        months.setEnabled(false); 
        fields.add(months);

        fields.add(new JLabel("Day:"));
        days = new JComboBox<>();
        date();
        days.setEnabled(false); 
        fields.add(days);

        fields.add(new JLabel("Year:"));
        years = new JComboBox<>();
        for (int i = 2024; i <= 2033; i++) 
        {
            years.addItem(String.valueOf(i));
        }
        years.addActionListener(e -> date());
        years.setEnabled(false); 
        fields.add(years);
        deadline.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e)
            {
                greyout();
            }
        });

        fields.add(new JLabel("Notes:"));
        notes = new JTextArea();
        JScrollPane notesScrollPane = new JScrollPane(notes);
        fields.add(notesScrollPane);

        JButton save = new JButton("Save Task");
        save.addActionListener(new SaveItem());
        fields.add(save);

        JButton addItem = new JButton("New Task");
        addItem.addActionListener(e -> reset());
        fields.add(addItem);
        
        done = new JButton("Toggle Done");
        done.addActionListener(new Complete());

        delete = new JButton("Delete");
        delete.addActionListener(new Delete());
        
        JPanel toDoList = new JPanel();
        toDoList.setLayout(new BorderLayout());
        toDoList.add(list, BorderLayout.WEST); 
 
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout()); 
        buttons.add(done); 
        buttons.add(delete);
        toDoList.add(buttons, BorderLayout.SOUTH);

        add(toDoList, BorderLayout.WEST);
      
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        file.add(exit);
        bar.add(file);
        setJMenuBar(bar);
        items = new ArrayList<>();
        setVisible(true);
    }

    private void reset() {
        enterItem.setText("");
        enterPriority.setText("");
        deadline.setSelected(false);
        months.setSelectedIndex(0);
        days.setSelectedIndex(0);
        years.setSelectedIndex(0);
        notes.setText("");
    }
    
    private void greyout() 
    {
        boolean checked = deadline.isSelected();
        months.setEnabled(checked);
        days.setEnabled(checked);
        years.setEnabled(checked);
    }
    
    public void showMessage(String message, String title) 
    {
    	JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void date() {
    	 if (years != null && months != null && years.getSelectedItem() != null && months.getSelectedItem() != null) 
    	 {
	        String choice = (String) years.getSelectedItem();
	        int month = months.getSelectedIndex() + 1;
	        int year = Integer.parseInt(choice);
	        int converted = YearMonth.of(year, month).lengthOfMonth();
	        days.removeAllItems();
	        for (int i = 1; i <= converted; i++) 
	        {
	            days.addItem(String.valueOf(i));
	        }
	    }
    }

    public boolean verify() 
    {
        if (enterItem.getText().isEmpty()) 
        {
        	return false;
        }
        try 
        {
            int priority = Integer.parseInt(enterPriority.getText());
            if (priority <= 0) 
            {
            	return false;
            }
        } catch (IllegalArgumentException e)
        {
            return false;
        }
        return true; 
    }
    
    public class Complete implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            if (index != -1) 
            {
                String name = model.getElementAt(index).replaceAll("<html><strike>|</strike></html>", "");
                Item item = find(name);
                if (item != null) 
                {
                    if (item.isCompleted()) 
                    {
                        item.setCompleted(false);
                        strikethrough();
                    } 
                    else 
                    {
                        item.setCompleted(true);
                        strikethrough();
                    }
                }
            }
        }

        private void strikethrough() {
        	items.sort((i1, i2) -> 
        	{
                if (i1.isCompleted() && !i2.isCompleted()) 
                {
                	return 1;
                }
                else if (!i1.isCompleted() && i2.isCompleted()) 
                {
                	return -1;
               	}
                return 0; 
            });
            model.clear();
            for (Item item : items) 
            {
                if (item.isCompleted())
                {
                    model.addElement("<html><strike>" + item.getName() + "</strike></html>");
                } 
                else 
                {
                    model.addElement(item.getName());
                }
            }
        }

        public Item find(String name) {
            for (Item item : items) 
            {
                if (item.getName().equalsIgnoreCase(name)) 
                {
                    return item;
                }
            }
            return null;
        }
        
    }

    public class Delete implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            if (index != -1) 
            {
                String name = model.getElementAt(index);
                model.remove(index);
                items.removeIf(item -> item.getName().equalsIgnoreCase(name));
            }
        }
    }

    public class SaveItem implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!verify()) 
            {
            	showMessage("Invalid input!", "Error");
                return;
            }
            String name = enterItem.getText().trim();
            int priority = Integer.parseInt(enterPriority.getText().trim());
            for (Item item : items) 
            {
                if (item.getName().equalsIgnoreCase(name)) 
                {
                	showMessage("Invalid input!", "Error");
                    return;
                }
            }
            Item newItem = new Item(name, priority);
            if (deadline.isSelected()) 
            {
                int month = months.getSelectedIndex() + 1;
                int day = Integer.parseInt((String) days.getSelectedItem());
                int year = Integer.parseInt((String) years.getSelectedItem());
                newItem.setDeadline(LocalDate.of(year, month, day));
            }
            newItem.setNotes(notes.getText().trim());
            items.add(newItem);
            items.sort(null);
            model.clear();
            for (Item item : items) 
            {
                model.addElement(item.getName());
            }
            list.setSelectedValue(name, true);
            showMessage("Task saved!", "Success!");
        }
    }
    
    
    
    
    
    public static void main(String[] args) 
    {
        JFrame frame = new ToDos();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("List");
        frame.setVisible(true);
    }
}
