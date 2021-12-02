package com.lucenetutorial.lucene;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class LuceneTester {
	static String indexDir = LuceneConstants.INDEX_DIR;
	static String dataDir = LuceneConstants.DATA_DIR;
	Indexer indexer;
	Searcher searcher;
	private IndexWriter writer;
	public static void main(String[] args) {
		Boolean exit = false;
		
		do {
			try {
				Scripts.Script(1);
				Scanner input = new Scanner(System.in);
				switch(input.nextInt()) {
				  case 1:
					  Scripts.Script(2);
				    break;
				  case 2:
					  	System.out.println("Epelekse:\n1. Eisagwgh arxeioy\n2.Diagrafh arxeioy\n");
						switch(input.nextInt()) {
							case 1:
								caseOption2(1);
								break;
							case 2:
								caseOption2(2);
								break;
						}
		
				    break;
				  case 3:
					  caseOption3(input);
				    break;
				  case 4:
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
		
	public static void caseOption2(int choice) throws IOException, ParseException {
		LuceneTester tester = new LuceneTester();
		if(choice==1) {
			System.out.println("Epelekse kapoio arxeio px Article8.txt Article10.txt\n");
			Scanner input = new Scanner(System.in);
			String articles = input.nextLine();
			ArrayList<String> art = new ArrayList<String>();
			String[] a = articles.split(" ");
			for(String str : a) {
				art.add(str);
			}
			
			tester.createIndex(art);
			tester.search("fuck");
		}else {
			System.out.println("Epelekse kapoio arxeio px Article8.txt Article10.txt\n");
			Scanner input = new Scanner(System.in);
			String articles = input.nextLine();
			ArrayList<String> art = new ArrayList<String>();
			String[] a = articles.split(" ");
			for(String str : a) {
				art.add(str);
			}
			DeleteDocuments deleteDocuments = new DeleteDocuments();
			deleteDocuments.deleteSpecificDocs(art);
		}
		
		
		Scripts.Script(12);
	}
	
	public static void caseOption3(Scanner input) throws IOException, ParseException {
		LuceneTester tester = new LuceneTester();
		Scripts.Script(13);
		input.nextLine(); // clear keyboard
		String wordSearching = input.nextLine();
		
		tester.search(wordSearching);
	}
	
	private void createIndex(ArrayList<String> articles) throws IOException {
		indexer = new Indexer(indexDir);
		int numIndexed;
			long startTime = System.currentTimeMillis();		
		numIndexed = indexer.createIndex(dataDir, new TextFileFilter(),articles);
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
