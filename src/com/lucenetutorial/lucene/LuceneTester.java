package com.lucenetutorial.lucene;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class LuceneTester {
	static String indexDir = LuceneConstants.INDEX_DIR;
	static String dataDir = LuceneConstants.DATA_DIR;
	Indexer indexer;
	Searcher searcher;

	public static void main(String[] args) {
		Boolean exit = false;
		
		do {
			try {
				Scripts.Script(1);
				Scanner input = new Scanner(System.in);
				switch(input.nextInt()) {
				  case 1:
					  caseOption1(input);
				    break;
				  case 2:
					  caseOption2();
				    break;
				  case 3:
					  exit = !exit;
					  Scripts.Script(3);
					break;
				  default:
					  Scripts.Script(4);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			} 
			catch (ParseException e) {
				e.printStackTrace();
			}
		}while(!exit);
		
	}
	
	public static void caseOption1(Scanner input) throws IOException {
		Boolean exit = false;
		
		do {
			try {
				Scripts.Script(2);
			
				switch(input.nextInt()) {
				  case 1:
					  addTextFile(input);
				    break;
				  case 2:
					  removeTextFile(input);
				    break;
				  case 3:
					  editTextFile();
					break;
				  case 4:
					  exit=!exit;
					break;
				  default:
					  Scripts.Script(4);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}while(!exit);
	}
	
	private static void addTextFile(Scanner input) throws IOException {
		input.nextLine(); // to reset keyboard
		Scripts.Script(6);
		String placeText = input.nextLine();
		Scripts.Script(7);
		String peopleText = input.nextLine();
		Scripts.Script(8);
		String titleText = input.nextLine();
		Scripts.Script(9);
		String bodyText = input.nextLine();
		
		FileWriter fileWriter = new FileWriter(dataDir + "\\Article"+ numberOfFiles() + ".txt");
		fileWriter.write("<PLACES>"+ placeText + "</PLACES>\n"
					   + "<PEOPLE>" + peopleText +"</PEOPLE>\n" 
					   + "<TITLE>" + titleText + "</TITLE>\n"
					   + "<BODY>" + bodyText + "</BODY>");
		fileWriter.close();
		Scripts.Script(5);
	}
	
	private static void removeTextFile(Scanner input) throws IOException {
//		File directoryPath = new File(dataDir);
//        String texts_names[] = directoryPath.list();
//        
//        Scripts.Script(10);
//		int number_to_delete = input.nextInt();
//		
//		File specifiedFile = new File(dataDir + "\\Article"+ number_to_delete + ".txt");
	}
	
	private static void editTextFile() {
		
	}
	
	public static void caseOption2() throws IOException, ParseException {
		LuceneTester tester;
		
		tester = new LuceneTester();
		tester.createIndex();
		
		tester.search("INTERNATIONAL");
	}
	
	private void createIndex() throws IOException {
		indexer = new Indexer(indexDir);
		int numIndexed;
		long startTime = System.currentTimeMillis();
		numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
		long endTime = System.currentTimeMillis();
		
		indexer.close();
		System.out.println("\n"+numIndexed + " File(s) indexed, time taken: " + (endTime-startTime)+" ms");
	}

	private void search(String searchQuery) throws IOException, ParseException 
	{
		searcher = new Searcher(indexDir);
		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.search(searchQuery);
		long endTime = System.currentTimeMillis();
		
		System.out.println("\n"+hits.totalHits +" documents found. Time :" + (endTime - startTime) + " ms");
		for(ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = searcher.getDocument(scoreDoc);
			System.out.println("File: " + doc.get(LuceneConstants.FILE_PATH));
		}
		
		searcher.close();
	}

	static long numberOfFiles() throws IOException {
		try (Stream<Path> files = Files.list(Paths.get("C:\\Users\\johnb\\eclipse-workspace\\Search-Engine-TReSA\\Data"))) {
		    return files.count();
		}
	}

}
