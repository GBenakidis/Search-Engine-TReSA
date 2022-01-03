package com.lucenetutorial.lucene;

import java.io.IOException;

public class Scripts {
	static void Script(int choise) throws IOException {
		if(choise == 1) {
			System.out.println("\n\nWELCOME TO TReSA\n\nYOUR CHOICES ARE:");
			System.out.println("1. Number of articles at Data dir\n2. Create or update Index\n3. Search for " +
					           "a word or a phrase\n4. Article relation\n5. Exit");
		}
		else if(choise == 2) {
			System.out.println("\n\nThere are: " + LuceneTester.numberOfFiles() + " articles");
		}
		else if(choise == 3) {
			System.out.println("\nHave a nice day!");
		}
		else if(choise == 4) {
			System.out.println("\n\nWrong number choice");
		}
		else if(choise == 5) {
			System.out.println("\n\nYour article is created");
		}
		else if(choise == 6) {
			System.out.println("\nIn which country has be written:");
		}
		else if(choise == 7) {
			System.out.println("\nFrom whom [can be empty]:");
		}
		else if(choise == 8) {
			System.out.println("\nThe title of the article:");
		}
		else if(choise == 9) {
			System.out.println("\nWhat is the context:");
		}
		else if(choise == 10) {
			System.out.println("\nWhat number has the article you want to delete:");
		}
		else if(choise == 11) {
			System.out.println("\nYour options are:");
		}
		else if(choise == 12) {
			System.out.println("\nIndex has been created!");
		}
		else if(choise == 13) {
			System.out.println("\nWrite what you want to find:");
		}
		else if(choise == 14) {
			System.out.println("\n\nChoose:\n1. Create/Recreate index\n2. Insert article\n3. Delete article\n4. Back");
		}
		else if(choise == 15) {
			System.out.println("Choose an article (Example format: Article8.txt):\n");
		}
		else if(choise == 16) {
			System.out.println("\nArticles inside Data dir are:\n");
		}
		else if(choise == 17) {
			System.out.println("\nDo you want your Index to be:\n1. Full(all data dir articles)\n2. Empty\n");
		}
		else if(choise == 18) {
			System.out.println("\nType of search:\n1. VSM\n2. Boolean\n3. Phrase\n4. Query\n");
		}
		else if(choise == 19) {
			System.out.println("\nCorrect format of our Boolean Model must be writen with:\n" +
							   "Logical operations\n&& <- AND\n|| <- OR\n^ <- NOT\n\n");
		}
		else if(choise == 20) {
			System.out.println("\nChoose in which field you want the search to take place:" +
					           "\n1. Places\n2. People\n3. Title\n4. Body");
		}
		else if(choise == 21) {
			System.out.println("\nChoose an article to find a similar one(Format: Article[number]):\n");
		}
		else if(choise == 22) {
			System.out.println("\nNo article in the Index has that name.\n");
		}
		else if(choise == 23) {
			System.out.println("\nA problem occurred.\n");
		}

	}
	
	static void ScriptWithString(String s) {
		System.out.println("Incorrect file name: " + s);
	}

}
