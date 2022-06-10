//Jeremy Perez
//Professor Vuckovic
//Data Structures
//04/21/2022

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Fair extends JFrame implements ActionListener{

	
	static ArrayList<String> chemList = new ArrayList<String>();
	static ArrayList<String> mathList = new ArrayList<String>();
	static ArrayList<String> socialList = new ArrayList<String>();
	static ArrayList<String> envList = new ArrayList<String>();
	static ArrayList<String> lifeScienceList = new ArrayList<String>();
	static ArrayList<String> earthList = new ArrayList<String>();
	
	
	static ArrayList<String> chemJudgeList = new ArrayList<String>();
	static ArrayList<String> mathJudgeList = new ArrayList<String>();
	static ArrayList<String> socialJudgeList = new ArrayList<String>();
	static ArrayList<String> envJudgeList = new ArrayList<String>();
	static ArrayList<String> lifeScienceJudgeList = new ArrayList<String>();
	static ArrayList<String> earthJudgeList = new ArrayList<String>();
	
	static JTextField judgesInput = new JTextField("");
	static JLabel judgesLabel= new JLabel("Enter the name of the .txt file with the list of judges: ",SwingConstants.LEFT);
	static JTextField projectInput = new JTextField ("");
	static JLabel projects= new JLabel("Enter the name of the .txt file with the list of projects: ",SwingConstants.LEFT);
	static JTextField outputMessage = new JTextField ("");
	static JLabel output= new JLabel("Enter the name for the .txt file where you want to store the output: ", SwingConstants.LEFT);


	@SuppressWarnings("rawtypes")
	static ArrayList<ArrayList> unsorted = new ArrayList<ArrayList>();
	@SuppressWarnings("rawtypes")
	static ArrayList<ArrayList> sorted = new ArrayList<ArrayList>();

	static ArrayList<String> groupList = new ArrayList<String>();



	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run()
			{
				createGUI();
			}

		});

	}

	public Fair()
	{
		JFrame bigWindow = new JFrame();
		bigWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel buttonPanel = new JPanel(new FlowLayout());
		JPanel textPanel = new JPanel(new BorderLayout());
		JPanel textPanel1 = new JPanel(new BorderLayout());

		JButton MakeTheGroups = new JButton("Make Groups");
		MakeTheGroups.setActionCommand("Make Groups");
		MakeTheGroups.addActionListener(this);

		buttonPanel.add(MakeTheGroups);
		
		textPanel.add(projects, BorderLayout.NORTH);
		textPanel.add(judgesLabel,BorderLayout.CENTER);
		textPanel.add(output,BorderLayout.SOUTH);
		
		textPanel1.add(projectInput, BorderLayout.NORTH);
		textPanel1.add(judgesInput,BorderLayout.CENTER);
		textPanel1.add(outputMessage, BorderLayout.SOUTH);

		bigWindow.getContentPane().add(textPanel, BorderLayout.WEST);
		bigWindow.getContentPane().add(textPanel1, BorderLayout.CENTER);
		bigWindow.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		bigWindow.setVisible(true);
		bigWindow.setSize(500,300);

	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("Make Groups"))
		{
			combine(projectInput.getText(), judgesInput.getText(), outputMessage.getText());
			projectInput.setVisible(false);
			judgesInput.setVisible(false);
			projects.setVisible(false);
			judgesLabel.setVisible(false);
			output.setVisible(false);
			outputMessage.setText("Task Complete. Please, check the " + outputMessage.getText() + ".txt file for results.");
		}
	}


	public static void combine(String s1, String s2, String s3)
	{
		try
		{
			BufferedReader projectInput= new BufferedReader(new FileReader(s1+".txt"));
			BufferedReader judgeInput= new BufferedReader(new FileReader(s2+".txt"));
			PrintWriter outputStream= new PrintWriter(new FileOutputStream(s3+".txt"));
			String line=projectInput.readLine();

			while(line!=null)
			{
				line=line.replaceAll(" ", "");
				//System.out.println(line);

				if(line.contains("Chemistry"))
					chemList.add(line);
				if(line.contains("Math"))
					mathList.add(line);
				if(line.contains("Social"))
					socialList.add(line);
				if(line.contains("Environment"))
					envList.add(line);
				if(line.contains("Life"))
					lifeScienceList.add(line);
				if(line.contains("Earth"))
					earthList.add(line);

				line=projectInput.readLine();

			}

			line=judgeInput.readLine();
			while(line!=null)
			{
				if(line.contains("Chemistry"))
					chemJudgeList.add(line.substring(0,line.indexOf(':')));
				if(line.contains("Math"))
					//mathJudgeList.add(line.substring(0,line.indexOf(':')));
				if(line.contains("Social"))
					socialJudgeList.add(line.substring(0,line.indexOf(':')));
				if(line.contains("Environment"))
					envJudgeList.add(line.substring(0,line.indexOf(':')));
				if(line.contains("Life"))
					lifeScienceJudgeList.add(line.substring(0,line.indexOf(':')));
				if(line.contains("Earth"))
					earthJudgeList.add(line.substring(0,line.indexOf(':')));

				line=judgeInput.readLine();
			}

			//the following code segment sorts the different judge array by size, so that we can start  with the least populous one and move onto the most.
			//This way we don't end up with all of a subjects judges being stolen by other subjects.

			unsorted.add(chemJudgeList);
			unsorted.add(lifeScienceJudgeList);
			unsorted.add(envJudgeList);
			unsorted.add(socialJudgeList);
			unsorted.add(mathJudgeList);
			unsorted.add(earthJudgeList);

			int sizes[] = new int[6];

			for(int i=0; i<unsorted.size(); i++)
				sizes[i]=unsorted.get(i).size();

			Arrays.sort(sizes);
			//reverse(sizes);

			for(int j=0; j<6; j++)
				for(int k=0; k<unsorted.size(); k++)
					if(unsorted.get(k).size()==sizes[j])
						sorted.add(unsorted.get(k));
			//end sort

			for(int h=0; h<sorted.size(); h++)
			{
				if(sorted.get(h)==earthJudgeList)
					makeGroup(earthList, earthJudgeList);
				else if (sorted.get(h)==lifeScienceJudgeList)
					makeGroup(lifeScienceList, lifeScienceJudgeList);
				else if(sorted.get(h)==envJudgeList)
					makeGroup(envList, envJudgeList);
				else if(sorted.get(h)==socialJudgeList)
					makeGroup(socialList,socialJudgeList);
				else if(sorted.get(h)==mathJudgeList)
					makeGroup(mathList, mathJudgeList);
				else if(sorted.get(h)==chemJudgeList)
					makeGroup(chemList, chemJudgeList);
			}

			for(String s: groupList)
				System.out.print(s);

			for(String s: groupList)
				outputStream.print(s);

			System.out.println("Done");

			outputStream.close();
			judgeInput.close();
			projectInput.close();
		}

		catch (FileNotFoundException e)
		{
			System.out.println("File was not found.");
			e.printStackTrace();
		}

		catch (IOException e)
		{
			System.out.println("Error reading file");
			e.printStackTrace();
		}

	}

	public static void makeGroup(ArrayList<String> projects, ArrayList<String> judges)
	{
		int count=1, size, temp=projects.size()/6+1;
		String rndString;

		for(int h=0; h<temp; h++) //What this loop does is pull 6 projects and three judges from their respective lists and concatonates them into a string and puts that in the final list.
		{

			groupList.add(projects.get(0).substring(3, projects.get(0).length())+"_"+count + " ");
			count++;

			if(judges.size()>=3)
			{
				if(projects.size()>5)
				{
					for(int i=0; i < 3; i++)
					{
						groupList.add(judges.get(0));

						if(i!=2)
							groupList.add(", ");

						rndString=judges.get(0);

						for(int u=0; u<sorted.size(); u++)
							sorted.get(u).remove(rndString);

					}
				}
				else
				{
					for(int i=0; i < projects.size()/2 + 1; i++)
					{
						groupList.add(judges.get(0));

						if(i!=projects.size()/2)
							groupList.add(", ");

						rndString=judges.get(0);

						for(int u=0; u<sorted.size(); u++)
							sorted.get(u).remove(rndString);

					}
				}

				groupList.add("\r\nProjects: ");

				if(projects.size()>=6)
				{
					for(int j=0; j<6; j++)
					{
						if(j!=5)
							groupList.add(projects.get(0).substring(0, 3)+ ", ");
						else
							groupList.add(projects.get(0).substring(0, 3));
						projects.remove(0);
					}
				}

				else //in case there are fewer than 6 projects
				{
					size=projects.size();
					for(int j=0; j<size; j++)
					{
						if(j!=size-1)
							groupList.add(projects.get(0).substring(0, 3)+ ", ");
						else
							groupList.add(projects.get(0).substring(0, 3));

						projects.remove(0);
					}
				}

				groupList.add("\r\n");
			}

			// Since there are categories with less than 3 judges. We don't the code below
			else //in case there are fewer than three judges
			{
				size=judges.size();
				for(int i=0; i<size; i++)
				{
					groupList.add(judges.get(0));

					if(i != size-1)
						groupList.add(", ");

					rndString=judges.get(0);

					for(int u=0; u<sorted.size(); u++)
						sorted.get(u).remove(rndString);
				}

				groupList.add("\r\nProjects: ");

				if(projects.size()>=6)
				{
					for(int j=0; j<6; j++)
					{
						if(j!=5)
							groupList.add(projects.get(0).substring(0, 3)+ ", ");
						else
							groupList.add(projects.get(0).substring(0, 3));

						projects.remove(0);
					}
				}

				else //in case there are fewer than 6 projects
				{
					size=projects.size();
					for(int j=0; j<size; j++)
					{
						if(j!=size-1)
							groupList.add(projects.get(0).substring(0, 3)+ ", ");
						else
							groupList.add(projects.get(0).substring(0, 3));

						projects.remove(0);
					}
				}

				groupList.add("\r\n");
			}
		}
		groupList.add(" \r\n");
	}
	public static void createGUI()
	{
		//Make sure it looks Good
		JFrame.setDefaultLookAndFeelDecorated(false);

		//Create and set up the window
		Fair frame = new Fair();

		//Display window
		frame.setVisible(true);
	}
}

	