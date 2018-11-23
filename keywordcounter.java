/*
	The class keywordcounter is implemented to find the 'n' 
	most searched keywords in the search engine 'DuckDuckGo'.
	The keywords are stored in a in built HashTable data structure
	and the corresponding frequencies are stored in the Fibonacci
	Heap.

	The input is a text file with the keywords andd their 
	corresponding frequencies

	The output corresponds to the queries in the input file
	and lists the top n keywords in newline everytime a digit 
	is encountered in the input.

	The main method is invoked in the following format
	java keywordcounter 'InputFileName.txt'

	We use BufferedReader and BufferedWriter to read and 
	write to the files.
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.StringJoiner;

public class keywordcounter {
	@SuppressWarnings("resource")
	public static void main(String[] args)
	{
		BufferedReader reader;
		BufferedWriter writer;

		try
		{
			long startTime = System.currentTimeMillis();
			reader = new BufferedReader(new FileReader(args[0]));
			writer = new BufferedWriter(new FileWriter("output_file.txt"));
			String line = reader.readLine();
			//Initializing the HashTable and the FibonacciHeap.
			Hashtable<String, Node> hashTable = new Hashtable<>();
			FibonacciHeap H = FibonacciHeap.makeFibHeap();
			//Read until there are no more lines in the input file
			while(line!=null)
			{
				/*
					If the character at 0 index of the line is '$',
					then we need to check if the keyword is already 
					present in the hashTable. If yes, we increment
					the frequency of the keyword by the value in the
					FibonacciHeap and call the Increase key method to
					increase the value of the node to the new incremented
					key value. Else, we will insert the keyword into the 
					HashTable and the corresponding Node to	the FibonacciHeap.
				*/
				if(line.charAt(0) == '$')
				{
					int index = line.indexOf(" ");
					String key = line.substring(1, index);
					Integer val = new Integer(line.substring(index+1));
					if(hashTable.containsKey(key))
					{
						Node y = hashTable.get(key);
						val = val + y.getKey();
						H.FibHeapIncreaseKey(y, val);
					}
					else
					{
						Node x = new Node(val, key);
						hashTable.put(key, x);
						H.FibHeapInsert(x);
					}
				}
				/*
				Else if the character at the 0 index of the line is a digit 'd',
				then run ExtractMax 'd' times, and store each node in a list.
				If while extracting, the heap becomes empty then we continue 
				to the next line in the input. After Extracting the top d elements,
				we insert the nodes in the list again into the FibonacciHeap, as we 
				do not need to delete the keywords.
				*/
				else if(Character.isDigit(line.charAt(0)))
				{
					Integer d = Integer.parseInt(String.valueOf(line));
					Node y;
					//StringBuilder str = new StringBuilder();
					StringJoiner str = new StringJoiner(",");
					Node[] list = new Node[d];
					//List<Node> list = new ArrayList<Node>();
					for(int i=0;i<d;i++)
					{
						y = H.FibHeapExtractMax();
						if(y != null)
						{
							str.add(y.getName());
							//list.add(y);
							list[i] = y;
						}
						else 
						{
							continue;
						}
					}

					/*for(Node temp: list)
					{
						H.FibHeapInsert(temp);
					}*/
					for(int i=0; i<d; i++)
					{
						if(list[i] != null)
							H.FibHeapInsert(list[i]);
					}
					writer.write(str.toString());
					writer.newLine();
				}
				/*
					If we encounter stop in the input, the we just break the loop
					and stop the control flow.
				*/
				else if(line.equals("stop"))
				{
					break;
				}

				//After processing the current line, read the next line
				line = reader.readLine();
			}

			/*
				Close the BufferedWriter and calculate the processing time
			*/
			writer.close();
			reader.close();
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("Time Taken in milli seconds is:" + totalTime) ;
		}
		catch(Exception e)
		{
			/*
				If any Exception is caught, print the Exception Message
			*/
			System.err.println("Exception caught:" + e.getMessage());
		}
	}
}
