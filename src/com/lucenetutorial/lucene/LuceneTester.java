package com.lucenetutorial.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class LuceneTester {
	Indexer indexer;
	Searcher searcher;

	public static void main(String[] args) {
		boolean exit = false;
		do {
			try {
				Scripts.Script(1);
				Scanner input = new Scanner(System.in);
				switch (input.nextInt()) {
					case 1 -> Scripts.Script(2);
					case 2 -> {
						Scripts.Script(14);
						switch (input.nextInt()) {
							case 1:
								caseOption2(1);
								break;
							case 2:
								caseOption2(2);
								break;
							case 3:
								caseOption2(3);
								break;
							case 4:
								break;
						}
					}
					case 3 -> caseOption3(input);
					case 4 -> caseOption4(input);
					case 5 -> {
						exit = true;
						Scripts.Script(3);
					}
					default -> Scripts.Script(4);
				}
			}
			catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}while(!exit);
		
	}

	public static void caseOption2(int choice) throws IOException, ParseException {
		LuceneTester tester = new LuceneTester();
		
		Scanner input = new Scanner(System.in);
		
		if(choice==1){
			Scripts.Script(17);
			int c = input.nextInt();
			if(c==1) {
				tester.createIndex(1);
				Scripts.Script(12);
			}else if(c==2) {
				tester.createIndex(2);
				Scripts.Script(12);
			}else {
				Scripts.Script(4);
			}
		}else if(choice==2) {
			allDataDirFiles();
			Scripts.Script(15);
			//Scanner input = new Scanner(System.in);
			String articles = input.nextLine();
			ArrayList<String> art = new ArrayList<>();
			String[] a = articles.split(" ");
			for(String str : a) {
				if(tester.checkTheName(str)) {
					art.add(str);
				}else {
					Scripts.ScriptWithString(str);
				}
				
			}
			if(!art.isEmpty())
				tester.editIndex(art);
		}else if(choice==3){
			allDataDirFiles();
			Scripts.Script(15);
			//Scanner input = new Scanner(System.in);
			String articles = input.nextLine();
			ArrayList<String> art = new ArrayList<>();
			String[] a = articles.split(" ");
			for(String str : a) {
				if(tester.checkTheName(str)) {
					art.add(str);
				}else {
					Scripts.ScriptWithString(str);
				}
			}
			if(!art.isEmpty()) {
				DeleteDocuments deleteDocuments = new DeleteDocuments();
				deleteDocuments.deleteSpecificDocs(art);
			}
			
		}else {
			Scripts.Script(4);
		}
	}
	
	public static void caseOption3(Scanner input) throws IOException, ParseException {
		LuceneTester tester = new LuceneTester();
		Scripts.Script(18);
		input.nextLine(); // clear keyboard
		int choice = input.nextInt();
		tester.search(choice, input);
	}

	public static void caseOption4(Scanner input) throws IOException, ParseException {
		LuceneTester tester = new LuceneTester();
		tester.search(5, input);
	}
	
	private void editIndex(ArrayList<String> articles) throws IOException {
		indexer = new Indexer(LuceneConstants.INDEX_DIR);
		int numIndexed;
		long startTime = System.currentTimeMillis();		
		
		numIndexed = indexer.editIndex(LuceneConstants.DATA_DIR, new TextFileFilter(),articles);
		long endTime = System.currentTimeMillis();		
		indexer.close();
		System.out.println("\n"+numIndexed + " File(s) indexed, time taken: " + (endTime-startTime)+" ms");
	}

	private void createIndex(int i) throws IOException {
		indexer = new Indexer(LuceneConstants.INDEX_DIR);
		int numIndexed;
		long startTime = System.currentTimeMillis();

		numIndexed = indexer.createIndex(LuceneConstants.DATA_DIR, new TextFileFilter(), i);
		long endTime = System.currentTimeMillis();
		indexer.close();
		 System.out.println("\n"+numIndexed + " File(s) indexed, time taken: " + (endTime-startTime)+" ms");
	}

	private void search(int choice, Scanner input) throws IOException, ParseException {
		searcher = new Searcher(LuceneConstants.INDEX_DIR);
		boolean articleComp = false;
		long startTime = System.currentTimeMillis();
		TopDocs hits;
		if (choice == 1) {
			hits = searcher.search(1, input);
		} else if (choice == 2) {
			hits = searcher.search(2, input);
		} else if (choice == 3) {
			hits = searcher.search(3, input);
		} else if (choice == 4) {
			hits = searcher.search(4, input);
		} else if (choice == 5) {
			hits = searcher.search(5, input);
			articleComp = true;
		} else {
			throw new IllegalStateException("Unexpected value: " + choice);
		}
		long endTime = System.currentTimeMillis();
		if(hits != null){
			System.out.println("\n" + hits.totalHits + " documents found. Time :" + (endTime - startTime) + " ms\n");

			if(articleComp) {
				int n = 0;
				for (ScoreDoc scoreDoc : hits.scoreDocs) {
					if (n == 0) {
						n++;
						continue;
					}
					Document doc = searcher.getDocument(scoreDoc);
					System.out.println(n + ". " + doc.get(LuceneConstants.FILE_NAME) + " with score " + scoreDoc.score);
					n++;
				}
			}
			else{
				for(ScoreDoc scoreDoc : hits.scoreDocs) {
					Document doc = searcher.getDocument(scoreDoc);
					System.out.println("File: " + doc.get(LuceneConstants.FILE_PATH));
				}

			}
		}
		else{
			Scripts.Script(23);
		}
		
		
		searcher.close();
	}

	static long numberOfFiles() throws IOException {
		try (Stream<Path> files = Files.list(Paths.get(LuceneConstants.DATA_DIR))) {
		    return files.count();
		}
	}

	public boolean checkTheName(String articles) {
		String[] arr = articles.split(" ");
		for(String str : arr) {
			if(str.startsWith("Article") && str.endsWith(".txt")) {
				String num = str.replace("Article", "");
				num = num.replace(".txt", "");
				Scripts.ScriptWithString(num);
			    try {
			        int d = Integer.parseInt(num);
			    } catch (NumberFormatException nfe) {
			        return false;
			    }
			    return true;
			}else {
				return false;
			}
		}
		return true;
	}

	public static void allDataDirFiles() throws IOException {
		Scripts.Script(16);
		int i=0;
		File[] files = new File(LuceneConstants.DATA_DIR).listFiles();
	    for (File file : Objects.requireNonNull(files))
			System.out.println("File "+(i++)+": "+file.getName());
	}

}
