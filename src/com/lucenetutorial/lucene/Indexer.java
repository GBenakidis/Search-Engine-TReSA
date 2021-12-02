package com.lucenetutorial.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {
	private IndexWriter writer;
	
	public Indexer(String indexDirectoryPath) throws IOException {
		// This directory will contain the indexes
		Path indexPath = Paths.get(indexDirectoryPath);
		if(!Files.exists(indexPath)) {
			Files.createDirectory(indexPath);
		}
		
		// Path indexPath = Files.createTempDirectory(indexDirectoryPath);
		Directory indexDirectory = FSDirectory.open(indexPath);
		
		// Create the indexer
		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
		writer = new IndexWriter(indexDirectory, config);
	}
	
	private Document getDocument(File file) throws IOException {
		Document document = new Document();
	
		// Index file name
		Field fileNameField = new Field(LuceneConstants.FILE_NAME, file.getName(), StringField.TYPE_STORED);
		
		// Index file path
		Field filePathField = new Field(LuceneConstants.FILE_PATH, file.getCanonicalPath(), StringField.TYPE_STORED);
		
		String preProcessed = contentTaker(file);
		
		// System.out.println("\n\n" + preProcessed); // Printing 
		
		// Pre-processing
		String postProcessing = preProcessing(preProcessed);
		
		// System.out.println("\n\n" + postProcessing); // Printing 
		
		// Index file contents
		Field contentField = new Field(LuceneConstants.CONTENTS, postProcessing, TextField.TYPE_STORED);

		document.add(contentField);
		document.add(fileNameField);
		document.add(filePathField);
		
		return document;
	}
	
	private String preProcessing(String preProcessedContent) {
		// Removing <Places>, <People>, <Title> and <Body>
		preProcessedContent = removingHTML(preProcessedContent);
		
		// Remove punctuation
		preProcessedContent = removingPunctuation(preProcessedContent);
		
		// Everything to lower case
		preProcessedContent = preProcessedContent.toLowerCase();
		
		// Removing numbers
		preProcessedContent = preProcessedContent.replaceAll("[0-9]","");
		
		// Removing stop words
		preProcessedContent = removingStopwords(preProcessedContent);
		
		return preProcessedContent;
	}
	
	private String removingStopwords(String preProcessedContent) {
	    Pattern p = Pattern.compile("\\b(I|this|its|i|ii|iii|vi|iv|x|v|y.....)\\b\\s?");
	    Matcher m = p.matcher(preProcessedContent);
	    preProcessedContent = m.replaceAll("");
	    
		return preProcessedContent;
	}
	
	private String removingPunctuation(String preProcessedContent) {
		preProcessedContent = preProcessedContent.replaceAll("\\p{Punct}", "");
		preProcessedContent = preProcessedContent.replaceAll("", "");
		
		return preProcessedContent;
	}
	
	private String removingHTML(String preProcessedContent) {
		preProcessedContent=preProcessedContent.replace("<PLACES>","");
		preProcessedContent=preProcessedContent.replace("</PLACES>","");
		
		preProcessedContent=preProcessedContent.replace("<PEOPLE>","");
		preProcessedContent=preProcessedContent.replace("</PEOPLE>","");
		
		preProcessedContent=preProcessedContent.replace("<TITLE>","");
		preProcessedContent=preProcessedContent.replace("</TITLE>","");
		
		preProcessedContent=preProcessedContent.replace("<BODY>","");
		preProcessedContent=preProcessedContent.replace("</BODY>","");
		
		preProcessedContent=preProcessedContent.replace("    ",""); // useless in general, keep it for System.out
		
		return preProcessedContent;
	}
	
	private String contentTaker(File file) throws IOException {
		// https://www.journaldev.com/875/java-read-file-to-string
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		// delete the last new line separator
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		reader.close();
		
		return stringBuilder.toString();
	}
	
	private void indexFile(File file) throws IOException {
		System.out.println("Indexing " + file.getCanonicalPath());
		Document document = getDocument(file);
		writer.addDocument(document);
	}
	
	public int createIndex(String dataDirPath, FileFilter filter) throws IOException {
		// Get all files in the data directory
		File[] files = new File(dataDirPath).listFiles();
		for (File file : files) {
			if(!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && filter.accept(file) ){
				indexFile(file);
			}
		}
		
		return writer.numRamDocs();
	}

	public void close() throws CorruptIndexException, IOException {
		writer.close();
	}
	
}
