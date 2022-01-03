package com.lucenetutorial.lucene;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;

public class Searcher {
	IndexSearcher indexSearcher;
	Directory indexDirectory;
	IndexReader indexReader;
	Query query;
	
	public Searcher(String indexDirectoryPath) throws IOException {
		Path indexPath = Paths.get(indexDirectoryPath);
		indexDirectory = FSDirectory.open(indexPath);
		indexReader = DirectoryReader.open(indexDirectory);
		indexSearcher = new IndexSearcher(indexReader);
	}
	
	public TopDocs search(int choice, Scanner input) throws IOException, ParseException {
		if(choice!=5)Scripts.Script(13);
		else Scripts.Script(21);
		if(choice == 2 ) { Scripts.Script(19); }
		input.nextLine();
		String searchQuery = input.nextLine();

		switch (choice) {
			case 1 -> {
				QueryParser queryParser = new QueryParser(LuceneConstants.CONTENTS, new StandardAnalyzer());
				query = queryParser.parse(searchQuery);
				System.out.println("query: " + query.toString());
				return indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
			}
			case 2 -> {
				BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
				List<String> arraySearchQueryBoolean = stringToArrayList(searchQuery);
				Query query;
				boolean prevAND = false, prevOR = false, prevNOT = false;
				for (int i = 0; i < arraySearchQueryBoolean.size(); i++) {
					if (arraySearchQueryBoolean.get(i).contains("&&")) {
						prevAND = true;
					} else if (arraySearchQueryBoolean.get(i).contains("||")) {
						prevOR = true;
					} else if (arraySearchQueryBoolean.get(i).contains("^")) {
						prevNOT = true;
					} else {
						if (prevAND) {
							query = new TermQuery(new Term(LuceneConstants.CONTENTS, arraySearchQueryBoolean.get(i)));
							booleanQuery.add(query, BooleanClause.Occur.MUST);
							prevAND = false;
						} else if (prevOR) {
							query = new TermQuery(new Term(LuceneConstants.CONTENTS, arraySearchQueryBoolean.get(i)));
							booleanQuery.add(query, BooleanClause.Occur.SHOULD);
							prevOR = false;
						} else if (prevNOT) {
							query = new TermQuery(new Term(LuceneConstants.CONTENTS, arraySearchQueryBoolean.get(i)));
							booleanQuery.add(query, BooleanClause.Occur.MUST_NOT);
							prevNOT = false;
						} else {
							query = new TermQuery(new Term(LuceneConstants.CONTENTS, arraySearchQueryBoolean.get(i)));
							booleanQuery.add(query, BooleanClause.Occur.MUST);
							if (i != 0) {
								booleanQuery.add(query, BooleanClause.Occur.MUST);
							} else if (arraySearchQueryBoolean.get(i + 1).contains("||")) {
								booleanQuery.add(query, BooleanClause.Occur.SHOULD);
							}
						}
					}
				}
				return indexSearcher.search(booleanQuery.build(), LuceneConstants.MAX_SEARCH);
			}
			case 3 -> {
				PhraseQuery.Builder phraseQuery = new PhraseQuery.Builder();
				List<String> arraySearchQueryPhrase = stringToArrayList(searchQuery);
				for (String s : arraySearchQueryPhrase) {
					phraseQuery.add(new Term(LuceneConstants.CONTENTS, s));
				}
				return indexSearcher.search(phraseQuery.build(), LuceneConstants.MAX_SEARCH);
			}
			case 4 -> {
				Scripts.Script(20);
				choice = input.nextInt();
				QueryParser queryParser;
				if (choice == 1) {
					queryParser = new QueryParser(LuceneConstants.PLACES, new StandardAnalyzer());
				} else if (choice == 2) {
					queryParser = new QueryParser(LuceneConstants.PEOPLE, new StandardAnalyzer());
				} else if (choice == 3) {
					// Doesn't work with LuceneConstants.TITLE
					queryParser = new QueryParser(LuceneConstants.CONTENTS, new StandardAnalyzer());
				} else if (choice == 4) {
					// Doesn't work with LuceneConstants.BODY
					queryParser = new QueryParser(LuceneConstants.CONTENTS, new StandardAnalyzer());
				} else {
					throw new IllegalStateException("Unexpected value: " + choice);
				}
				query = queryParser.parse(searchQuery);
				System.out.println("query: " + query.toString());
				return indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
			}
			case 5 -> {
				if (checkExistanceOfFile(searchQuery, input)){
					QueryParser queryParser = new QueryParser(LuceneConstants.CONTENTS, new StandardAnalyzer());
					// SearchQuery should become LuceneConstants.CONTENTS of the file we searched
					// (file name is inside searchQuery variable)

					// Take LuceneConstants.CONTENTS of [filename].txt from Index
					String documentContents = documentContext(searchQuery);
					query = queryParser.parse(documentContents);
					return indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
				}
				else{
					Scripts.Script(22);
				}
			}
		}
		return null;
	}
	
	public Document getDocument(ScoreDoc scoreDoc) throws IOException {
		return indexSearcher.doc(scoreDoc.doc);
	}
	
	public void close() throws IOException {
		indexReader.close();
		indexDirectory.close();
	}
	
	public List<String> stringToArrayList(String s) {
		String[] str = s.split(" ");
		return Arrays.asList(str);
	}

	public String documentContext(String searchQuery) throws IOException {
		Set<String> h = new HashSet<>() {{ add(LuceneConstants.CONTENTS); }};
		ArrayList<String> fileNumbers = new ArrayList<>();
		for(int i=0 ; i<LuceneTester.numberOfFiles() ; i++){
			String s=String.valueOf(i);
			fileNumbers.add(s);
		}
		Collections.sort(fileNumbers);
		int positionInsideIndex=0;
		searchQuery = searchQuery.replace("Article","");
		for (String a: fileNumbers) {
			if(a.equals(searchQuery)) { break; }
			positionInsideIndex++;
		}
		Document documentSearched = indexReader.document(positionInsideIndex,h);
		return documentSearched.toString();
	}

	public boolean checkExistanceOfFile(String searchQuery, Scanner input) throws IOException {
		PhraseQuery.Builder phraseQuery = new PhraseQuery.Builder();
		List<String> arraySearchQueryPhrase = stringToArrayList(searchQuery+".txt");
		for (String s : arraySearchQueryPhrase) {
			phraseQuery.add(new Term(LuceneConstants.FILE_NAME, s));
		}
		TopDocs hits = indexSearcher.search(phraseQuery.build(), LuceneConstants.MAX_SEARCH);
		return !hits.totalHits.toString().contains("0");
	}
}